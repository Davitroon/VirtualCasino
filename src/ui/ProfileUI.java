package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.DataBaseController;
import controller.MainController;
import controller.ViewController;
import model.User;

/**
 * Window that displays information about the currently logged-in user.
 * <p>
 * Shows the user's name, email, last access date, and session preferences.
 * Provides buttons to log out, go back to the home window, or delete the user.
 * </p>
 * 
 * @author Davitroon
 * @since 3.0
 */
public class ProfileUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private JCheckBox chckbxRememberSession;
	private JLabel lblUserTitle;
	private JLabel lblLastAccess;
	private JLabel lblEmail;
	private JLabel lblUser;

	/**
	 * Constructs the ProfileUI frame.
	 * <p>
	 * Initializes the window layout, labels, buttons, and event listeners for
	 * logging out, going back, toggling session remember, and deleting the user.
	 * </p>
	 * 
	 * @param controller The main controller handling user actions and window
	 *                   changes
	 * @since 3.0
	 */
	public ProfileUI(MainController controller) {

		ViewController viewController = controller.getViewController();
		DataBaseController dbController = controller.getDataBaseController();

		setBounds(100, 100, 802, 433);
		setLayout(null);
		setBackground(new Color(220, 220, 220));

		// Título principal
		JLabel lblProfile = new JLabel("Profile", SwingConstants.CENTER);
		lblProfile.setFont(new Font("Segoe UI Black", Font.BOLD, 36));
		lblProfile.setBounds(138, 21, 525, 50);
		add(lblProfile);

		// Email
		JLabel lblEmailTitle = new JLabel("Email:");
		lblEmailTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblEmailTitle.setForeground(new Color(60, 60, 60));
		lblEmailTitle.setBounds(219, 156, 132, 30);
		add(lblEmailTitle);

		// Último acceso
		JLabel lblLastAccessTitle = new JLabel("Last access:");
		lblLastAccessTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblLastAccessTitle.setForeground(new Color(60, 60, 60));
		lblLastAccessTitle.setBounds(219, 196, 132, 30);
		add(lblLastAccessTitle);

		// Checkbox recordar sesión
		chckbxRememberSession = new JCheckBox("Remember session");
		chckbxRememberSession.setBounds(277, 246, 200, 25);
		chckbxRememberSession.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		chckbxRememberSession.setBackground(new Color(245, 245, 245));
		add(chckbxRememberSession);

		// Botones
		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		btnBack.setForeground(Color.WHITE);
		btnBack.setBackground(new Color(120, 120, 120));
		btnBack.setFocusPainted(false);
		btnBack.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnBack.setBounds(10, 386, 132, 36);
		add(btnBack);

		JButton btnDeleteUser = new JButton("Delete User");
		btnDeleteUser.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		btnDeleteUser.setForeground(Color.WHITE);
		btnDeleteUser.setBackground(new Color(242, 77, 77));
		btnDeleteUser.setFocusPainted(false);
		btnDeleteUser.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnDeleteUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnDeleteUser.setBounds(506, 386, 132, 36);
		add(btnDeleteUser);

		JButton btnLogOut = createButton("Log Out", new Color(242, 77, 77));
		btnLogOut.setBounds(660, 385, 132, 36);
		add(btnLogOut);

		lblUser = new JLabel("");
		lblUser.setForeground(new Color(60, 60, 60));
		lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblUser.setBounds(364, 116, 260, 30);
		add(lblUser);

		lblEmail = new JLabel("");
		lblEmail.setForeground(new Color(60, 60, 60));
		lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblEmail.setBounds(364, 156, 260, 30);
		add(lblEmail);

		lblLastAccess = new JLabel("");
		lblLastAccess.setForeground(new Color(60, 60, 60));
		lblLastAccess.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblLastAccess.setBounds(361, 196, 263, 30);
		add(lblLastAccess);

		lblUserTitle = new JLabel("Logged in as:");
		lblUserTitle.setForeground(new Color(60, 60, 60));
		lblUserTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblUserTitle.setBounds(217, 116, 132, 30);
		add(lblUserTitle);

		// Click back button
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewController.switchPanel(viewController.getHomeUI());
			}
		});

		// Log out button
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				dbController.disableRememberLogin();
				viewController.switchPanel(viewController.getConnectUI());
			}
		});

		// Toggle remember session
		chckbxRememberSession.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxRememberSession.isSelected()) {
					dbController.enableRememberLogin(controller.getCurrentUser().getName());

				} else {
					dbController.disableRememberLogin();
				}
			}
		});

		// Delete user button
		btnDeleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this user?",
						"Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (response == JOptionPane.YES_OPTION) {
					viewController.switchPanel(viewController.getConnectUI());
					dbController.deleteUser(controller.getCurrentUser().getId());
				}
			}
		});
	}

	// Método para crear botones con estilo
	private JButton createButton(String text, Color bgColor) {
		JButton btn = new JButton(text);
		btn.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		btn.setForeground(Color.WHITE);
		btn.setBackground(bgColor);
		btn.setFocusPainted(false);
		btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

		return btn;
	}

	/**
	 * Loads and displays the data of the currently logged-in user.
	 * <p>
	 * Updates labels for username, email, last access, and the remember session
	 * checkbox.
	 * </p>
	 * 
	 * @param user The currently logged-in user
	 * @since 3.0
	 */
	public void upateUserData(User user) {
		lblUser.setText(user.getName());
		lblEmail.setText(user.getEmail());
		lblLastAccess.setText(user.getLastAccess());
		chckbxRememberSession.setSelected(user.isRememberSession());
	}
}
