package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
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
 * Window for adding new games to the system.
 * <p>
 * This window allows the user to select the game type and set the initial
 * balance. It validates the money input before enabling the "Add" button.
 * </p>
 * 
 * @author Davitroon
 * @since 3.0
 */
public class GameUI extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField textMoney;

	private boolean moneyValid;
	private JButton btnAdd;

	private JLabel lblErrorMoney;
	private JComboBox<Object> comboType;

    /**
     * Constructs the GameUI window.
     * <p>
     * Initializes UI components, sets up listeners for buttons and input fields,
     * and handles window closing events.
     * </p>
     * 
     * @param controller Reference to the MainController handling program logic
     *                   and data operations.
     * @since 3.0
     */
	public GameUI(MainController controller) {

		ViewController viewController = controller.getViewController();
		ManagementUI managementUI = viewController.getManagementUI();

		setBounds(100, 100, 802, 433);
		setLayout(null);

		JLabel lblAddGame = new JLabel("Add Game", SwingConstants.CENTER);
		lblAddGame.setFont(new Font("Stencil", Font.PLAIN, 30));
		lblAddGame.setBounds(10, 40, 782, 39);
		add(lblAddGame);

		btnAdd = new JButton("Add");
		btnAdd.setBackground(new Color(128, 128, 255));
		btnAdd.setEnabled(false);
		btnAdd.setBounds(660, 386, 132, 36);
		add(btnAdd);

		JButton btnBack = new JButton("Back");
		btnBack.setBackground(new Color(128, 128, 128));
		btnBack.setBounds(10, 386, 132, 36);
		add(btnBack);

		JLabel lblType = new JLabel("Type");
		lblType.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblType.setBounds(263, 132, 49, 14);
		add(lblType);

		JLabel lblMoney = new JLabel("Money");
		lblMoney.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMoney.setBounds(263, 182, 49, 14);
		add(lblMoney);

		textMoney = new JTextField();
		textMoney.setBounds(263, 207, 182, 32);
		add(textMoney);
		textMoney.setColumns(10);

		lblErrorMoney = new JLabel("");
		lblErrorMoney.setForeground(new Color(255, 0, 0));
		lblErrorMoney.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErrorMoney.setBounds(263, 244, 233, 14);
		add(lblErrorMoney);

		comboType = new JComboBox<Object>();
		comboType.setModel(new DefaultComboBoxModel<Object>(new String[] { "Blackjack", "SlotMachine" }));
		comboType.setBounds(260, 149, 111, 22);
		add(comboType);

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

		// Click "Add" button
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Double money = Double.parseDouble(textMoney.getText());
				String type = String.valueOf(comboType.getSelectedItem());

				if (type.equalsIgnoreCase("Blackjack")) {
					controller.getDataBaseController().addGame(new Blackjack(money));
				}

				if (type.equalsIgnoreCase("SlotMachine")) {
					controller.getDataBaseController().addGame(new Slotmachine(money));
				}

				clearFields();
				viewController.switchPanel(managementUI);
			}
		});
	}

    /**
     * Clears all form fields and resets validation states.
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
     * Checks if all required fields are valid to enable the add button.
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