package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.DatabaseController;
import controller.MainController;
import controller.ViewController;
import exceptions.MailException;
import model.User;

/**
 * Window that allows a user to create a new account and log in.
 * <p>
 * Provides fields for username, password, repeated password, and email.
 * Includes options to remember the login and show/hide the password.
 * </p>
 * 
 * @author Davitroon
 * @since 3.0
 */
public class SignInUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtUsername;

	private boolean validUsername;
	private boolean validPassword;
	private boolean validPassword2;
	private boolean validEmail;

	private JPasswordField passwordField;
	private JButton btnAccept;
	private JLabel lblUsernameError;
	private JCheckBox chkRememberLogin;
	private JPasswordField passwordField2;
	private JTextField txtEmail;
	private JCheckBox chkShowPassword;

	private User user;
	private JLabel lblPasswordNotice;

	/**
	 * Constructs the SignInUI frame.
	 * <p>
	 * Initializes the window layout, text fields, buttons, labels, and event
	 * listeners for user creation, validation, and login functionality.
	 * </p>
	 * 
	 * @param controller The main controller handling user actions and window
	 *                   changes
	 * @since 3.0
	 */
	public SignInUI(MainController controller) {

		DatabaseController dbController = controller.getDataBaseController();
		ViewController viewController = controller.getViewController();

		setBounds(100, 100, 802, 433);
		setLayout(null);
		setBackground(new Color(220, 220, 220));

		JLabel lblCreateuser = new JLabel("Create User", SwingConstants.CENTER);
		lblCreateuser.setFont(new Font("Segoe UI Black", Font.BOLD, 36));
		lblCreateuser.setBounds(138, 21, 525, 50);
		add(lblCreateuser);

		JButton btnBack = new JButton("Back");
		btnBack.setForeground(Color.WHITE);
		btnBack.setFocusPainted(false);
		btnBack.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnBack.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		btnBack.setBackground(new Color(128, 128, 128));
		btnBack.setBounds(10, 386, 132, 36);
		add(btnBack);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Segoe UI Black", Font.BOLD, 16));
		lblUsername.setBounds(103, 118, 133, 20);
		add(lblUsername);

		txtUsername = new JTextField();
		txtUsername.setBounds(103, 141, 232, 26);
		txtUsername.setColumns(10);
		add(txtUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Segoe UI Black", Font.BOLD, 16));
		lblPassword.setBounds(103, 199, 129, 20);
		add(lblPassword);

		btnAccept = new JButton("Accept");
		btnAccept.setBackground(new Color(128, 128, 255));
		btnAccept.setEnabled(false);
		btnAccept.setBounds(660, 386, 132, 36);
		btnAccept.setForeground(Color.WHITE);
		btnAccept.setFocusPainted(false);
		btnAccept.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnAccept.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAccept.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		add(btnAccept);

		lblUsernameError = new JLabel("");
		lblUsernameError.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUsernameError.setForeground(new Color(255, 0, 0));
		lblUsernameError.setBounds(103, 174, 258, 14);
		add(lblUsernameError);

		lblPasswordNotice = new JLabel("Password must be at least 8 characters");
		lblPasswordNotice.setForeground(Color.RED);
		lblPasswordNotice.setBounds(103, 248, 272, 20);
		add(lblPasswordNotice);

		passwordField = new JPasswordField();
		passwordField.setEchoChar('*');
		passwordField.setBounds(103, 221, 232, 26);
		add(passwordField);

		chkShowPassword = new JCheckBox("Show");
		chkShowPassword.setBounds(627, 223, 69, 23);
		add(chkShowPassword);

		chkRememberLogin = new JCheckBox("Remember login");
		chkRememberLogin.setBounds(103, 275, 201, 23);
		add(chkRememberLogin);

		JLabel lblPassword2 = new JLabel("Repeat Password");
		lblPassword2.setFont(new Font("Segoe UI Black", Font.BOLD, 16));
		lblPassword2.setBounds(389, 199, 201, 15);
		add(lblPassword2);

		passwordField2 = new JPasswordField();
		passwordField2.setEchoChar('*');
		passwordField2.setBounds(389, 219, 232, 26);
		add(passwordField2);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Segoe UI Black", Font.BOLD, 16));
		lblEmail.setBounds(389, 118, 58, 20);
		add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(389, 144, 232, 26);
		add(txtEmail);

		JLabel lblErrorEmail = new JLabel("");
		lblErrorEmail.setForeground(Color.RED);
		lblErrorEmail.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErrorEmail.setBounds(389, 174, 258, 14);
		add(lblErrorEmail);

		// Click back button
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetContent();
				viewController.switchPanel(viewController.getConnectUI());
			}
		});

		// When typing in the username field
		txtUsername.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				validUsername = false;
				String text = txtUsername.getText();

				if (controller.getValidator().validateName(text, lblUsernameError)) {
					validUsername = true;
				}
				checkForm();
			}
		});

		// When typing in the password field
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				validPassword = false;

				if (passwordField.getPassword().length > 7) {
					validPassword = true;
					lblPasswordNotice.setForeground(Color.GRAY);
				} else {
					lblPasswordNotice.setForeground(Color.RED);
				}

				checkForm();
			}
		});

		// When typing in the repeat password field
		passwordField2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				validPassword2 = false;
				char[] passwordChars = passwordField2.getPassword();
				String password = new String(passwordChars);

				if (password.length() > 7) {
					validPassword2 = true;
				}
				checkForm();
			}
		});

		// When typing in the email field
		txtEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				validEmail = false;
				String email = txtEmail.getText().trim();

				if (email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
					validEmail = true;
					lblErrorEmail.setText("");
				} else {
					lblErrorEmail.setText("Invalid email format");
				}

				checkForm();
			}
		});

		// Show or hide password
		chkShowPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkShowPassword.isSelected()) {
					passwordField.setEchoChar((char) 0); // Show
					passwordField2.setEchoChar((char) 0);
				} else {
					passwordField.setEchoChar('*'); // Hide
					passwordField2.setEchoChar('*');
				}
			}
		});

		// Click accept button
		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				char[] passwordChars = passwordField.getPassword();
				String password = new String(passwordChars);

				char[] passwordChars2 = passwordField2.getPassword();
				String password2 = new String(passwordChars2);

				if (!password.equals(password2)) {
					JOptionPane.showMessageDialog(null, "Passwords do not match.", "Warning",
							JOptionPane.WARNING_MESSAGE);
				} else {
					// Create the user and log in as them
					try {
						user = new User(txtUsername.getText(), password, txtEmail.getText(),
								chkRememberLogin.isSelected());

						user = dbController.addUser(user, chkRememberLogin.isSelected());
						controller.updateUser(user);

						dbController.addDefaultUser();
						viewController.switchPanel(viewController.getHomeUI());
						resetContent();

					} catch (MailException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Invalid Email",
								JOptionPane.WARNING_MESSAGE);

					} catch (SQLIntegrityConstraintViolationException e2) {
						JOptionPane.showMessageDialog(null, e2.getMessage(), "Invalid Email",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
	}

	/**
	 * Resets all form fields and error messages to their default state.
	 * <p>
	 * Used when closing the window or switching to another UI.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public void resetContent() {
		lblUsernameError.setText("");
		btnAccept.setEnabled(false);
		txtUsername.setText("");
		passwordField.setText("");
		passwordField2.setText("");
		txtEmail.setText("");
		chkRememberLogin.setSelected(false);
		chkShowPassword.setSelected(false);
		passwordField.setEchoChar('*');
		passwordField2.setEchoChar('*');
	}

	/**
	 * Validates the form fields and enables or disables the Accept button
	 * accordingly.
	 * <p>
	 * Ensures username, password, repeated password, and email are valid.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public void checkForm() {
		if (validPassword && validPassword2 && validUsername && validEmail) {
			btnAccept.setEnabled(true);
		} else {
			btnAccept.setEnabled(false);
		}
	}
}
