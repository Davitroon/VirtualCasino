package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.MainController;
import controller.ViewController;
import exceptions.BetException;
import exceptions.GameException;
import model.Blackjack;
import model.Client;

/**
 * Window where the {@link Blackjack} game is played.
 * <p>
 * This window allows the user to play a Blackjack game, interact with the
 * dealer, place bets, and visualize card hands. It is integrated with the MVC
 * architecture through {@link MainController} and {@link ViewController}.
 * </p>
 * 
 * @author Davitroon
 * @since 3.0
 */
public class BlackjackUI extends JPanel {

	private static final long serialVersionUID = 1L;

	private MainController controller;
	private PlayUI playUI;
	private JLabel lblDealerCardsList;
	private JLabel lblYourCardsList;
	private JLabel lblYourCards;
	private JLabel lblDealerCards;
	private JButton btnStand;
	private JButton btnHit;

	private Client client;
	private Blackjack blackjack;

	private double bet;
	private JLabel lblCurrentBet;
	private boolean gameFinished;
	private JButton btnBack;
	private JButton btnInfo;
	private ViewController viewController;

	/**
	 * Constructor to create the Blackjack game window.
	 * <p>
	 * Initializes all UI components, configures event listeners, and links the
	 * window to the MVC controllers.
	 * </p>
	 * 
	 * @param controller MainController instance to handle game logic and
	 *                   communication with the model and other UI windows.
	 * @since 3.0
	 */
	public BlackjackUI(MainController controller) {
		viewController = controller.getViewController();
		this.controller = controller;
		playUI = viewController.getPlayUI();

		setBounds(100, 100, 802, 433);
		setLayout(null);

		JLabel lblBlackjack = new JLabel("Blackjack");
		lblBlackjack.setFont(new Font("Stencil", Font.PLAIN, 30));
		lblBlackjack.setHorizontalAlignment(SwingConstants.CENTER);
		lblBlackjack.setBounds(10, 37, 782, 30);
		add(lblBlackjack);

		lblDealerCards = new JLabel("lorem");
		lblDealerCards.setHorizontalAlignment(SwingConstants.CENTER);
		lblDealerCards.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDealerCards.setBounds(20, 88, 772, 21);
		add(lblDealerCards);

		lblYourCards = new JLabel("lorem");
		lblYourCards.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourCards.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblYourCards.setBounds(20, 189, 772, 21);
		add(lblYourCards);

		btnBack = new JButton("Back");
		btnBack.setBackground(new Color(128, 128, 128));
		btnBack.setBounds(10, 386, 132, 36);
		add(btnBack);

		lblDealerCardsList = new JLabel("lorem");
		lblDealerCardsList.setHorizontalAlignment(SwingConstants.CENTER);
		lblDealerCardsList.setFont(new Font("VL Gothic", Font.PLAIN, 15));
		lblDealerCardsList.setBounds(20, 120, 772, 30);
		add(lblDealerCardsList);

		lblYourCardsList = new JLabel("lorem");
		lblYourCardsList.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourCardsList.setFont(new Font("VL Gothic", Font.PLAIN, 15));
		lblYourCardsList.setBounds(10, 221, 782, 30);
		add(lblYourCardsList);

		btnHit = new JButton("Hit");
		btnHit.setBackground(new Color(0, 128, 64));
		btnHit.setBounds(413, 305, 108, 38);
		add(btnHit);

		btnStand = new JButton("Stand");
		btnStand.setBackground(new Color(255, 128, 128));
		btnStand.setBounds(287, 305, 108, 38);
		add(btnStand);

		lblCurrentBet = new JLabel("lorem");
		lblCurrentBet.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentBet.setFont(new Font("Tw Cen MT", Font.BOLD, 15));
		lblCurrentBet.setBounds(20, 273, 772, 21);
		add(lblCurrentBet);

		btnInfo = new JButton("?");
		btnInfo.setBackground(new Color(128, 255, 255));
		btnInfo.setBounds(755, 11, 37, 38);
		add(btnInfo);

		// Click on back button
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeWindow();
			}
		});

		// Click on stand button
		btnStand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean clientWins = controller.blackjackStand(blackjack, BlackjackUI.this);
				endGame(clientWins);
			}
		});

		// Click hit button
		btnHit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (controller.blackjackHit(blackjack)) {
					endGame(false);
				}
				lblYourCardsList.setText("(" + blackjack.sumCards(blackjack.getPlayerCards()) + ") "
						+ blackjack.showCards(false, "player"));

				if (blackjack.sumCards(blackjack.getPlayerCards()) == 21)
					endGame(true);
			}
		});

		// Click info button
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = """
						How to play Blackjack?

						- The goal is to get as close to 21 as possible without going over.
						- You and the dealer receive cards at the start.
						- You can ask for more cards ("Hit" button) if you think you won't exceed 21.
						- If you exceed 21, you lose automatically.
						- When you decide to stand ("Stand" button), the dealer will reveal their cards.
						- You win if you have more points than the dealer without going over 21.
						- If you hit 21 with only two cards (an Ace and a face card or a 10), it's a Blackjack!

						If you win, money will be added to your balance. If you lose, ... well, there's always next time.
						Good luck and play smart!
						""";

				JOptionPane.showMessageDialog(null, message, "Blackjack Guide", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	/**
	 * Updates the display of the dealer's card list.
	 * 
	 * @param cards The string representation of the dealer's cards.
	 * @param sum   The sum of the dealer's cards.
	 * @since 3.0
	 */
	public void updateDealerCards(String cards, int sum) {
		lblDealerCardsList.setText(cards + " (" + sum + ")");
	}

	/**
	 * Handles closing the Blackjack window.
	 * <p>
	 * Prompts the user if a game is in progress and ensures balances and tables are
	 * updated before switching windows.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public void closeWindow() {
		if (!gameFinished) {
			if (!controller.warnCloseGame(client, blackjack, bet)) {
				return;
			}
		}

		try {
			playUI.updateTables();
			controller.getViewController().switchPanel(playUI);

		} catch (GameException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * Ends the current Blackjack match.
	 * <p>
	 * Compares player and dealer hands, calculates the result of the bet, updates
	 * balances, and optionally allows starting a new game.
	 * </p>
	 * 
	 * @param clientWins True if the client has won the round, false otherwise.
	 * @since 3.0
	 */
	public void endGame(boolean clientWins) {

		double betResult = blackjack.play(bet);
		boolean goToFinalMessage;
		gameFinished = true;

		// If the winning amount is greater than the game's money pool, the game's money
		// is set directly
		if (blackjack.getMoney() < betResult)
			betResult = blackjack.getMoney();

		controller.updateBalances(client, blackjack, betResult, false);

		lblDealerCardsList.setText(
				"(" + blackjack.sumCards(blackjack.getDealerCards()) + ") " + blackjack.showCards(false, "dealer"));
		lblYourCardsList.setText(
				"(" + blackjack.sumCards(blackjack.getPlayerCards()) + ") " + blackjack.showCards(false, "player"));
		lblDealerCards.setText(String.format("Dealer's Hand (%.2f$)", blackjack.getMoney()));
		lblYourCards.setText(String.format("%s's Hand (%.2f$)", client.getName(), client.getBalance()));

		btnHit.setEnabled(false);
		btnStand.setEnabled(false);

		goToFinalMessage = true;
		do {
			String endStatus = controller.blackjackEndStatus(clientWins, client, blackjack, betResult);
			int choice = controller.gameEndStatus(endStatus); // Translated from 'eleccion'

			if (choice == 0) {
				closeWindow();
				break;
			}

			if (choice == 1) {
				try {
					double newBet = controller.promptBet(client, blackjack);
					if (newBet != 0) {
						bet = newBet;
						goToFinalMessage = false;
						startGame();
					}

				} catch (BetException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					goToFinalMessage = true; // Repeat the loop until a valid bet is entered or canceled
				}
			}
		} while (goToFinalMessage);
	}

	/**
	 * Starts a new Blackjack game.
	 * <p>
	 * Deals cards to the player and dealer, updates UI labels, and handles
	 * automatic wins if 21 is reached.
	 * 
	 * @since 3.0
	 */
	public void startGame() {
		controller.startBlackjack(blackjack);
		int dealerHand = blackjack.sumCards(blackjack.getDealerCards());
		int clientHand = blackjack.sumCards(blackjack.getPlayerCards());
		gameFinished = false;

		lblDealerCards.setText(String.format("Dealer's Hand (%.2f$)", blackjack.getMoney()));
		lblYourCards.setText(String.format("%s's Hand (%.2f$)", client.getName(), client.getBalance()));

		int dealerFirstCardSum = blackjack.getDealerCards().get(0);
		if (dealerFirstCardSum > 10)
			dealerFirstCardSum = 10;
		lblDealerCardsList.setText("(" + ((blackjack.getDealerCards().get(0) == 1 ? "11/1" : dealerFirstCardSum)
				+ ") - " + blackjack.showCards(true, "dealer")));
		lblYourCardsList.setText("(" + clientHand + ") - " + blackjack.showCards(false, "player"));
		lblCurrentBet.setText(String.format("Current Bet: %.2f$", bet));

		btnHit.setEnabled(true);
		btnStand.setEnabled(true);

		if (clientHand == 21)
			endGame(true);
		if (dealerHand == 21)
			endGame(false);
	}

	/**
	 * Initializes the necessary data to start the game.
	 * 
	 * @param client    Current client playing.
	 * @param blackjack Blackjack game instance.
	 * @since 3.3
	 */
	public void initializeData(Client client, Blackjack blackjack) {
		this.client = client;
		this.blackjack = blackjack;
	}
}
