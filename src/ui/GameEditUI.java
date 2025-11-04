package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.MainController;
import controller.ViewController;
import model.Blackjack;
import model.Slotmachine;

/**
 * Window for editing existing game data.
 * <p>
 * This window allows the user to modify a game's type, balance, and active
 * status. It validates the money input and updates the game data in the
 * database upon submission.
 * </p>
 * 
 * @author Davitroon
 * @since 3.0
 */
public class GameEditUI extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField textMoney;
	private boolean moneyValid;
	private JButton btnUpdate;

	private JLabel lblErrorMoney;
	private JComboBox<Object> comboType;
	private JTextField textId;
	private JCheckBox chckbxActive;

	private MainController controller;

	/**
	 * Constructs the GameEditUI window.
	 * <p>
	 * Initializes UI components, sets up listeners for the update and back buttons,
	 * validates inputs, and handles window closing.
	 * </p>
	 * 
	 * @param controller Reference to the MainController handling program logic and
	 *                   data operations.
	 * @since 3.0
	 */
	public GameEditUI(MainController controller) {

		this.controller = controller;
		ViewController viewController = controller.getViewController();
		ManagementUI managementUI = viewController.getManagementUI();

		setBounds(100, 100, 802, 433);
		setLayout(null);
		setBackground(new Color(220, 220, 220));

		JLabel lblEditGame = new JLabel("Edit Game", SwingConstants.CENTER);
		lblEditGame.setFont(new Font("Segoe UI Black", Font.BOLD, 36));
		lblEditGame.setBounds(138, 21, 525, 50);
		add(lblEditGame);

		btnUpdate = new JButton("Update");
		btnUpdate.setBackground(new Color(128, 128, 255));
		btnUpdate.setEnabled(false);
		btnUpdate.setBounds(660, 386, 132, 36);
		btnUpdate.setForeground(Color.WHITE);
		btnUpdate.setFocusPainted(false);
		btnUpdate.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnUpdate.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnUpdate.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		add(btnUpdate);

		JButton btnBack = new JButton("Back");
		btnBack.setForeground(Color.WHITE);
		btnBack.setFocusPainted(false);
		btnBack.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnBack.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		btnBack.setBackground(new Color(128, 128, 128));
		btnBack.setBounds(10, 386, 132, 36);
		add(btnBack);

		JLabel lblType = new JLabel("Type");
		lblType.setFont(new Font("Segoe UI Black", Font.BOLD, 16));
		lblType.setBounds(225, 252, 49, 27);
		add(lblType);

		JLabel lblMoney = new JLabel("Money");
		lblMoney.setFont(new Font("Segoe UI Black", Font.BOLD, 16));
		lblMoney.setBounds(225, 176, 89, 27);
		add(lblMoney);

		textMoney = new JTextField();
		textMoney.setBounds(225, 201, 236, 32);
		add(textMoney);
		textMoney.setColumns(10);

		lblErrorMoney = new JLabel("");
		lblErrorMoney.setForeground(new Color(255, 0, 0));
		lblErrorMoney.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErrorMoney.setBounds(225, 236, 236, 14);
		add(lblErrorMoney);

		comboType = new JComboBox<Object>();
		comboType.setModel(new DefaultComboBoxModel<Object>(new String[] { "Blackjack", "SlotMachine" }));
		comboType.setBounds(225, 280, 111, 22);
		add(comboType);

		JLabel lblId = new JLabel("ID");
		lblId.setFont(new Font("Segoe UI Black", Font.BOLD, 16));
		lblId.setBounds(225, 106, 26, 27);
		add(lblId);

		textId = new JTextField();
		textId.setEnabled(false);
		textId.setEditable(false);
		textId.setBounds(225, 136, 38, 29);
		add(textId);
		textId.setColumns(10);

		chckbxActive = new JCheckBox("Active");
		chckbxActive.setBounds(467, 206, 89, 23);
		add(chckbxActive);

		// When typing in the money field
		textMoney.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				moneyValid = false;
				String text = textMoney.getText();

				if (controller.getValidator().validateGameMoney(text, lblErrorMoney)) {
					moneyValid = true;
				}

				checkForm();
			}
		});

		// Click "Back" button
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearFields();
				viewController.switchPanel(managementUI);
			}
		});

		// Click "Update" button
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id = Integer.parseInt(textId.getText());
				String type = String.valueOf(comboType.getSelectedItem());
				boolean active = chckbxActive.isSelected() ? true : false;
				Double money = Double.parseDouble(textMoney.getText());

				if (type.equalsIgnoreCase("Blackjack")) {
					controller.getDataBaseController().updateGame(new Blackjack(id, type, active, money));
				}

				if (type.equalsIgnoreCase("SlotMachine")) {
					controller.getDataBaseController().updateGame(new Slotmachine(id, type, active, money));
				}

				clearFields();
				managementUI.updateGamesTable();
				managementUI.updateClientsTable();
				viewController.switchPanel(managementUI);
			}
		});
	}

	/**
	 * Loads the original game data into the form fields for editing.
	 * 
	 * @param gameId ID of the game to retrieve from the database
	 * @since 3.0
	 */
	public void loadOriginalGame(int gameId) {
		ResultSet rset = controller.getDataBaseController().queryGame(gameId);

		try {
			rset.next();
			textId.setText(String.valueOf(gameId));

			switch (rset.getString(2)) {
			case "Blackjack":
				comboType.setSelectedIndex(0);
				break;

			case "SlotMachine":
				comboType.setSelectedIndex(1);
				break;
			}

			if (rset.getBoolean(3))
				chckbxActive.setSelected(true);
			textMoney.setText(String.valueOf(rset.getString(4)));

		} catch (SQLException e) {
			e.printStackTrace();
		}

		moneyValid = true;
		checkForm();
	}

	/**
	 * Clears all form fields and resets validation states.
	 * 
	 * @since 3.0
	 */
	public void clearFields() {
		btnUpdate.setEnabled(false);
		textMoney.setText("");
		comboType.setSelectedIndex(0);
		lblErrorMoney.setText("");
	}

	/**
	 * Checks if all required fields are valid to enable the update button.
	 * 
	 * @since 3.0
	 */
	public void checkForm() {
		if (moneyValid) {
			btnUpdate.setEnabled(true);
			return;
		}

		btnUpdate.setEnabled(false);
	}
}