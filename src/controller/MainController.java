package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dao.DatabaseManager;
import exceptions.BetException;
import model.Blackjack;
import model.Client;
import model.Game;
import model.Session;
import model.Slotmachine;
import model.User;
import ui.BlackjackUI;

/**
 * The central controller of the application, coordinating between the Model
 * (game logic, clients, users) and the View (UI windows).
 * <p>
 * Responsibilities:
 * <ul>
 * <li>Handling user actions from the UI and translating them into application
 * logic.</li>
 * <li>Managing game logic, bets, results, and client/game balance updates.</li>
 * <li>Interfacing with the database layer via {@link DataBaseController}.</li>
 * <li>Maintaining the current user session via {@link Session}.</li>
 * <li>Performing input validation through {@link Validator}.</li>
 * </ul>
 * </p>
 * 
 * @author Davitroon
 * @since 3.0
 */
public class MainController {

	private Validator validator;
	private ViewController viewController;
	private Session session;
	private DataBaseController dbController;

	private double lastBet;

	/**
	 * Constructs the MainController with a session.
	 *
	 * @param session The current session object.
	 * @since 3.0
	 */
	public MainController(Session session) {
		this.session = session;
	}

	/**
	 * Initializes essential components: Validator, DatabaseController, and
	 * ViewController.
	 *
	 * @param dbManager Database manager used to initialize the database controller.
	 * @since 3.3
	 */
	public void initializeClasses(DatabaseManager dbManager) {
		validator = new Validator();
		dbController = new DataBaseController(this, dbManager);
		viewController = new ViewController();
		viewController.initializeClasses(this, dbManager);
	}

	// ---------------------- DATABASE / DATA ACCESS METHODS ----------------------

	/**
	 * Updates the balances of a client and a game after a match and stores the
	 * session.
	 *
	 * @param client     The client whose balance is to be updated.
	 * @param game       The game whose balance is to be updated.
	 * @param betResult  Amount won or lost (positive for win, negative for loss).
	 * @param gameClosed True if the game was closed prematurely, false otherwise.
	 * @since 3.0
	 */
	public void updateBalances(Client client, Game game, double betResult, boolean gameClosed) {
		double clientAdjustment = gameClosed ? (-1 * betResult) : betResult;
		double gameAdjustment = gameClosed ? betResult : (-1 * betResult);

		client.setBalance(client.getBalance() + clientAdjustment);
		game.setMoney(game.getMoney() + gameAdjustment);

		dbController.updateClientBalance(client);
		dbController.updateGameBalance(game);
		dbController.addGameSession(client, game, betResult);
	}

	/**
	 * Closes the program safely by closing the DB connection and exiting.
	 * 
	 * @since 3.0
	 */
	public void closeProgram() {
		dbController.closeConnection();
		System.exit(0);
	}

	// -------------------------- GAME LOGIC METHODS --------------------------

	/**
	 * Prompts the client for a valid bet, with options to repeat the last bet or
	 * cancel.
	 *
	 * @param client The client making the bet.
	 * @param game   The game in which the bet is placed.
	 * @return The validated bet amount, or 0 if canceled.
	 * @throws BetException If the client or game balance is insufficient or the
	 *                      input is invalid.
	 * @since 3.0
	 */
	public double promptBet(Client client, Game game) throws BetException {
		validator.validateMinimumBalances(client.getBalance(), game.getMoney());

		while (true) {
			JTextField betField = new JTextField();
			Object[] message = { "Enter a bet:", betField };

			String[] options = { "Cancel", "Repeat Last Bet", "Accept" };
			int option = JOptionPane.showOptionDialog(null, message, "Bet", JOptionPane.DEFAULT_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

			if (option == 0 || option == JOptionPane.CLOSED_OPTION) {
				return 0;
			} else if (option == 1) {
				if (lastBet == 0) {
					JOptionPane.showMessageDialog(null, "There is no previous bet.");
					continue;
				}
				validator.validateBet(String.valueOf(lastBet), client.getBalance(), game.getMoney());
				return lastBet;
			} else {
				try {
					validator.validateBet(betField.getText(), client.getBalance(), game.getMoney());
					lastBet = Double.parseDouble(betField.getText());
					return lastBet;
				} catch (BetException ex) {
					throw ex;
				}
			}
		}
	}

	/**
	 * Warns the user when attempting to close a game mid-match.
	 *
	 * @param client The client playing.
	 * @param game   The game being played.
	 * @param bet    The bet amount for the match.
	 * @return True if the user confirms exit, false otherwise.
	 * @since 3.0
	 */
	public boolean warnCloseGame(Client client, Game game, double bet) {
		int response = JOptionPane.showConfirmDialog(null,
				"Are you sure you want to quit? This will count as a loss for the current match.", "Confirmation",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (response == JOptionPane.YES_OPTION) {
			updateBalances(client, game, bet, true);
			return true;
		}
		return false;
	}

	/**
	 * Displays a generic end-of-game window to prompt the user for next action.
	 *
	 * @param statusMessage Message describing the end of the match.
	 * @return User choice (0: Go Back, 1: Bet Again)
	 * @since 3.0
	 */
	public int gameEndStatus(String statusMessage) {
		String[] options = { "Go Back", "Bet Again" };
		return JOptionPane.showOptionDialog(null, statusMessage + " What do you want to do?", "End of Match",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	}

	// --------------------- BLACKJACK GAME LOGIC METHODS ---------------------

	/**
	 * Initializes a blackjack match by shuffling and dealing cards to dealer and
	 * player.
	 *
	 * @param blackjack Blackjack match object.
	 * @since 3.0
	 */
	public void startBlackjack(Blackjack blackjack) {
		blackjack.shuffleCards();
		blackjack.dealCards(2, "dealer");
		blackjack.dealCards(2, "player");
	}

	/**
	 * Executes a hit action for the player.
	 *
	 * @param blackjack Blackjack object.
	 * @return True if the player busts, false otherwise.
	 * @since 3.0
	 */
	public boolean blackjackHit(Blackjack blackjack) {
		blackjack.dealCards(1, "player");
		return blackjack.playerLoses("player");
	}

	/**
	 * Executes the stand action in Blackjack and resolves the dealer's moves.
	 *
	 * @param blackjack Blackjack object.
	 * @param window    UI window to update dealer cards.
	 * @return True if the client wins, false if the client loses.
	 * @since 3.0
	 */
	public boolean blackjackStand(Blackjack blackjack, BlackjackUI window) {
		while (blackjack.dealerShouldHit()) {
			blackjack.dealCards(1, "dealer");
			if (blackjack.playerLoses("dealer")) {
				window.updateDealerCards(blackjack.showCards(false, "dealer"),
						blackjack.sumCards(blackjack.getDealerCards()));
				return true;
			}
		}
		window.updateDealerCards(blackjack.showCards(false, "dealer"), blackjack.sumCards(blackjack.getDealerCards()));
		return false;
	}

	/**
	 * Returns a formatted message with the outcome of a blackjack match.
	 *
	 * @param clientWins True if the player won.
	 * @param client     The client object.
	 * @param blackjack  Blackjack object.
	 * @param betResult  Amount won/lost.
	 * @return Message describing the outcome.
	 * @since 3.0
	 */
	public String blackjackEndStatus(boolean clientWins, Client client, Blackjack blackjack, double betResult) {
		int playerCardsSum = blackjack.sumCards(blackjack.getPlayerCards());
		int dealerCardsSum = blackjack.sumCards(blackjack.getDealerCards());

		if (!clientWins) {
			if (playerCardsSum > 21) {
				return "You busted (exceeded 21), you lost " + String.format("%.2f", betResult) + "$!";
			} else {
				return "You lost " + String.format("%.2f", betResult) + "$!";
			}
		} else {
			if (playerCardsSum == dealerCardsSum) {
				if (playerCardsSum == 21) {
					return "Tie with Blackjack (Push), you won " + String.format("%.2f", betResult) + "$!";
				}
				return "Tie (Push), you won " + String.format("%.2f", betResult) + "$!";
			} else {
				if (playerCardsSum == 21 && blackjack.getPlayerCards().size() == 2) {
					return "Victory with a Blackjack, you won " + String.format("%.2f", betResult) + "$!";
				}
				return "You won " + String.format("%.2f", betResult) + "$!";
			}
		}
	}

	// --------------------- SLOTMACHINE GAME LOGIC METHODS ---------------------

	/**
	 * Returns a formatted message with the outcome of a slot machine match.
	 *
	 * @param client      The client playing.
	 * @param slotmachine Slotmachine object.
	 * @param betResult   Amount won/lost.
	 * @return Message describing the outcome.
	 * @since 3.0
	 */
	public String slotmachineEndStatus(Client client, Slotmachine slotmachine, double betResult) {
		int[] numbers = slotmachine.getNumbers();

		if (numbers[0] == 7 && numbers[1] == 7 && numbers[2] == 7) {
			return String.format("!!Jackpot, you won %.2f$!", betResult);
		}

		if (numbers[0] == numbers[1] && numbers[0] == numbers[2]) {
			return String.format("Triple combination, you won %.2f$!", betResult);
		}

		if (numbers[0] == numbers[1] || numbers[0] == numbers[2] || numbers[1] == numbers[2]) {
			return String.format("Double combination, you won %.2f$!", betResult);
		}

		return String.format("No combination... You lost %.2f$.", Math.abs(betResult));
	}

	// ------------------------- TABLE FILLING METHODS -------------------------

	/**
	 * Fills the client table in the Games window with a subset of client information.
	 * <p>
	 * The table will contain the following columns: ID, Name, Active status, Balance.
	 * </p>
	 *
	 * @param rset        ResultSet obtained from querying the clients from the database. 
	 *                    It is expected to have the required columns in the correct order.
	 * @param clientModel The table model that will be filled with the client data.
	 * @since 3.0
	 */
	public void fillClientTable(ResultSet rset, DefaultTableModel clientModel) {
		try {
			do {
				Object[] clientList = new Object[4];
				clientList[0] = rset.getInt(1);              // ID
				clientList[1] = rset.getString(2);           // Name
				clientList[2] = rset.getBoolean(5) ? "YES" : "NO"; // Active
				clientList[3] = rset.getDouble(6);          // Balance

				clientModel.addRow(clientList);
			} while (rset.next());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fills the client table in the Management window with full client details.
	 * <p>
	 * The table will contain the following columns: ID, Name, Age, Gender, Active status, Balance.
	 * </p>
	 *
	 * @param rset        ResultSet obtained from querying the clients from the database. 
	 *                    It is expected to have all columns required for the full client view.
	 * @param clientModel The table model that will be filled with the client data.
	 * @since 3.0
	 */
	public void fillFullClientTable(ResultSet rset, DefaultTableModel clientModel) {
		try {
			do {
				Object[] clientList = new Object[6];
				clientList[0] = rset.getInt(1);              // ID
				clientList[1] = rset.getString(2);           // Name
				clientList[2] = rset.getInt(3);              // Age
				clientList[3] = rset.getString(4);           // Gender
				clientList[4] = rset.getBoolean(5) ? "YES" : "NO"; // Active
				clientList[5] = rset.getDouble(6);          // Balance

				clientModel.addRow(clientList);
			} while (rset.next());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fills the game table with information about the available games.
	 * <p>
	 * The table will contain the following columns: ID, Type, Active status, Money.
	 * </p>
	 *
	 * @param rset      ResultSet obtained from querying the games from the database.
	 *                  It is expected to have the required columns in the correct order.
	 * @param gameModel The table model that will be filled with the game data.
	 * @since 3.0
	 */
	public void fillGameTable(ResultSet rset, DefaultTableModel gameModel) {
		try {
			do {
				Object[] gameList = new Object[4];
				gameList[0] = rset.getInt(1);              // ID
				gameList[1] = rset.getString(2);           // Type
				gameList[2] = rset.getBoolean(3) ? "YES" : "NO"; // Active
				gameList[3] = rset.getDouble(4);           // Money

				gameModel.addRow(gameList);
			} while (rset.next());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	// ----------------------------- GETTERS / SETTERS -----------------------------

	public ViewController getViewController() {
		return viewController;
	}

	public DataBaseController getDataBaseController() {
		return dbController;
	}

	public Session getSession() {
		return session;
	}

	public User getCurrentUser() {
		return session.getCurrentUser();
	}

	public Validator getValidator() {
		return validator;
	}

	public void updateUser(User user) {
		session.setCurrentUser(user);
	}
}