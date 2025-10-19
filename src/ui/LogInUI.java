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

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import controller.Session;
import controller.Validator;
import dao.DBManagement;
import model.User;
import ui.ConnectUI;
import ui.LogInUI;

/**
 * Window where the user can log in.
 * 
 * @author David
 * @since 3.0
 */
public class LogInUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textUsername;

	private User user;

	private boolean usernameValid;
	private boolean passwordValid;
	private JPasswordField passwordField;
	private JButton btnAccept;
	private JLabel lblUsernameError;
	private JCheckBox chckbxRememberSession;
	private JCheckBox chckbxShowPassword;

	/**
	 * Constructs the login window frame.
	 *
	 * @param model      the data model of the application
	 * @param controller the controller handling UI actions
	 * @param session    the current session object
	 * @param login      the ConnectUI window to return to
	 * @param validator  the validator for input fields
	 * @since 3.0
	 */
	public LogInUI(DBManagement model, Controller controller, Session session, ConnectUI login, Validator validator) {

		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 533, 367);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblLogIn = new JLabel("Log In", SwingConstants.CENTER);
		lblLogIn.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblLogIn.setBounds(10, 29, 486, 31);
		contentPane.add(lblLogIn);

		JButton btnBack = new JButton("Back");
		btnBack.setBackground(Color.GRAY);
		btnBack.setBounds(21, 273, 105, 32);
		contentPane.add(btnBack);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUsername.setBounds(89, 107, 133, 14);
		contentPane.add(lblUsername);

		textUsername = new JTextField();
		textUsername.setBounds(228, 101, 172, 26);
		textUsername.setColumns(10);
		contentPane.add(textUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPassword.setBounds(89, 165, 129, 15);
		contentPane.add(lblPassword);

		btnAccept = new JButton("Accept");
		btnAccept.setEnabled(false);
		btnAccept.setBounds(391, 273, 105, 32);
		contentPane.add(btnAccept);

		lblUsernameError = new JLabel("");
		lblUsernameError.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUsernameError.setForeground(new Color(255, 0, 0));
		lblUsernameError.setBounds(89, 132, 311, 14);
		contentPane.add(lblUsernameError);

		JLabel lblPasswordNotice = new JLabel("Password must be at least 8 characters");
		lblPasswordNotice.setForeground(Color.RED);
		lblPasswordNotice.setBounds(89, 191, 288, 20);
		contentPane.add(lblPasswordNotice);

		passwordField = new JPasswordField();
		passwordField.setEchoChar('*');
		passwordField.setBounds(189, 160, 149, 26);
		contentPane.add(passwordField);

		chckbxShowPassword = new JCheckBox("Show");
		chckbxShowPassword.setBounds(344, 162, 69, 23);
		contentPane.add(chckbxShowPassword);

		chckbxRememberSession = new JCheckBox("Remember login");
		chckbxRememberSession.setBounds(89, 218, 201, 23);
		contentPane.add(chckbxRememberSession);

		// Click back button
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetContent();
				controller.switchWindow(LogInUI.this, login);
			}
		});

		// Typing in the username field
		textUsername.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				usernameValid = false;
				String text = textUsername.getText();

				if (validator.validateName(text, lblUsernameError)) {
					usernameValid = true;
				}

				checkForm();
			}
		});

		// Typing in the password field
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				passwordValid = false;
				if (passwordField.getPassword().length >= 8) {
					passwordValid = true;
					lblPasswordNotice.setForeground(Color.GRAY);
				} else {
					lblPasswordNotice.setForeground(Color.RED);
				}

				checkForm();
			}
		});

		// Show or hide password
		chckbxShowPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxShowPassword.isSelected()) {
					passwordField.setEchoChar((char) 0); // Show
				} else {
					passwordField.setEchoChar('*'); // Hide
				}
			}
		});

		// Click accept button
		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				char[] passwordChars = passwordField.getPassword();
				String password = new String(passwordChars);

				// Check if the user exists
				try {
					ResultSet rset = (model.queryUser(textUsername.getText()));
					if (rset != null) {
						user = new User(rset.getInt("id"), rset.getString("username"), rset.getString("user_password"),
								rset.getString("email"), rset.getString("last_access"),
								rset.getBoolean("remember_login"));

						// Validate that the user's password matches the input
						if (!user.getPassword().equals(password)) {
							JOptionPane.showMessageDialog(null, "Incorrect password", "Warning",
									JOptionPane.WARNING_MESSAGE);
						} else {
							// If "remember login" is checked, update before logging in
							if (chckbxRememberSession.isSelected()) {
								model.toggleRememberLogin(user.getName(), true);
							}
							session.startSession(user, LogInUI.this);
							resetContent();
						}
						rset.close();

					} else {
						JOptionPane.showMessageDialog(null, "The user " + textUsername.getText() + " is not registered",
								"Warning", JOptionPane.WARNING_MESSAGE);
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		// Closing the window with the X
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				resetContent();
				controller.switchWindow(LogInUI.this, login);
			}
		});
	}

	/**
	 * Resets the content of the page before closing.
	 */
	public void resetContent() {
		lblUsernameError.setText("");
		btnAccept.setEnabled(false);
		textUsername.setText("");
		passwordField.setText("");
		chckbxRememberSession.setSelected(false);
		chckbxShowPassword.setSelected(false);
	}

	/**
	 * Checks the form to ensure the data is valid.
	 */
	public void checkForm() {
		if (usernameValid && passwordValid) {
			btnAccept.setEnabled(true);
		} else {
			btnAccept.setEnabled(false);
		}
	}

	public void setUser(User user) {
		this.user = user;
	}

}