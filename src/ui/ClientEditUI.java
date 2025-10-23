package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.MainController;
import controller.Validator;
import dao.DatabaseManager;
import model.Client;
import ui.ClientEditUI;
import ui.ManagementUI;

/**
 * Window for the client edit form.
 * 
 * @author David
 * @since 3.0
 */
public class ClientEditUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textName;
	private JTextField textAge;
	private JTextField textBalance;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	private DatabaseManager model;

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

	/**
	 * Create the frame.
	 * 
	 * @param management The parent management window (GestionUI) from which this
	 *                   editing window was opened, used for returning or refreshing
	 *                   data.
	 * @param controller The Controller instance used to manage logic, handle button
	 *                   actions, and perform data manipulations.
	 * @param model      The Model instance providing connectivity to the database
	 *                   and data management services.
	 * @param validator  The Validator instance used to check the integrity and
	 *                   format of user input (e.g., name, age, balance).
	 * @since 3.0
	 */
	public ClientEditUI(ManagementUI management, MainController controller, DatabaseManager model, Validator validator) {

		this.model = model;

		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 499, 403);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblEditClient = new JLabel("Edit Client", SwingConstants.CENTER);
		lblEditClient.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblEditClient.setBounds(10, 25, 463, 39);
		contentPane.add(lblEditClient);

		textName = new JTextField();
		textName.setBounds(189, 100, 167, 32);
		contentPane.add(textName);
		textName.setColumns(10);

		textAge = new JTextField();
		textAge.setBounds(76, 165, 133, 32);
		contentPane.add(textAge);
		textAge.setColumns(10);

		rdbtnMale = new JRadioButton("Male");
		buttonGroup.add(rdbtnMale);
		rdbtnMale.setBounds(110, 246, 99, 23);
		contentPane.add(rdbtnMale);

		rdbtnFemale = new JRadioButton("Female");
		buttonGroup.add(rdbtnFemale);
		rdbtnFemale.setBounds(214, 246, 86, 23);
		contentPane.add(rdbtnFemale);

		rdbtnOther = new JRadioButton("Other");
		buttonGroup.add(rdbtnOther);
		rdbtnOther.setBounds(302, 246, 64, 23);
		contentPane.add(rdbtnOther);

		textBalance = new JTextField();
		textBalance.setBounds(280, 165, 151, 32);
		contentPane.add(textBalance);
		textBalance.setColumns(10);

		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblName.setLabelFor(textName);
		lblName.setBounds(126, 107, 64, 17);
		contentPane.add(lblName);

		JLabel lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAge.setLabelFor(textAge);
		lblAge.setBounds(37, 172, 64, 17);
		contentPane.add(lblAge);

		JLabel lblBalance = new JLabel("Balance");
		lblBalance.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBalance.setLabelFor(textBalance);
		lblBalance.setBounds(229, 172, 36, 17);
		contentPane.add(lblBalance);

		JLabel lblGender = new JLabel("Gender", SwingConstants.CENTER);
		lblGender.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblGender.setBounds(52, 225, 379, 14);
		contentPane.add(lblGender);

		btnModify = new JButton("Modify");
		btnModify.setBackground(new Color(128, 128, 255));
		btnModify.setBounds(333, 307, 111, 32);
		contentPane.add(btnModify);

		JButton btnBack = new JButton("Go back");
		btnBack.setBackground(new Color(128, 128, 128));
		btnBack.setBounds(25, 307, 111, 32);
		contentPane.add(btnBack);

		lblErrorName = new JLabel("");
		lblErrorName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErrorName.setForeground(new Color(255, 0, 0));
		lblErrorName.setBounds(126, 134, 267, 14);
		contentPane.add(lblErrorName);

		lblErrorAge = new JLabel("");
		lblErrorAge.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErrorAge.setForeground(new Color(255, 0, 0));
		lblErrorAge.setBounds(37, 200, 172, 14);
		contentPane.add(lblErrorAge);

		lblErrorBalance = new JLabel("");
		lblErrorBalance.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErrorBalance.setForeground(new Color(255, 0, 0));
		lblErrorBalance.setBounds(236, 200, 195, 14);
		contentPane.add(lblErrorBalance);

		JLabel lblId = new JLabel("ID");
		lblId.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblId.setBounds(37, 108, 26, 14);
		contentPane.add(lblId);

		textId = new JTextField();
		textId.setEnabled(false);
		textId.setEditable(false);
		textId.setBounds(57, 100, 44, 32);
		contentPane.add(textId);
		textId.setColumns(10);

		chckbxActive = new JCheckBox("Active");
		chckbxActive.setBounds(368, 105, 76, 23);
		contentPane.add(chckbxActive);

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

				model.modifyClient(new Client(name, age, gender, balance, id, active));

				clearFields();
				controller.switchWindow(ClientEditUI.this, management);
			}
		});

		// Click back button
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearFields();
				controller.switchWindow(ClientEditUI.this, management);
			}
		});

		// When closing the window using the X
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				clearFields();
				controller.switchWindow(ClientEditUI.this, management);
			}
		});
	}

	/**
	 * Method to fill the form fields with the data of the client to be edited.
	 * 
	 * @param id ID of the client to retrieve their data
	 * @since 3.0
	 */
	public void loadOriginalClient(int id) {

		ResultSet rset = model.querySingleData("customers", id);
		String gender = "";

		try {
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
	 * Method to clear all form fields.
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
	 * Method to get the gender based on the selected radio button.
	 * 
	 * @return Gender as a char.
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
	 * Method to check if the user has filled all fields in the form.
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