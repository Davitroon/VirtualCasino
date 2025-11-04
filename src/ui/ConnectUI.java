package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.MainController;
import controller.ViewController;

/**
 * Window that allows the user to log in or create a profile.
 * <p>
 * This window serves as the start screen of the application if automatic login
 * is not enabled. Users can choose to create a new account, log in with an
 * existing account, or exit the application.
 * </p>
 * 
 * @author Davitroon
 * @since 3.0
 */
public class ConnectUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private HomeUI menu;

	/**
	 * Constructs the ConnectUI window.
	 * <p>
	 * Initializes all UI components, sets up event listeners for buttons, and
	 * handles closing the application.
	 * </p>
	 * 
	 * @param controller Reference to the MainController that handles program logic
	 *                   and window transitions.
	 * @since 3.0
	 */
	public ConnectUI(MainController controller) {

		ViewController viewController = controller.getViewController();

		setBounds(100, 100, 802, 433);
		setLayout(null);
		setBackground(new Color(220, 220, 220));

		JLabel lblTitle = new JLabel("Connect", SwingConstants.CENTER);
		lblTitle.setFont(new Font("Segoe UI Black", Font.BOLD, 36));
		lblTitle.setBounds(138, 21, 525, 50);
		add(lblTitle);

		JButton btnCreateUser = new JButton("Create User");
		btnCreateUser.setBackground(new Color(128, 128, 255));
		btnCreateUser.setBounds(259, 183, 132, 45);
		btnCreateUser.setForeground(Color.WHITE);
		btnCreateUser.setFocusPainted(false);
		btnCreateUser.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnCreateUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCreateUser.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		add(btnCreateUser);

		JButton btnLogIn = new JButton("Log In");
		btnLogIn.setBackground(new Color(128, 128, 255));
		btnLogIn.setBounds(423, 183, 132, 45);
		btnLogIn.setForeground(Color.WHITE);
		btnLogIn.setFocusPainted(false);
		btnLogIn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnLogIn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnLogIn.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		add(btnLogIn);

		JButton btnExit = new JButton("Exit");
		btnExit.setForeground(Color.WHITE);
		btnExit.setFocusPainted(false);
		btnExit.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnExit.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		btnExit.setBackground(new Color(128, 128, 128));
		btnExit.setBounds(10, 386, 132, 36);
		add(btnExit);

		// Click "Exit" button
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.closeProgram();
				System.exit(0);
			}
		});

		// Click "Create User" button
		btnCreateUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewController.switchPanel(viewController.getSignInUI());
			}
		});

		// Click "Log In" button
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewController.switchPanel(viewController.getLogInUI());
			}
		});
	}

	/**
	 * Returns the reference to the main menu window (HomeUI) associated with this
	 * ConnectUI.
	 * 
	 * @return the HomeUI menu window
	 * @since 3.3
	 */
	public HomeUI getMenu() {
		return menu;
	}
}