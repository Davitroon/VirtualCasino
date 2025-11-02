package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.MainController;
import controller.ViewController;
import model.User;

/**
 * Window where the user can log in to the application.
 * <p>
 * Provides fields for username and password, with optional "remember login"
 * and "show password" functionality. Validates input before enabling the
 * accept button.
 * </p>
 * 
 * @author Davitroon
 * @since 3.0
 */
public class LogInUI extends JPanel {

	private static final long serialVersionUID = 1L;
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
	 * <p>
	 * Sets up all UI components, event listeners, and validation logic for
	 * the login process.
	 * </p>
	 * 
	 * @param controller the main controller handling UI actions and data
	 *                   operations
	 * @since 3.0
	 */
	public LogInUI(MainController controller) {

		ViewController viewController = controller.getViewController();

		setBounds(100, 100, 802, 433);
		setLayout(null);

		JLabel lblLogIn = new JLabel("Log In", SwingConstants.CENTER);
		lblLogIn.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblLogIn.setBounds(10, 33, 782, 31);
		add(lblLogIn);

		JButton btnBack = new JButton("Back");
		btnBack.setBackground(Color.GRAY);
		btnBack.setBounds(10, 386, 132, 36);
		add(btnBack);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUsername.setBounds(186, 132, 133, 14);
		add(lblUsername);

		textUsername = new JTextField();
		textUsername.setBounds(329, 127, 288, 26);
		textUsername.setColumns(10);
		add(textUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPassword.setBounds(186, 169, 129, 15);
		add(lblPassword);

		btnAccept = new JButton("Accept");
		btnAccept.setEnabled(false);
		btnAccept.setBounds(660, 386, 132, 36);
		add(btnAccept);

		lblUsernameError = new JLabel("");
		lblUsernameError.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUsernameError.setForeground(new Color(255, 0, 0));
		lblUsernameError.setBounds(89, 132, 311, 14);
		add(lblUsernameError);

		JLabel lblPasswordNotice = new JLabel("Password must be at least 8 characters");
		lblPasswordNotice.setForeground(Color.RED);
		lblPasswordNotice.setBounds(329, 190, 288, 20);
		add(lblPasswordNotice);

		passwordField = new JPasswordField();
		passwordField.setEchoChar('*');
		passwordField.setBounds(329, 164, 288, 26);
		add(passwordField);

		chckbxShowPassword = new JCheckBox("Show");
		chckbxShowPassword.setBounds(623, 166, 69, 23);
		add(chckbxShowPassword);

		chckbxRememberSession = new JCheckBox("Remember login");
		chckbxRememberSession.setBounds(186, 228, 201, 23);
		add(chckbxRememberSession);

		// Click back button
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetContent();
				viewController.switchPanel(viewController.getConnectUI());
			}
		});

		// Typing in the username field
		textUsername.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				usernameValid = false;
				String text = textUsername.getText();

				if (controller.getValidator().validateName(text, lblUsernameError)) {
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

				user = new User(textUsername.getText(), password);

				// Check if the user exists
				try {
					ResultSet rset = (controller.getDataBaseController().queryUser(user));
					if (rset != null) {
						user = new User(rset.getInt("id"), rset.getString("username"), rset.getString("password"),
								rset.getString("email"), rset.getString("last_access"),
								rset.getBoolean("remember_login"));

						// Validate that the user's password matches the input
						if (!user.getPassword().equals(password)) {
							JOptionPane.showMessageDialog(null, "Incorrect password", "Warning",
									JOptionPane.WARNING_MESSAGE);

						} else {
							// If "remember login" is checked, update before logging in
							if (chckbxRememberSession.isSelected()) {
								controller.getDataBaseController().enableRememberLogin(user.getName());
							}
							
							controller.updateUser(user);
							viewController.switchPanel(viewController.getHomeUI());
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
	}

	/**
	 * Resets all content in the login form.
	 * <p>
	 * Clears the username and password fields, resets validation flags, and
	 * unchecks all checkboxes. Used before closing the window or returning to
	 * ConnectUI.
	 * </p>
	 * 
	 * @since 3.0
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
	 * Checks whether the form is valid.
	 * <p>
	 * Enables the accept button only if both username and password pass validation.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public void checkForm() {
		if (usernameValid && passwordValid) {
			btnAccept.setEnabled(true);
		} else {
			btnAccept.setEnabled(false);
		}
	}

	/**
	 * Sets the user associated with this login window.
	 * 
	 * @param user the User object to set
	 * @since 3.3
	 */
	public void setUser(User user) {
		this.user = user;
	}

}