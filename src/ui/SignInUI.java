package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLIntegrityConstraintViolationException;

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

import controller.DataBaseController;
import controller.MainController;
import controller.ViewController;
import exceptions.MailException;
import model.User;

/**
 * Window where a user can be created and log in as that user.
 * 
 * @author David
 * @since 3.0
 */
public class SignInUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
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
	private JLabel lblEmail;
	private JTextField txtEmail;
	private JCheckBox chkShowPassword;

	private User user;
	private JLabel lblPasswordNotice;

	/**
	 * Creates the frame for creating a user and signing in as them.
	 * 
	 * @param model        The data model
	 * @param controller   The controller handling logic
	 * @param validatorThe validator for form fields
	 * @since 3.0
	 */
	public SignInUI(MainController controller) {

		DataBaseController dbController = controller.getDataBaseController();
		ViewController viewController = controller.getViewController();

		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 658, 363);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCrearuser = new JLabel("Create User", SwingConstants.CENTER);
		lblCrearuser.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblCrearuser.setBounds(10, 29, 622, 31);
		contentPane.add(lblCrearuser);

		JButton btnVolver = new JButton("Back");
		btnVolver.setBackground(Color.GRAY);
		btnVolver.setBounds(27, 276, 105, 32);
		contentPane.add(btnVolver);

		JLabel lblNombre = new JLabel("Username");
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNombre.setBounds(27, 106, 133, 14);
		contentPane.add(lblNombre);

		txtUsername = new JTextField();
		txtUsername.setBounds(161, 101, 172, 26);
		txtUsername.setColumns(10);
		contentPane.add(txtUsername);

		JLabel lblContrasena = new JLabel("Password");
		lblContrasena.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblContrasena.setBounds(27, 161, 129, 15);
		contentPane.add(lblContrasena);

		btnAccept = new JButton("Accept");
		btnAccept.setEnabled(false);
		btnAccept.setBounds(517, 276, 105, 32);
		contentPane.add(btnAccept);

		lblUsernameError = new JLabel("");
		lblUsernameError.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUsernameError.setForeground(new Color(255, 0, 0));
		lblUsernameError.setBounds(27, 131, 306, 14);
		contentPane.add(lblUsernameError);

		lblPasswordNotice = new JLabel("Password must be at least 8 characters");
		lblPasswordNotice.setForeground(Color.RED);
		lblPasswordNotice.setBounds(27, 187, 272, 20);
		contentPane.add(lblPasswordNotice);

		passwordField = new JPasswordField();
		passwordField.setEchoChar('*');
		passwordField.setBounds(120, 156, 165, 26);
		contentPane.add(passwordField);

		chkShowPassword = new JCheckBox("Show");
		chkShowPassword.setBounds(305, 186, 69, 23);
		contentPane.add(chkShowPassword);

		chkRememberLogin = new JCheckBox("Remember login");
		chkRememberLogin.setBounds(27, 219, 201, 23);
		contentPane.add(chkRememberLogin);

		JLabel lblContrasena2 = new JLabel("Repeat Password");
		lblContrasena2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblContrasena2.setBounds(295, 161, 129, 15);
		contentPane.add(lblContrasena2);

		passwordField2 = new JPasswordField();
		passwordField2.setEchoChar('*');
		passwordField2.setBounds(434, 156, 149, 26);
		contentPane.add(passwordField2);

		lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEmail.setBounds(352, 106, 58, 14);
		contentPane.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(411, 101, 172, 26);
		contentPane.add(txtEmail);

		JLabel lblErrorCorreo = new JLabel("");
		lblErrorCorreo.setForeground(Color.RED);
		lblErrorCorreo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErrorCorreo.setBounds(351, 131, 232, 14);
		contentPane.add(lblErrorCorreo);

		// Click back button
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetContent();
				viewController.switchWindow(SignInUI.this, viewController.getConnectUI());
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
					lblErrorCorreo.setText("");
				} else {
					lblErrorCorreo.setText("Invalid email format");
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
						viewController.switchWindow(SignInUI.this, viewController.getHomeUI());
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

		// When closing the window using the X
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				resetContent();
				viewController.switchWindow(SignInUI.this, viewController.getLogInUI());
			}
		});
	}

	/**
	 * Resets the page content when closing.
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
	 * Checks the form to ensure the data is valid.
	 */
	public void checkForm() {
		if (validPassword && validPassword2 && validUsername && validEmail) {
			btnAccept.setEnabled(true);
		} else {
			btnAccept.setEnabled(false);
		}
	}
}
