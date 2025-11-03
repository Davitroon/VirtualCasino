package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

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
 * current bet, client balance, and game balance. Allows the player to play the
 * game, view instructions, and navigate back to the previous window.
 * </p>
 * 
 * @author David
 * @since 3.0
 */
public class SlotmachineUI extends JPanel {

	private static final long serialVersionUID = 1L;

	private MainController controller;
	private PlayUI playUI;
	private JButton btnSpin;

	private Client client;
	private Slotmachine slotMachine;

	private double bet;
	private JLabel lblNum1;
	private JLabel lblNum2;
	private JLabel lblNum3;
	private JLabel lblClient;
	private JLabel lblGame;
	private JButton btnBack;

	private ViewController viewController;
	private JButton btn1Bet;
	private JButton btn5Bet;
	private JButton btn25Bet;
	private JButton btn50Bet;
	private JTextField textCustomBet;

	private JButton selectedBet;

	private JButton btnCustomBet;
	private JLabel lblBetResult;

	private double resultBet;

	/**
	 * Constructs the SlotmachineUI window.
	 * <p>
	 * Initializes all GUI components, buttons, labels, and event listeners required
	 * to play a slot machine game.
	 * </p>
	 * 
	 * @param controller The main controller handling game logic and window changes.
	 * @since 3.0
	 */
	public SlotmachineUI(MainController controller) {

		this.controller = controller;
		viewController = controller.getViewController();
		playUI = viewController.getPlayUI();

		setBounds(100, 100, 802, 433);
		setLayout(null);

		JLabel lblSlotMachine = new JLabel("Slot Machine");
		lblSlotMachine.setFont(new Font("Stencil", Font.PLAIN, 30));
		lblSlotMachine.setHorizontalAlignment(SwingConstants.CENTER);
		lblSlotMachine.setBounds(10, 56, 782, 30);
		add(lblSlotMachine);

		btnSpin = new JButton("Spin");
		btnSpin.setEnabled(false);
		btnSpin.setBackground(new Color(0, 128, 64));
		btnSpin.setBounds(641, 386, 151, 36);
		add(btnSpin);

		lblNum1 = createSlotLabel();
		lblNum1.setBounds(293, 120, 80, 80);
		add(lblNum1);

		lblNum2 = createSlotLabel();
		lblNum2.setBounds(370, 120, 80, 80);
		add(lblNum2);

		lblNum3 = createSlotLabel();
		lblNum3.setBounds(450, 120, 80, 80);
		add(lblNum3);

		lblClient = new JLabel("lorem");
		lblClient.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblClient.setBounds(10, 224, 304, 21);
		add(lblClient);

		lblGame = new JLabel("lorem");
		lblGame.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblGame.setBounds(10, 192, 304, 21);
		add(lblGame);

		btnBack = new JButton("Back");
		btnBack.setBackground(new Color(128, 128, 128));
		btnBack.setBounds(10, 386, 132, 36);
		add(btnBack);

		JButton btnInfo = new JButton("?");
		btnInfo.setBackground(new Color(128, 255, 255));
		btnInfo.setBounds(755, 11, 37, 35);
		add(btnInfo);

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

		lblBetResult = new JLabel("");
		lblBetResult.setHorizontalAlignment(SwingConstants.CENTER);
		lblBetResult.setBounds(226, 280, 357, 14);
		add(lblBetResult);

		// Click Spin button
		btnSpin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Map<JButton, Integer> betOptions = Map.of(btn1Bet, 1, btn5Bet, 5, btn25Bet, 25, btn50Bet, 50);
				Integer value = betOptions.get(selectedBet);

				if (value != null) {
					bet = value;

				} else if (selectedBet == btnCustomBet) {
					try {
						controller.getValidator().validateBet(textCustomBet.getText(), client.getBalance(),
								slotMachine.getMoney());
						bet = Integer.parseInt(textCustomBet.getText());

					} catch (BetException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}

				viewController.alternateBetMenu(false, (selectedBet == btnCustomBet ? textCustomBet : null), btnSpin, btn1Bet,
						btn5Bet, btn25Bet, btn50Bet, btnCustomBet);
				lblBetResult.setText("");
				animateSpin();
				// Espera un poco y luego genera el resultado real
				new javax.swing.Timer(1200, ev -> {
					slotMachine.generateNumbers();
					lblNum1.setText(String.valueOf(slotMachine.getNumbers()[0]));
					lblNum2.setText(String.valueOf(slotMachine.getNumbers()[1]));
					lblNum3.setText(String.valueOf(slotMachine.getNumbers()[2]));
					endGame();
					((javax.swing.Timer) ev.getSource()).stop();
				}).start();
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
		try {
			playUI.updateTables();
			controller.getViewController().switchPanel(playUI);

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
		resultBet = slotMachine.play(bet);

		// If the bet is higher than the game's balance, use the game's balance instead
		if (slotMachine.getMoney() < resultBet)
			resultBet = slotMachine.getMoney();

		controller.updateBalances(client, slotMachine, resultBet, false);
		lblClient.setText(String.format("%s's Balance: %.2f$", client.getName(), client.getBalance()));
		lblGame.setText(String.format("Game Money: %.2f$", slotMachine.getMoney()));
		viewController.alternateBetMenu(true, (selectedBet == btnCustomBet ? textCustomBet : null), btnSpin, btn1Bet,
				btn5Bet, btn25Bet, btn50Bet, btnCustomBet);
		lblBetResult.setText(controller.slotmachineEndStatus(client, slotMachine, resultBet));
	}

	/**
	 * Starts or resets the Slot Machine game.
	 * <p>
	 * Updates GUI labels, resets numbers to zero, and enables the spin button.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public void loadUI() {
		lblNum1.setText("0");
		lblNum2.setText("0");
		lblNum3.setText("0");
		lblClient.setText(String.format("%s's Balance: %.2f$", client.getName(), client.getBalance()));
		lblGame.setText(String.format("Game Money: %.2f$", slotMachine.getMoney()));
		lblBetResult.setText("");
		textCustomBet.setText("");

		if (selectedBet != null) {
			selectedBet.setBackground(new Color(30, 30, 30));
			selectedBet.setForeground(new Color(255, 215, 0));
			btnSpin.setEnabled(false);
			textCustomBet.setEnabled(false);
			selectedBet = null;
		}
	}

	/**
	 * Initializes the Slot Machine game data.
	 * 
	 * @param client      The client playing the game.
	 * @param slotmachine The Slotmachine game instance.
	 * @since 3.3
	 */
	public void initializeData(Client client, Slotmachine slotmachine) {
		this.client = client;
		this.slotMachine = slotmachine;
	}

	/**
	 * Creates and styles a slot label for the slot machine interface.
	 *
	 * @return A fully styled {@code JLabel} component initialized with the value
	 *         "0".
	 * @since 3.3
	 */
	private JLabel createSlotLabel() {
		JLabel label = new JLabel("0", SwingConstants.CENTER);
		label.setOpaque(true);
		label.setBackground(new Color(30, 30, 30));
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Digital-7 Mono", Font.BOLD, 36));
		label.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3, true));
		label.setPreferredSize(new Dimension(80, 80));
		return label;
	}

	/**
	 * Animates the slot machine spin effect.
	 *
	 * <p>
	 * <b>Note:</b> This method uses {@link Thread#sleep(long)} for timing and
	 * should not be called from the Event Dispatch Thread (EDT).
	 * </p>
	 *
	 * @since 3.3
	 */
	private void animateSpin() {
		new Thread(() -> {
			try {
				for (int i = 0; i < 10; i++) {
					lblNum1.setText(String.valueOf((int) (Math.random() * 9 + 1)));
					lblNum2.setText(String.valueOf((int) (Math.random() * 9 + 1)));
					lblNum3.setText(String.valueOf((int) (Math.random() * 9 + 1)));
					Thread.sleep(100);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
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
		if (selectedBet != null) {
			selectedBet.setBackground(new Color(30, 30, 30));
			selectedBet.setForeground(new Color(255, 215, 0));
		}

		if (selectedBet == button) {
			selectedBet = null;
			button.setBackground(new Color(30, 30, 30));
			button.setForeground(new Color(255, 215, 0));
			btnSpin.setEnabled(false);

		} else {
			selectedBet = button;
			button.setBackground(new Color(0, 150, 0));
			button.setForeground(Color.BLACK);
			btnSpin.setEnabled(true);
		}

		if (button == btnCustomBet) {
			textCustomBet.setEnabled(selectedBet == btnCustomBet);

		} else {
			textCustomBet.setEnabled(false);
		}
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
}