package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import logic.Controller;
import logic.Model;
import logic.Session;
import logic.Validator;
import ui.ConnectUI;
import ui.SignInUI;
import ui.LogInUI;
import ui.HomeUI;

/**
 * Window where the user can log in with a profile from the database. This will
 * be the program's start screen if automatic login has not been set.
 * 
 * @author David
 * @since 3.0
 */
public class ConnectUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private LogInUI logIn;
	private SignInUI createUser;
	private HomeUI menu;

	/**
	 * Create the frame.
	 * 
	 * @param model      Reference to the model that manages data operations.
	 * @param controller Reference to the controller handling program logic.
	 * @param session    Current session information.
	 * @param validator  Reference to the validator for input checks.
	 * @param menu       Reference to the home menu window.
	 * @since 3.0
	 */
	public ConnectUI(Model model, Controller controller, Session session, Validator validator, HomeUI menu) {
		this.menu = menu;

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 501, 301);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitle = new JLabel("Connect", SwingConstants.CENTER);
		lblTitle.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblTitle.setBounds(10, 28, 465, 31);
		contentPane.add(lblTitle);

		JButton btnCreateUser = new JButton("Create User");
		btnCreateUser.setBackground(new Color(128, 128, 255));
		btnCreateUser.setBounds(113, 112, 117, 45);
		contentPane.add(btnCreateUser);

		JButton btnLogIn = new JButton("Log In");
		btnLogIn.setBackground(new Color(128, 128, 255));
		btnLogIn.setBounds(253, 112, 117, 45);
		contentPane.add(btnLogIn);

		JButton btnExit = new JButton("Exit");
		btnExit.setBackground(Color.GRAY);
		btnExit.setBounds(10, 219, 105, 32);
		contentPane.add(btnExit);

		// Close window
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				model.closeConnection();
				System.exit(0);
			}
		});

		// Click "Exit" button
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.closeConnection();
				System.exit(0);
			}
		});

		// Click "Create User" button
		btnCreateUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (createUser == null) {
					createUser = new SignInUI(model, controller, session, ConnectUI.this, validator);
				}
				setVisible(false);
				createUser.setVisible(true);
			}
		});

		// Click "Log In" button
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (logIn == null) {
					logIn = new LogInUI(model, controller, session, ConnectUI.this, validator);
				}
				setVisible(false);
				logIn.setVisible(true);
			}
		});
	}

	public HomeUI getMenu() {
		return menu;
	}
}