package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.MainController;
import controller.ViewController;


/**
 * Window that allows the user to log in or create a profile.
 * <p>
 * This window serves as the start screen of the application if automatic
 * login is not enabled. Users can choose to create a new account, log in
 * with an existing account, or exit the application.
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
     * Initializes all UI components, sets up event listeners for buttons,
     * and handles closing the application.
     * </p>
     * 
     * @param controller Reference to the MainController that handles program
     *                   logic and window transitions.
     * @since 3.0
     */
	public ConnectUI(MainController controller) {

		ViewController viewController = controller.getViewController();

		setBounds(100, 100, 802, 433);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);

		JLabel lblTitle = new JLabel("Connect", SwingConstants.CENTER);
		lblTitle.setFont(new Font("Stencil", Font.PLAIN, 30));
		lblTitle.setBounds(10, 28, 782, 45);
		add(lblTitle);

		JButton btnCreateUser = new JButton("Create User");
		btnCreateUser.setBackground(new Color(128, 128, 255));
		btnCreateUser.setBounds(261, 213, 117, 45);
		add(btnCreateUser);

		JButton btnLogIn = new JButton("Log In");
		btnLogIn.setBackground(new Color(128, 128, 255));
		btnLogIn.setBounds(410, 213, 117, 45);
		add(btnLogIn);

		JButton btnExit = new JButton("Exit");
		btnExit.setBackground(Color.GRAY);
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