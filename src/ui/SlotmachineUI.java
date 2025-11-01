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
import model.Client;
import model.Slotmachine;

/**
 * Window where the {@link Slotmachine} game is played.
 * <p>
 * Displays the slot machine interface with three number slots, spin button,
 * current bet, client balance, and game balance. Allows the player to play
 * the game, view instructions, and navigate back to the previous window.
 * </p>
 * 
 * @author David
 * @since 3.0
 */
public class SlotmachineUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private MainController controller;
	private PlayUI playUI;
	private JButton btnSpin;

	private Client client;
	private Slotmachine slotMachine;

	private double bet;
	private boolean gameFinished;
	private JLabel lblNum1;
	private JLabel lblNum2;
	private JLabel lblNum3;
	private JLabel lblCurrentBet;
	private JLabel lblClient;
	private JLabel lblGame;
	private JButton btnBack;

	private ViewController viewController;

	/**
	 * Constructs the SlotmachineUI window.
	 * <p>
	 * Initializes all GUI components, buttons, labels, and event listeners
	 * required to play a slot machine game.
	 * </p>
	 * 
	 * @param controller The main controller handling game logic and window changes.
	 * @since 3.0
	 */
	public SlotmachineUI(MainController controller) {

		this.controller = controller;
		viewController = controller.getViewController();
		playUI = viewController.getPlayUI();

		setResizable(false);
		setBounds(100, 100, 523, 421);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSlotMachine = new JLabel("Slot Machine");
		lblSlotMachine.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblSlotMachine.setHorizontalAlignment(SwingConstants.CENTER);
		lblSlotMachine.setBounds(49, 56, 421, 30);
		contentPane.add(lblSlotMachine);

		btnSpin = new JButton("Spin");
		btnSpin.setBackground(new Color(0, 128, 64));
		btnSpin.setBounds(391, 319, 108, 38);
		contentPane.add(btnSpin);

		lblNum1 = new JLabel("0");
		lblNum1.setBackground(new Color(0, 64, 0));
		lblNum1.setFont(new Font("Cambria Math", Font.BOLD, 18));
		lblNum1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNum1.setBounds(153, 118, 62, 38);
		contentPane.add(lblNum1);

		lblNum2 = new JLabel("0");
		lblNum2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNum2.setFont(new Font("Cambria Math", Font.BOLD, 18));
		lblNum2.setBounds(225, 118, 62, 38);
		contentPane.add(lblNum2);

		lblNum3 = new JLabel("0");
		lblNum3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNum3.setFont(new Font("Cambria Math", Font.BOLD, 18));
		lblNum3.setBounds(297, 118, 62, 38);
		contentPane.add(lblNum3);

		lblCurrentBet = new JLabel("lorem");
		lblCurrentBet.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentBet.setFont(new Font("Tw Cen MT", Font.BOLD, 15));
		lblCurrentBet.setBounds(10, 287, 489, 21);
		contentPane.add(lblCurrentBet);

		lblClient = new JLabel("lorem");
		lblClient.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblClient.setBounds(10, 224, 304, 21);
		contentPane.add(lblClient);

		lblGame = new JLabel("lorem");
		lblGame.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblGame.setBounds(10, 192, 304, 21);
		contentPane.add(lblGame);

		btnBack = new JButton("Back");
		btnBack.setBackground(new Color(128, 128, 128));
		btnBack.setBounds(10, 319, 108, 38);
		contentPane.add(btnBack);

		JButton btnInfo = new JButton("?");
		btnInfo.setBackground(new Color(128, 255, 255));
		btnInfo.setBounds(462, 11, 37, 35);
		contentPane.add(btnInfo);

		addWindowListener(new WindowAdapter() {
			// When closing the window using the X button
			@Override
			public void windowClosing(WindowEvent e) {
				closeWindow();
			}
		});

		// Click Spin button
		btnSpin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				slotMachine.generateNumbers();
				lblNum1.setText(String.valueOf(slotMachine.getNumbers()[0]));
				lblNum2.setText(String.valueOf(slotMachine.getNumbers()[1]));
				lblNum3.setText(String.valueOf(slotMachine.getNumbers()[2]));
				endGame();
			}
		});

		// Click Back button
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeWindow();
			}
		});

		// Click Info button
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// A triple-quoted string allows formatting with line breaks and spaces
				String message = """
						How to play the Slot Machine?

						- First, place a bet using your available balance.
						- Then press the "Spin" button to generate three random numbers between 1 and 9.
						- The numbers will appear on the screen immediately.

						How is winning determined?
						- If there are no matching numbers, you lose your bet.
						- If two numbers are the same, you win 1.9 times your bet.
						- If all three numbers are the same, you win 3.5 times your bet.
						- If all three numbers are 7, you win 6.5 times your bet.

						Keep in mind that this game is completely random.
						""";

				JOptionPane.showMessageDialog(null, message, "Slot Machine Guide", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	/**
	 * Closes the Slot Machine window safely.
	 * <p>
	 * Calls the controller to warn about unsaved game state and updates tables
	 * before switching back to the PlayUI window.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public void closeWindow() {
		if (!gameFinished) {
			if (!controller.warnCloseGame(client, slotMachine, bet)) {
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
	 * Ends the current Slot Machine game.
	 * <p>
	 * Calculates winnings/losses, updates client and game balances, disables the
	 * spin button, and manages end-of-game options.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public void endGame() {
		double resultBet = slotMachine.play(bet);
		boolean showFinalMessage;
		gameFinished = true;

		// If the bet is higher than the game's balance, use the game's balance instead
		if (slotMachine.getMoney() < resultBet)
			resultBet = slotMachine.getMoney();

		controller.updateBalances(client, slotMachine, resultBet, false);
		btnSpin.setEnabled(false);
		lblClient.setText(String.format("%s's Balance: %.2f$", client.getName(), client.getBalance()));
		lblGame.setText(String.format("Game Money: %.2f$", slotMachine.getMoney()));

		showFinalMessage = true;
		do {
			String endState = controller.slotmachineEndStatus(client, slotMachine, resultBet);
			int choice = controller.gameEndStatus(endState);

			if (choice == 0) {
				closeWindow();
				break;
			}

			if (choice == 1) {
				try {
					double newBet = controller.promptBet(client, slotMachine);
					if (newBet != 0) {
						bet = newBet;
						showFinalMessage = false;
						startGame();
					}

				} catch (BetException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					showFinalMessage = true; // Repeat the loop until a valid bet is entered or cancelled
				}
			}
		} while (showFinalMessage);
	}

	/**
	 * Starts or resets the Slot Machine game.
	 * <p>
	 * Updates GUI labels, resets numbers to zero, and enables the spin button.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public void startGame() {
		btnSpin.setEnabled(true);
		lblNum1.setText("0");
		lblNum2.setText("0");
		lblNum3.setText("0");
		lblCurrentBet.setText(String.format("Current Bet: %.2f$", bet));
		lblClient.setText(String.format("%s's Balance: %.2f$", client.getName(), client.getBalance()));
		lblGame.setText(String.format("Game Money: %.2f$", slotMachine.getMoney()));
	}

	/**
	 * Initializes the Slot Machine game data.
	 * 
	 * @param client      The client playing the game.
	 * @param slotmachine The Slotmachine game instance.
	 * @param bet         The current bet amount.
	 * @since 3.3
	 */
	public void initializeData(Client client, Slotmachine slotmachine, double bet) {
		this.client = client;
		this.slotMachine = slotmachine;
		this.bet = bet;
	}

}