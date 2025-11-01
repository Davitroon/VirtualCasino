package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.MainController;
import controller.Validator;
import controller.ViewController;
import model.Client;

/**
 * User interface window for adding a new client.
 * <p>
 * This window allows an administrator to input information for a new client,
 * including name, age, gender, and balance. It performs real-time validation
 * on each field and integrates with the MVC architecture through
 * {@link MainController} and {@link ViewController}.
 * </p>
 * 
 * @author Davitroon
 * @since 3.0
 */
public class ClientUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
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

		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 496, 397);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblAddClient = new JLabel("Add Client", SwingConstants.CENTER);
		lblAddClient.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblAddClient.setBounds(10, 21, 460, 39);
		contentPane.add(lblAddClient);

		textName = new JTextField();
		textName.setBounds(122, 84, 321, 32);
		contentPane.add(textName);
		textName.setColumns(10);

		textAge = new JTextField();
		textAge.setBounds(105, 149, 129, 32);
		contentPane.add(textAge);
		textAge.setColumns(10);

		rdbtnMale = new JRadioButton("Male");
		buttonGroup.add(rdbtnMale);
		rdbtnMale.setBounds(100, 246, 99, 23);
		contentPane.add(rdbtnMale);

		rdbtnFemale = new JRadioButton("Female");
		buttonGroup.add(rdbtnFemale);
		rdbtnFemale.setBounds(211, 246, 86, 23);
		contentPane.add(rdbtnFemale);

		rdbtnOther = new JRadioButton("Other");
		buttonGroup.add(rdbtnOther);
		rdbtnOther.setBounds(309, 246, 64, 23);
		contentPane.add(rdbtnOther);

		textBalance = new JTextField();
		textBalance.setBounds(290, 149, 153, 32);
		contentPane.add(textBalance);
		textBalance.setColumns(10);

		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblName.setLabelFor(textName);
		lblName.setBounds(52, 92, 64, 17);
		contentPane.add(lblName);

		JLabel lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAge.setLabelFor(textAge);
		lblAge.setBounds(52, 157, 64, 17);
		contentPane.add(lblAge);

		JLabel lblBalance = new JLabel("Balance");
		lblBalance.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBalance.setLabelFor(textBalance);
		lblBalance.setBounds(244, 157, 53, 17);
		contentPane.add(lblBalance);

		JLabel lblGender = new JLabel("Gender", SwingConstants.CENTER);
		lblGender.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblGender.setBounds(34, 220, 409, 14);
		contentPane.add(lblGender);

		btnAdd = new JButton("Add");
		btnAdd.setBackground(new Color(128, 128, 255));
		btnAdd.setEnabled(false);
		btnAdd.setBounds(359, 295, 111, 32);
		contentPane.add(btnAdd);

		JButton btnBack = new JButton("Back");
		btnBack.setBackground(new Color(128, 128, 128));
		btnBack.setBounds(24, 295, 111, 32);
		contentPane.add(btnBack);

		lblErrorName = new JLabel("");
		lblErrorName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErrorName.setForeground(new Color(255, 0, 0));
		lblErrorName.setBounds(54, 121, 389, 14);
		contentPane.add(lblErrorName);

		lblErrorAge = new JLabel("");
		lblErrorAge.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErrorAge.setForeground(new Color(255, 0, 0));
		lblErrorAge.setBounds(52, 186, 182, 14);
		contentPane.add(lblErrorAge);

		lblErrorBalance = new JLabel("");
		lblErrorBalance.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErrorBalance.setForeground(new Color(255, 0, 0));
		lblErrorBalance.setBounds(244, 186, 199, 17);
		contentPane.add(lblErrorBalance);

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
				viewController.switchWindow(ClientUI.this, viewController.getManagementUI());
			}
		});

		// Click "Back" button
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearFields();
				viewController.switchWindow(ClientUI.this, viewController.getManagementUI());
			}
		});

		// When closing the window using the X
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				clearFields();
				viewController.switchWindow(ClientUI.this, viewController.getManagementUI());
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