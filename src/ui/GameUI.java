package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import logic.Blackjack;
import logic.Controller;
import logic.Model;
import logic.Slotmachine;
import logic.Validator;
import ui.GameUI;
import ui.ManagementUI;

/**
 * Window for the game form.
 * 
 * @author David
 * @since 3.0
 */
public class GameUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private JTextField textMoney;

	private boolean moneyValid;
	private JButton btnAdd;

	private JLabel lblErrorMoney;
	private JComboBox<Object> comboType;

	/**
	 * Constructor for the game form window
	 * 
	 * @param management Reference to the management window.
	 * @param controller Reference to the controller handling logic.
	 * @param model      Reference to the model managing data operations.
	 * @param validator  Reference to the validator for input checks.
	 * @since 3.0
	 */
	public GameUI(ManagementUI management, Controller controller, Model model, Validator validator) {

		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 414, 314);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblAddGame = new JLabel("Add Game", SwingConstants.CENTER);
		lblAddGame.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblAddGame.setBounds(10, 21, 387, 39);
		contentPane.add(lblAddGame);

		btnAdd = new JButton("Add");
		btnAdd.setBackground(new Color(128, 128, 255));
		btnAdd.setEnabled(false);
		btnAdd.setBounds(270, 228, 111, 32);
		contentPane.add(btnAdd);

		JButton btnBack = new JButton("Back");
		btnBack.setBackground(new Color(128, 128, 128));
		btnBack.setBounds(20, 228, 111, 32);
		contentPane.add(btnBack);

		JLabel lblType = new JLabel("Type");
		lblType.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblType.setBounds(36, 84, 49, 14);
		contentPane.add(lblType);

		JLabel lblMoney = new JLabel("Money");
		lblMoney.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMoney.setBounds(36, 146, 49, 14);
		contentPane.add(lblMoney);

		textMoney = new JTextField();
		textMoney.setBounds(97, 137, 182, 32);
		contentPane.add(textMoney);
		textMoney.setColumns(10);

		lblErrorMoney = new JLabel("");
		lblErrorMoney.setForeground(new Color(255, 0, 0));
		lblErrorMoney.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErrorMoney.setBounds(36, 172, 233, 14);
		contentPane.add(lblErrorMoney);

		comboType = new JComboBox<Object>();
		comboType.setModel(new DefaultComboBoxModel<Object>(new String[] { "Blackjack", "SlotMachine" }));
		comboType.setBounds(97, 80, 111, 22);
		contentPane.add(comboType);

		// When typing in the money field
		textMoney.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				moneyValid = false;
				String text = textMoney.getText();

				if (validator.validateGameMoney(text, lblErrorMoney)) {
					moneyValid = true;
				}

				checkForm();
			}
		});

		// Click "Back" button
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearFields();
				controller.switchWindow(GameUI.this, management);
			}
		});

		// Click "Add" button
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Double money = Double.parseDouble(textMoney.getText());
				String type = String.valueOf(comboType.getSelectedItem());

				if (type.equalsIgnoreCase("Blackjack")) {
					Blackjack blackjack = new Blackjack(money);
					model.addGame(blackjack);
				}

				if (type.equalsIgnoreCase("SlotMachine")) {
					Slotmachine slotMachine = new Slotmachine(money);
					model.addGame(slotMachine);
				}

				clearFields();
				controller.switchWindow(GameUI.this, management);
			}
		});

		// When closing the window using the X
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				clearFields();
				controller.switchWindow(GameUI.this, management);
			}
		});
	}

	/**
	 * Method to clear all fields in the form.
	 * 
	 * @since 3.0
	 */
	public void clearFields() {
		btnAdd.setEnabled(false);
		textMoney.setText("");
		comboType.setSelectedIndex(0);
		lblErrorMoney.setText("");
	}

	/**
	 * Method to check that the user has filled in all form fields.
	 * 
	 * @since 3.0
	 */
	public void checkForm() {
		if (moneyValid) {
			btnAdd.setEnabled(true);
			return;
		}

		btnAdd.setEnabled(false);
	}
}