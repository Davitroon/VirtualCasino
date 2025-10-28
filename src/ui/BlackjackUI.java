package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.MainController;
import controller.ViewController;
import exceptions.BetException;
import exceptions.GameException;
import model.Blackjack;
import model.Client;
import model.Game;

/**
 * Window where Blackjack will be played.
 * @author Davitroon
 * @since 3.0
 */
public class BlackjackUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

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
	 * Constructor for the Blackjack game window. Initializes UI components and
	 * links the window to the MVC architecture elements and the current game
	 * session data.
	 * 
	 * @param controller The Controller instance used to manage game logic, state
	 *                   updates, and window changes.
	 * @param model      The Model instance providing access to database and overall
	 *                   application data.
	 * @param play       The parent 'Play' window (PlayUI) to return to when the
	 *                   game is closed.
	 * @since 3.0
	 */
	public BlackjackUI(MainController controller) {
		viewController = controller.getViewController();
		setResizable(false);

		this.controller = controller;
		playUI = viewController.getPlayUI();

		setBounds(100, 100, 577, 442);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblBlackjack = new JLabel("Blackjack");
		lblBlackjack.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblBlackjack.setHorizontalAlignment(SwingConstants.CENTER);
		lblBlackjack.setBounds(48, 36, 473, 30);
		contentPane.add(lblBlackjack);

		lblDealerCards = new JLabel("lorem");
		lblDealerCards.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDealerCards.setBounds(48, 88, 473, 21);
		contentPane.add(lblDealerCards);

		lblYourCards = new JLabel("lorem");
		lblYourCards.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblYourCards.setBounds(48, 189, 473, 21);
		contentPane.add(lblYourCards);

		btnBack = new JButton("Back");
		btnBack.setBackground(new Color(128, 128, 128));
		btnBack.setBounds(22, 349, 95, 30);
		contentPane.add(btnBack);

		lblDealerCardsList = new JLabel("lorem");
		lblDealerCardsList.setFont(new Font("VL Gothic", Font.PLAIN, 15));
		lblDealerCardsList.setBounds(48, 120, 473, 30);
		contentPane.add(lblDealerCardsList);

		lblYourCardsList = new JLabel("lorem");
		lblYourCardsList.setFont(new Font("VL Gothic", Font.PLAIN, 15));
		lblYourCardsList.setBounds(48, 221, 473, 30);
		contentPane.add(lblYourCardsList);

		btnHit = new JButton("Hit");
		btnHit.setBackground(new Color(0, 128, 64));
		btnHit.setBounds(298, 305, 108, 38);
		contentPane.add(btnHit);

		btnStand = new JButton("Stand");
		btnStand.setBackground(new Color(255, 128, 128));
		btnStand.setBounds(172, 305, 108, 38);
		contentPane.add(btnStand);

		lblCurrentBet = new JLabel("lorem");
		lblCurrentBet.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentBet.setFont(new Font("Tw Cen MT", Font.BOLD, 15));
		lblCurrentBet.setBounds(48, 273, 473, 21);
		contentPane.add(lblCurrentBet);

		btnInfo = new JButton("?");
		btnInfo.setBackground(new Color(128, 255, 255));
		btnInfo.setBounds(514, 11, 37, 38);
		contentPane.add(btnInfo);

		// When closing the window using the X
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeWindow();
			}
		});

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
	 * Updates the dealer's card list.
	 * 
	 * @param cards Their cards.
	 * @param sum   The sum of their cards.
	 */
	public void updateDealerCards(String cards, int sum) {
		lblDealerCardsList.setText(cards + " (" + sum + ")");
	}

	/**
	 * Method that calls the controller to show a warning message when attempting to
	 * close the window.
	 */
	public void closeWindow() {		
		if (!gameFinished) {
			if (!controller.warnCloseGame(client, blackjack, bet)) {
				return;
			}
		}

		try {
			playUI.updateTables();
			controller.getViewController().switchWindow(this, playUI);

		} catch (GameException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * Method to finalize a Blackjack match, comparing the client's and dealer's
	 * hands and adjusting their balances based on the bet.
	 * 
	 * @param clientWins True if the client has won and false otherwise.
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
	 * Method to start a Blackjack match. Cards are dealt to each player and shown.
	 * If the player gets 21, they win automatically.
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
	
	public void initializeData(Client client, Blackjack blackjack, double bet) {
		this.client = client;
		this.blackjack = blackjack;
		this.bet = bet;
	}
}
