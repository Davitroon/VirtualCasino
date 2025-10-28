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
 * This class serves as the central Controller in the application's MVC 
 * architecture. It coordinates the flow of control between the UI (Views) 
 * and the data/logic (Models and DAOs).
 * <ul>
 * <li>Handling user actions from the UI and translating them into application logic.</li>
 * <li>Managing game logic, status updates, and transaction processing.</li>
 * <li>Interfacing with the database layer (via {@link DataBaseController}) and validation layer (via {@link Validator}).</li>
 * <li>Maintaining the current user session state (via {@link Session}).</li>
 * </ul>
 * @author David
 * @since 3.0
 */
public class MainController {

	private Validator validator;
	private ViewController viewController;
	private Session session;
	private DataBaseController dbController;

	private double lastBet;
	
	
	

	public MainController(Session session) {
		this.session = session;
	}

	/**
	 * Initiliaze its essential components.
	 * @param dbManager The central database access manager, used to initialize DataBaseController.
	 */
	public void initializeClasses(DatabaseManager dbManager) {
		validator = new Validator();
		dbController = new DataBaseController(this, dbManager);
		viewController = new ViewController();
		viewController.initializeClasses(this, dbManager);
		
	}

	// ---------------------- DATABASE / DATA ACCESS METHODS ----------------------

	/**
	 * Updates the balance and game money for the client and the game after a match
	 * ends.
	 * 
	 * @param client     Client to update.
	 * @param game       Game to update.
	 * @param betResult  Result of the bet (positive for win, negative/zero for
	 *                   loss).
	 * @param gameClosed True if the game was closed before a bet finished, false if
	 *                   the bet terminated normally.
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

	public void closeProgram() {
		dbController.closeConnection();
		System.exit(0);
	}
	
	// -------------------------- GAME LOGIC METHODS --------------------------
	// (Methods that contain game-specific logic, prompts, and status messages)

	/**
	 * Throws an exception if betting is not possible. Prompts the user for a valid
	 * bet.
	 * 
	 * @param client Client object who will play.
	 * @param game   Game object that will be played.
	 * @return The entered bet, returns 0 if invalid or canceled.
	 * @throws BetException if the client or the game does not have enough money.
	 * @since 3.0
	 */
	public double promptBet(Client client, Game game) throws BetException {

		validator.validateMinimumBalances(client.getBalance(), game.getMoney());

		// Show window
		while (true) {
			JTextField betField = new JTextField();
			Object[] message = { "Enter a bet:", betField };

			String[] options = { "Cancel", "Repeat Last Bet", "Accept" };
			int option = JOptionPane.showOptionDialog(null, message, "Bet", JOptionPane.DEFAULT_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

			// User canceled
			if (option == 0 || option == JOptionPane.CLOSED_OPTION) {
				return 0;

				// Repeat Last Bet
			} else if (option == 1) {
				if (lastBet == 0) {
					JOptionPane.showMessageDialog(null, "There is no previous bet.");
					continue;
				}

				try {
					validator.validateBet(String.valueOf(lastBet), client.getBalance(), game.getMoney());
					return lastBet;
				} catch (BetException ex) {
					throw ex;
				}

				// Normal Bet
			} else {
				String betText = betField.getText();
				try {
					validator.validateBet(betText, client.getBalance(), game.getMoney());
					lastBet = Double.parseDouble(betText);
					return lastBet;

				} catch (BetException ex) {
					throw ex;
				}
			}
		}
	}

	/**
	 * Method that will show the user a warning if they try to close a game without
	 * having finished it.
	 * 
	 * @param client Client playing.
	 * @param game   Game being played.
	 * @param bet    Bet for the match.
	 * @return True if they confirmed exiting, false otherwise.
	 * @since 3.0
	 */
	public boolean warnCloseGame(Client client, Game game, double bet) {
		int response = JOptionPane.showConfirmDialog(null,
				"Are you sure you want to quit? This will count as a loss for the current match.", "Confirmation",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (response == JOptionPane.YES_OPTION) {
			// Assume 'updateBalances' uses the original bet amount as a loss when
			// 'gameClosed' is true
			updateBalances(client, game, bet, true);
			return true;
		}

		return false;
	}
	
	/**
	 * Method that will show a generic end game window for the games.
	 * 
	 * @param statusMessage Custom message depending on the context of the end of
	 *                      the match.
	 * @return Player's choice (Go Back / Bet Again).
	 * @since 3.0
	 */
	public int gameEndStatus(String statusMessage) {
		String[] options = { "Go Back", "Bet Again" };
		int choice = JOptionPane.showOptionDialog(null, statusMessage + " What do you want to do?", "End of Match",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

		return choice;
	}

	// --------------------- BLACKJACK GAME LOGIC METHODS ---------------------

	/**
	 * Starts a blackjack match by shuffling the cards and dealing the cards to the
	 * players.
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
	 * The player hits (asks for a card) in the Blackjack game.
	 * 
	 * @param blackjack Blackjack object.
	 * @return True if the player has busted (exceeded 21), false otherwise.
	 * @since 3.0
	 */
	public boolean blackjackHit(Blackjack blackjack) {
		blackjack.dealCards(1, "player");
		return blackjack.playerLoses("player");
	}

	/**
	 * Handles the Stand action in Blackjack.
	 * 
	 * @param blackjack Blackjack Game.
	 * @param window    Blackjack window (UI).
	 * @return true if the client wins, false if the client loses.
	 */
	public boolean blackjackStand(Blackjack blackjack, BlackjackUI window) {
		while (blackjack.dealerShouldHit()) {
			blackjack.dealCards(1, "dealer");
			if (blackjack.playerLoses("dealer")) {
				// The player has won (dealer busted)
				window.updateDealerCards(blackjack.showCards(false, "dealer"),
						blackjack.sumCards(blackjack.getDealerCards()));
				return true;
			}
		}
		// The player has lost (dealer stands with a higher or equal score)
		window.updateDealerCards(blackjack.showCards(false, "dealer"), blackjack.sumCards(blackjack.getDealerCards()));
		return false;
	}

	/**
	 * * Shows the final result of a blackjack match.
	 * 
	 * @param clientWins True if the player won, false if the player lost.
	 * @param client     Client object who played.
	 * @param blackjack  Blackjack object that was played.
	 * @param betResult  Result of the bet (amount won/lost).
	 * @return Message with the status of the end of the match.
	 * @since 3.0
	 */
	public String blackjackEndStatus(boolean clientWins, Client client, Blackjack blackjack, double betResult) {

		// Assumes 'sumCards' and 'getPlayerCards/getDealerCards' are the translated
		// methods
		int playerCardsSum = blackjack.sumCards(blackjack.getPlayerCards());
		int dealerCardsSum = blackjack.sumCards(blackjack.getDealerCards());

		// Player loses
		if (!clientWins) {
			// Player busts (exceeds 21)
			if (playerCardsSum > 21) {
				return "You busted (exceeded 21), you lost " + String.format("%.2f", betResult) + "$!";

				// Player loses normally (dealer had higher non-busting score)
			} else {
				return "You lost " + String.format("%.2f", betResult) + "$!";
			}

			// Player wins or pushes
		} else {
			// Tie (Push)
			if (playerCardsSum == dealerCardsSum) {
				if (playerCardsSum == 21) {
					return "Tie with Blackjack (Push), you won " + String.format("%.2f", betResult) + "$!";
				}
				return "Tie (Push), you won " + String.format("%.2f", betResult) + "$!";

				// Victory
			} else {
				// Natural Blackjack (21 on first two cards)
				if (playerCardsSum == 21 && blackjack.getPlayerCards().size() == 2) {
					return "Victory with a Blackjack, you won " + String.format("%.2f", betResult) + "$!";
				}
				return "You won " + String.format("%.2f", betResult) + "$!";
			}
		}
	}

	// --------------------- SLOTMACHINE GAME LOGIC METHODS ---------------------

	/**
	 * Shows the final result of a slot machine match.
	 * 
	 * @param client      Client playing.
	 * @param slotmachine Slotmachine class object being played.
	 * @param betResult   Result of the bet (amount won/lost).
	 * @return Message with the result of the match.
	 * @since 3.0
	 */
	public String slotmachineEndStatus(Client client, Slotmachine slotmachine, double betResult) {
		// Assumes 'getNumeros' is 'getNumbers'
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

		// Since 'betResult' is the result (negative for loss), we show the absolute
		// value here.
		return String.format("No combination... You lost %.2f$.", Math.abs(betResult));
	}

	// ------------------------- TABLE FILLING METHODS -------------------------

	/**
	 * Method that fills the client table in the Games window.
	 * 
	 * @param rset        Result of the query to the DB (Database).
	 * @param clientModel Client table model.
	 * @since 3.0
	 */
	public void fillClientTable(ResultSet rset, DefaultTableModel clientModel) {
		try {
			do {
				// "ID", "Name", "Active", "Balance"
				Object[] clientList = new Object[4];
				clientList[0] = rset.getInt(1); // ID
				clientList[1] = rset.getString(2); // Name
				clientList[2] = (rset.getBoolean(5) == true ? "YES" : "NO"); // Active
				clientList[3] = rset.getDouble(6); // Balance

				clientModel.addRow(clientList);
			} while (rset.next());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method that fills the client table in the Management window, with all fields.
	 * 
	 * @param rset        Result of the query to the DB.
	 * @param clientModel Client table model.
	 * @since 3.0
	 */
	public void fillFullClientTable(ResultSet rset, DefaultTableModel clientModel) {
		try {
			do {
				// "ID", "Name", "Age", "Gender", "Active", "Balance" (Based on column indices
				// 1-6)
				Object[] clientList = new Object[6];
				clientList[0] = rset.getInt(1); // ID
				clientList[1] = rset.getString(2); // Name
				clientList[2] = rset.getInt(3); // Age
				clientList[3] = rset.getString(4); // Gender
				clientList[4] = (rset.getBoolean(5) == true ? "YES" : "NO"); // Active
				clientList[5] = rset.getDouble(6); // Balance

				clientModel.addRow(clientList);
			} while (rset.next());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method that fills the game table.
	 * 
	 * @param rset      Result of the query to the DB.
	 * @param gameModel Game table model.
	 * @since 3.0
	 */
	public void fillGameTable(ResultSet rset, DefaultTableModel gameModel) {
		try {
			do  {
				// "ID", "Type", "Active", "Money"
				Object[] gameList = new Object[4];
				gameList[0] = rset.getInt(1); // ID
				gameList[1] = rset.getString(2); // Type
				gameList[2] = (rset.getBoolean(3) == true ? "YES" : "NO"); // Active
				gameList[3] = rset.getDouble(4); // Money

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