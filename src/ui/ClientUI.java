package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.MainController;
import controller.Validator;
import controller.ViewController;
import model.Client;

/**
 * Window for adding new clients to the system.
 * <p>
 * This window allows an administrator to input information for a new client,
 * including name, age, gender, and balance. It performs real-time validation on
 * each field and integrates with the MVC architecture through
 * {@link MainController} and {@link ViewController}.
 * </p>
 * 
 * @author Davitroon
 * @since 3.0
 */
public class ClientUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textName;
	private JTextField textAge;
	private JTextField textBalance;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	private boolean nameValid;
	private boolean ageValid;
	private boolean balanceValid;
	private JRadioButton rdbtnMale;
	private JRadioButton rdbtnFemale;
	private JRadioButton rdbtnOther;
	private JButton btnAdd;

	private JLabel lblErrorName;
	private JLabel lblErrorAge;
	private JLabel lblErrorBalance;

	/**
	 * Constructor for the add-client window.
	 * <p>
	 * Initializes all UI components, sets up event listeners for form validation,
	 * and connects the window to MVC controllers.
	 * </p>
	 * 
	 * @param controller MainController instance used to handle actions, validation,
	 *                   and database communication.
	 * @since 3.0
	 */
	public ClientUI(MainController controller) {

		Validator validator = controller.getValidator();
		ViewController viewController = controller.getViewController();

		setBounds(100, 100, 802, 433);
		setLayout(null);

		JLabel lblAddClient = new JLabel("Add Client", SwingConstants.CENTER);
		lblAddClient.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblAddClient.setBounds(10, 34, 782, 39);
		add(lblAddClient);

		textName = new JTextField();
		textName.setBounds(309, 129, 260, 32);
		add(textName);
		textName.setColumns(10);

		textAge = new JTextField();
		textAge.setBounds(309, 292, 153, 32);
		add(textAge);
		textAge.setColumns(10);

		rdbtnMale = new JRadioButton("Male");
		buttonGroup.add(rdbtnMale);
		rdbtnMale.setBounds(149, 197, 99, 23);
		add(rdbtnMale);

		rdbtnFemale = new JRadioButton("Female");
		buttonGroup.add(rdbtnFemale);
		rdbtnFemale.setBounds(149, 223, 86, 23);
		add(rdbtnFemale);

		rdbtnOther = new JRadioButton("Other");
		buttonGroup.add(rdbtnOther);
		rdbtnOther.setBounds(149, 249, 64, 23);
		add(rdbtnOther);

		textBalance = new JTextField();
		textBalance.setBounds(309, 205, 153, 32);
		add(textBalance);
		textBalance.setColumns(10);

		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblName.setLabelFor(textName);
		lblName.setBounds(309, 110, 64, 17);
		add(lblName);

		JLabel lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAge.setLabelFor(textAge);
		lblAge.setBounds(309, 272, 64, 17);
		add(lblAge);

		JLabel lblBalance = new JLabel("Balance");
		lblBalance.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBalance.setLabelFor(textBalance);
		lblBalance.setBounds(309, 186, 53, 17);
		add(lblBalance);

		JLabel lblGender = new JLabel("Gender", SwingConstants.CENTER);
		lblGender.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblGender.setBounds(149, 179, 64, 14);
		add(lblGender);

		btnAdd = new JButton("Add");
		btnAdd.setBackground(new Color(128, 128, 255));
		btnAdd.setEnabled(false);
		btnAdd.setBounds(660, 386, 132, 36);
		add(btnAdd);

		JButton btnBack = new JButton("Back");
		btnBack.setBackground(new Color(128, 128, 128));
		btnBack.setBounds(10, 386, 132, 36);
		add(btnBack);

		lblErrorName = new JLabel("");
		lblErrorName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErrorName.setForeground(new Color(255, 0, 0));
		lblErrorName.setBounds(309, 166, 260, 14);
		add(lblErrorName);

		lblErrorAge = new JLabel("");
		lblErrorAge.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErrorAge.setForeground(new Color(255, 0, 0));
		lblErrorAge.setBounds(309, 327, 153, 14);
		add(lblErrorAge);

		lblErrorBalance = new JLabel("");
		lblErrorBalance.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErrorBalance.setForeground(new Color(255, 0, 0));
		lblErrorBalance.setBounds(309, 241, 153, 17);
		add(lblErrorBalance);

		// When typing in the name field
		textName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				nameValid = false;
				String text = textName.getText();

				if (validator.validateName(text, lblErrorName)) {
					nameValid = true;
				}

				checkForm();
			}
		});

		// When typing in the age field
		textAge.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				ageValid = false;
				String text = textAge.getText();

				if (validator.validateClientAge(text, lblErrorAge)) {
					ageValid = true;
				}

				checkForm();
			}
		});

		// When typing in the balance field
		textBalance.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				balanceValid = false;
				String text = textBalance.getText();

				if (validator.validateClientBalance(text, lblErrorBalance)) {
					balanceValid = true;
				}

				checkForm();
			}
		});

		// Select male gender
		rdbtnMale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkForm();
			}
		});

		// Select female gender
		rdbtnFemale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkForm();
			}
		});

		// Select other gender
		rdbtnOther.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkForm();
			}
		});

		// Click "Add" button
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = textName.getText();
				int age = Integer.parseInt(textAge.getText());
				char gender = getGender();
				double balance = Double.parseDouble(textBalance.getText());

				controller.getDataBaseController().addClient(new Client(name, age, gender, balance));

				clearFields();
				viewController.switchPanel(viewController.getManagementUI());
			}
		});

		// Click "Back" button
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearFields();
				viewController.switchPanel(viewController.getManagementUI());
			}
		});

	}

	/**
	 * Clears all fields in the form and disables the Add button.
	 * <p>
	 * Resets text fields, error labels, and radio button selections.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public void clearFields() {
		btnAdd.setEnabled(false);
		buttonGroup.clearSelection();
		textName.setText("");
		textAge.setText("");
		textBalance.setText("");
		lblErrorName.setText("");
		lblErrorAge.setText("");
		lblErrorBalance.setText("");
	}

	/**
	 * Returns the gender selected in the form.
	 * 
	 * @return 'M' for male, 'F' for female, 'O' for other, or 0 if none selected.
	 * @since 3.0
	 */
	public char getGender() {
		if (rdbtnMale.isSelected())
			return 'M';
		if (rdbtnFemale.isSelected())
			return 'F';
		if (rdbtnOther.isSelected())
			return 'O';
		return 0;
	}

	/**
	 * Checks whether all form fields have valid values and a gender is selected.
	 * Enables or disables the Add button accordingly.
	 * 
	 * @since 3.0
	 */
	public void checkForm() {

		if (ageValid && balanceValid && nameValid && getGender() != 0) {
			btnAdd.setEnabled(true);

		} else {
			btnAdd.setEnabled(false);
		}
	}
}