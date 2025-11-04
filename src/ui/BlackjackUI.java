package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
	private boolean currentlyPlaying;
	private ViewController viewController;

	private JComponent selectedBet;

	private JButton btn1Bet;
	private JButton btn5Bet;
	private JButton btn25Bet;

	private JButton btnCustomBet;
	private JButton btn50Bet;

	private JTextField textCustomBet;

	private JButton btnPlay;

	private JLabel lblBetResult;

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
		setBackground(new Color(220, 220, 220));

		JLabel lblBlackjack = new JLabel("Blackjack");
		lblBlackjack.setFont(new Font("Segoe UI Black", Font.BOLD, 36));
		lblBlackjack.setHorizontalAlignment(SwingConstants.CENTER);
		lblBlackjack.setBounds(138, 21, 525, 50);
		add(lblBlackjack);

		lblDealerCards = new JLabel("lorem");
		lblDealerCards.setHorizontalAlignment(SwingConstants.CENTER);
		lblDealerCards.setFont(new Font("Segoe UI Black", Font.BOLD, 16));
		lblDealerCards.setBounds(10, 106, 782, 30);
		add(lblDealerCards);

		lblYourCards = new JLabel("lorem");
		lblYourCards.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourCards.setFont(new Font("Segoe UI Black", Font.BOLD, 16));
		lblYourCards.setBounds(10, 179, 782, 30);
		add(lblYourCards);

		JButton btnBack = new JButton("Back");
		btnBack.setForeground(Color.WHITE);
		btnBack.setFocusPainted(false);
		btnBack.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnBack.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		btnBack.setBackground(new Color(128, 128, 128));
		btnBack.setBounds(10, 386, 132, 36);
		add(btnBack);

		btnPlay = new JButton("Play");
		btnPlay.setForeground(Color.WHITE);
		btnPlay.setFocusPainted(false);
		btnPlay.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnPlay.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnPlay.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startGame();
			}
		});
		btnPlay.setEnabled(false);
		btnPlay.setBackground(new Color(0, 128, 64));
		btnPlay.setBounds(641, 386, 151, 36);
		add(btnPlay);

		lblDealerCardsList = new JLabel("lorem");
		lblDealerCardsList.setHorizontalAlignment(SwingConstants.CENTER);
		lblDealerCardsList.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		lblDealerCardsList.setBounds(10, 138, 782, 30);
		add(lblDealerCardsList);

		lblYourCardsList = new JLabel("lorem");
		lblYourCardsList.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourCardsList.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		lblYourCardsList.setBounds(10, 208, 782, 30);
		add(lblYourCardsList);

		btnHit = new JButton("Hit");
		
		btnHit.setEnabled(false);
		btnHit.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		btnHit.setForeground(Color.WHITE);
		btnHit.setFocusPainted(false);
		btnHit.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnHit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnHit.setBackground(new Color(0, 128, 64));
		btnHit.setBounds(411, 262, 108, 38);
		add(btnHit);

		btnStand = new JButton("Stand");
		btnStand.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		btnStand.setEnabled(false);
		btnStand.setForeground(Color.WHITE);
		btnStand.setFocusPainted(false);
		btnStand.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnStand.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnStand.setBackground(new Color(255, 128, 128));
		btnStand.setBounds(293, 262, 108, 38);
		add(btnStand);

		btn1Bet = createStyledBetButton("1$", 641, 270);
		btn5Bet = createStyledBetButton("5$", 719, 270);
		btn25Bet = createStyledBetButton("25$", 641, 311);
		btn50Bet = createStyledBetButton("50$", 719, 309);
		btnCustomBet = createStyledBetButton("Custom", 641, 350);

		textCustomBet = new JTextField();
		textCustomBet.setEnabled(false);
		textCustomBet.setBounds(729, 350, 63, 25);
		add(textCustomBet);
		textCustomBet.setColumns(10);

		JButton btnInfo = new JButton("?");
		btnInfo.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnInfo.setBackground(new Color(128, 255, 255));
		btnInfo.setBounds(755, 11, 37, 38);
		btnInfo.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		btnInfo.setFocusPainted(false);
		btnInfo.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		add(btnInfo);
		
		lblBetResult = new JLabel("");
		lblBetResult.setHorizontalAlignment(SwingConstants.CENTER);
		lblBetResult.setBounds(220, 312, 374, 14);
		add(lblBetResult);

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
		if (currentlyPlaying) {
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
		currentlyPlaying = false;

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
		lblBetResult.setText(controller.blackjackEndStatus(clientWins, client, blackjack, betResult));

		btnHit.setEnabled(false);
		btnStand.setEnabled(false);
		viewController.alternateBetMenu(true, (selectedBet == btnCustomBet ? textCustomBet : null), btnPlay, btn1Bet,
				btn5Bet, btn25Bet, btn50Bet, btnCustomBet);
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

		Map<JButton, Integer> betOptions = Map.of(btn1Bet, 1, btn5Bet, 5, btn25Bet, 25, btn50Bet, 50);
		Integer value = betOptions.get(selectedBet);

		if (value != null) {
			bet = value;

		} else if (selectedBet == btnCustomBet) {
			try {
				controller.getValidator().validateBet(textCustomBet.getText(), client.getBalance(),
						blackjack.getMoney());
				bet = Integer.parseInt(textCustomBet.getText());
			} catch (BetException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		currentlyPlaying = true;
		controller.startBlackjack(blackjack);
		int dealerHand = blackjack.sumCards(blackjack.getDealerCards());
		int clientHand = blackjack.sumCards(blackjack.getPlayerCards());

		btnHit.setEnabled(true);
		btnStand.setEnabled(true);
		lblBetResult.setText("");
		viewController.alternateBetMenu(false, (selectedBet == btnCustomBet ? textCustomBet : null), btnPlay, btn1Bet,
				btn5Bet, btn25Bet, btn50Bet, btnCustomBet);

		int dealerFirstCardSum = blackjack.getDealerCards().get(0);
		if (dealerFirstCardSum > 10)
			dealerFirstCardSum = 10;

		lblDealerCardsList.setText("(" + ((blackjack.getDealerCards().get(0) == 1 ? "11/1" : dealerFirstCardSum)
				+ ") - " + blackjack.showCards(true, "dealer")));
		lblYourCardsList.setText("(" + clientHand + ") - " + blackjack.showCards(false, "player"));

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

	/**
	 * Creates a visually styled bet button with a predefined look and feel.
	 * <p>
	 * The button will have a gold-on-dark color scheme, custom font, border
	 * styling, and a hand cursor. Clicking the button will automatically highlight
	 * it.
	 * </p>
	 *
	 * @param text The text to display on the button.
	 * @param x    The X coordinate for button placement.
	 * @param y    The Y coordinate for button placement.
	 * @return A JButton instance with the applied styling and behavior.
	 * @since 3.3
	 */
	private JButton createStyledBetButton(String text, int x, int y) {
		JButton button = new JButton(text);
		button.setBounds(x, y, 71, 30);
		button.setFocusPainted(false);
		button.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		button.setForeground(new Color(255, 215, 0)); // dorado
		button.setBackground(new Color(30, 30, 30)); // negro elegante
		button.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 2, true),
						BorderFactory.createEmptyBorder(3, 8, 3, 8)));
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		button.addActionListener(e -> highlightSelectedBet(button));
		add(button);
		return button;
	}

	/**
	 * Highlights the selected bet button and resets the style of the other buttons.
	 * <p>
	 * If the selected button is the custom bet button, it also toggles the
	 * corresponding text field for manual input.
	 * </p>
	 *
	 * @param button The button that was pressed.
	 * @since 3.3
	 */
	private void highlightSelectedBet(JButton button) {
		// Reset style of the previos selected bet
		if (selectedBet != null) {
			selectedBet.setBackground(new Color(30, 30, 30));
			selectedBet.setForeground(new Color(255, 215, 0));
		}

		// If the choosen button is the same, disable it
		if (selectedBet == button) {
			selectedBet = null;
			btnPlay.setEnabled(false);

		} else {
			selectedBet = button;
			button.setBackground(new Color(0, 150, 0));
			button.setForeground(Color.BLACK);
			btnPlay.setEnabled(true);
		}

		if (button == btnCustomBet) {
			textCustomBet.setEnabled(selectedBet == btnCustomBet);

		} else {
			textCustomBet.setEnabled(false);
		}
	}

	/**
	 * Starts or resets the Slot Machine game.
	 * <p>
	 * Updates GUI labels, resets numbers to zero, and enables the spin button.
	 * </p>
	 * 
	 * @since 3.3
	 */
	public void loadUI() {
		textCustomBet.setText("");
		btnHit.setEnabled(false);
		btnStand.setEnabled(false);
		currentlyPlaying = false;
		lblDealerCardsList.setText("");
		lblYourCardsList.setText("");
		lblDealerCards.setText(String.format("Dealer's Hand (%.2f$)", blackjack.getMoney()));
		lblYourCards.setText(String.format("%s's Hand (%.2f$)", client.getName(), client.getBalance()));

		if (selectedBet != null) {
			selectedBet.setBackground(new Color(30, 30, 30));
			selectedBet.setForeground(new Color(255, 215, 0));
			btnPlay.setEnabled(false);
			textCustomBet.setEnabled(false);
			selectedBet = null;
		}
	}
}
