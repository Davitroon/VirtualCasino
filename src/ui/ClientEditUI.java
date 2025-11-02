package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.DataBaseController;
import controller.MainController;
import controller.Validator;
import controller.ViewController;
import model.Client;

/**
 * Window for editing existing client data.
 * <p>
 * This window allows an administrator to modify the details of a client,
 * including name, age, gender, balance, and active status. It integrates with
 * the MVC architecture through {@link MainController},
 * {@link DataBaseController}, and {@link ViewController}.
 * </p>
 * 
 * @author Davitroon
 * @since 3.0
 */
public class ClientEditUI extends JPanel {

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
	private JButton btnModify;

	private JLabel lblErrorName;
	private JLabel lblErrorAge;
	private JLabel lblErrorBalance;
	private JTextField textId;
	private JCheckBox chckbxActive;
	private DataBaseController dbController;

	/**
	 * Constructor for the client edit window.
	 * <p>
	 * Initializes all UI components, sets up event listeners for form validation,
	 * and connects the window to MVC controllers.
	 * </p>
	 * 
	 * @param controller MainController instance used to handle actions, validation,
	 *                   and database communication.
	 * @since 3.0
	 */
	public ClientEditUI(MainController controller) {

		ViewController viewController = controller.getViewController();
		Validator validator = controller.getValidator();
		dbController = controller.getDataBaseController();
		ManagementUI managementUI = viewController.getManagementUI();

		setBounds(100, 100, 802, 433);
		setLayout(null);

		JLabel lblEditClient = new JLabel("Edit Client", SwingConstants.CENTER);
		lblEditClient.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblEditClient.setBounds(10, 25, 782, 39);
		add(lblEditClient);

		textName = new JTextField();
		textName.setBounds(305, 121, 233, 32);
		add(textName);
		textName.setColumns(10);

		textAge = new JTextField();
		textAge.setBounds(305, 296, 233, 32);
		add(textAge);
		textAge.setColumns(10);

		rdbtnMale = new JRadioButton("Male");
		buttonGroup.add(rdbtnMale);
		rdbtnMale.setBounds(162, 214, 99, 23);
		add(rdbtnMale);

		rdbtnFemale = new JRadioButton("Female");
		buttonGroup.add(rdbtnFemale);
		rdbtnFemale.setBounds(162, 240, 86, 23);
		add(rdbtnFemale);

		rdbtnOther = new JRadioButton("Other");
		buttonGroup.add(rdbtnOther);
		rdbtnOther.setBounds(162, 266, 64, 23);
		add(rdbtnOther);

		textBalance = new JTextField();
		textBalance.setBounds(305, 212, 233, 32);
		add(textBalance);
		textBalance.setColumns(10);

		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblName.setLabelFor(textName);
		lblName.setBounds(305, 93, 64, 17);
		add(lblName);

		JLabel lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAge.setLabelFor(textAge);
		lblAge.setBounds(305, 272, 64, 17);
		add(lblAge);

		JLabel lblBalance = new JLabel("Balance");
		lblBalance.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBalance.setLabelFor(textBalance);
		lblBalance.setBounds(305, 186, 174, 17);
		add(lblBalance);

		JLabel lblGender = new JLabel("Gender", SwingConstants.CENTER);
		lblGender.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblGender.setBounds(162, 193, 99, 14);
		add(lblGender);

		btnModify = new JButton("Modify");
		btnModify.setBackground(new Color(128, 128, 255));
		btnModify.setBounds(660, 388, 132, 36);
		add(btnModify);

		JButton btnBack = new JButton("Go back");
		btnBack.setBackground(new Color(128, 128, 128));
		btnBack.setBounds(10, 388, 132, 36);
		add(btnBack);

		lblErrorName = new JLabel("");
		lblErrorName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErrorName.setForeground(new Color(255, 0, 0));
		lblErrorName.setBounds(305, 153, 233, 14);
		add(lblErrorName);

		lblErrorAge = new JLabel("");
		lblErrorAge.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErrorAge.setForeground(new Color(255, 0, 0));
		lblErrorAge.setBounds(305, 331, 233, 14);
		add(lblErrorAge);

		lblErrorBalance = new JLabel("");
		lblErrorBalance.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErrorBalance.setForeground(new Color(255, 0, 0));
		lblErrorBalance.setBounds(305, 247, 233, 14);
		add(lblErrorBalance);

		JLabel lblId = new JLabel("ID");
		lblId.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblId.setBounds(168, 93, 26, 14);
		add(lblId);

		textId = new JTextField();
		textId.setEnabled(false);
		textId.setEditable(false);
		textId.setBounds(168, 116, 44, 32);
		add(textId);
		textId.setColumns(10);

		chckbxActive = new JCheckBox("Active");
		chckbxActive.setBounds(543, 122, 76, 23);
		add(chckbxActive);

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

		// Click modify button
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = textName.getText();
				int age = Integer.parseInt(textAge.getText());
				char gender = getGender();
				double balance = Double.parseDouble(textBalance.getText());
				int id = Integer.parseInt(textId.getText());
				boolean active = chckbxActive.isSelected() ? true : false;

				dbController.modifyClient(new Client(name, age, gender, balance, id, active));

				clearFields();
				managementUI.updateGamesTable();
				managementUI.updateClientsTable();
				viewController.switchPanel(managementUI);
			}
		});

		// Click back button
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearFields();
				viewController.switchPanel(managementUI);
			}
		});
	}

	/**
	 * Loads the original client data into the form fields for editing.
	 * 
	 * @param id ID of the client to retrieve from the database.
	 * @since 3.0
	 */
	public void loadOriginalClient(int id) {

		ResultSet rset = dbController.queryClient(id);
		String gender = "";

		try {
			rset.next();
			textId.setText(String.valueOf(id));
			textName.setText(rset.getString(2));
			textAge.setText(String.valueOf(rset.getString(3)));
			gender = rset.getString(4);
			if (rset.getBoolean(5))
				chckbxActive.setSelected(true);
			textBalance.setText(String.valueOf(rset.getString(6)));

		} catch (SQLException e) {
			e.printStackTrace();
		}

		switch (gender) {
		case "M":
			rdbtnMale.doClick();
			break;
		case "F":
			rdbtnFemale.doClick();
			break;
		case "O":
			rdbtnOther.doClick();
			break;
		}

		ageValid = true;
		balanceValid = true;
		nameValid = true;
		checkForm();
	}

	/**
	 * Clears all form fields, resets validation flags, and disables the
	 * modification button.
	 * 
	 * @since 3.0
	 */
	public void clearFields() {
		btnModify.setEnabled(false);
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
	 * Enables or disables the modification button accordingly.
	 * 
	 * @since 3.0
	 */
	public void checkForm() {

		if (ageValid && balanceValid && nameValid && getGender() != 0) {
			btnModify.setEnabled(true);

		} else {
			btnModify.setEnabled(false);
		}
	}
}