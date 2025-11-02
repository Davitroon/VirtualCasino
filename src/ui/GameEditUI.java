package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

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
	private JPanel contentPane;

	private JTextField textMoney;
	private boolean moneyValid;
	private JButton btnUpdate;

	private JLabel lblErrorMoney;
	private JComboBox<Object> comboType;
	private JTextField textId;
	private JLabel lblId;
	private JCheckBox chckbxActive;
	
	private MainController controller;

    /**
     * Constructs the GameEditUI window.
     * <p>
     * Initializes UI components, sets up listeners for the update and back
     * buttons, validates inputs, and handles window closing.
     * </p>
     * 
     * @param controller Reference to the MainController handling program logic
     *                   and data operations.
     * @since 3.0
     */
	public GameEditUI(MainController controller) {

		this.controller = controller;
		ViewController viewController = controller.getViewController();
		ManagementUI managementUI = viewController.getManagementUI();
		
		setBounds(100, 100, 440, 314);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		JLabel lblEditGame = new JLabel("Edit Game", SwingConstants.CENTER);
		lblEditGame.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblEditGame.setBounds(6, 21, 412, 39);
		contentPane.add(lblEditGame);

		btnUpdate = new JButton("Update");
		btnUpdate.setBackground(new Color(128, 128, 255));
		btnUpdate.setEnabled(false);
		btnUpdate.setBounds(292, 228, 111, 32);
		contentPane.add(btnUpdate);

		JButton btnBack = new JButton("Back");
		btnBack.setBackground(new Color(128, 128, 128));
		btnBack.setBounds(24, 228, 111, 32);
		contentPane.add(btnBack);

		JLabel lblType = new JLabel("Type");
		lblType.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblType.setBounds(129, 84, 49, 14);
		contentPane.add(lblType);

		JLabel lblMoney = new JLabel("Money");
		lblMoney.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMoney.setBounds(40, 152, 49, 14);
		contentPane.add(lblMoney);

		textMoney = new JTextField();
		textMoney.setBounds(101, 143, 236, 32);
		contentPane.add(textMoney);
		textMoney.setColumns(10);

		lblErrorMoney = new JLabel("");
		lblErrorMoney.setForeground(new Color(255, 0, 0));
		lblErrorMoney.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErrorMoney.setBounds(40, 178, 297, 14);
		contentPane.add(lblErrorMoney);

		comboType = new JComboBox<Object>();
		comboType.setModel(new DefaultComboBoxModel<Object>(new String[] { "Blackjack", "SlotMachine" }));
		comboType.setBounds(172, 80, 111, 22);
		contentPane.add(comboType);

		lblId = new JLabel("ID");
		lblId.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblId.setBounds(40, 84, 26, 14);
		contentPane.add(lblId);

		textId = new JTextField();
		textId.setEnabled(false);
		textId.setEditable(false);
		textId.setBounds(64, 77, 38, 29);
		contentPane.add(textId);
		textId.setColumns(10);

		chckbxActive = new JCheckBox("Active");
		chckbxActive.setBounds(304, 80, 89, 23);
		contentPane.add(chckbxActive);

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