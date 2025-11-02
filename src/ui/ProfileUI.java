package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

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
	private JPanel contentPane;
	private JLabel lblUser;
	private JLabel lblLastAccess;
	private JCheckBox chckbxRememberSession;
	private JLabel lblEmail;
	private JButton btnDeleteUser;

	/**
	 * Constructs the ProfileUI frame.
	 * <p>
	 * Initializes the window layout, labels, buttons, and event listeners for
	 * logging out, going back, toggling session remember, and deleting the user.
	 * </p>
	 * 
	 * @param controller The main controller handling user actions and window changes
	 * @since 3.0
	 */
	public ProfileUI(MainController controller) {
		
		ViewController viewController = controller.getViewController();	
		DataBaseController dbController = controller.getDataBaseController();	
		
		setBounds(100, 100, 542, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		JLabel lblProfile = new JLabel("Profile", SwingConstants.CENTER);
		lblProfile.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblProfile.setBounds(10, 24, 506, 59);
		contentPane.add(lblProfile);

		lblUser = new JLabel("Logged in as:");
		lblUser.setBounds(72, 95, 433, 24);
		contentPane.add(lblUser);

		JButton btnBack = new JButton("Back");
		btnBack.setBackground(Color.GRAY);
		btnBack.setBounds(29, 293, 103, 31);
		contentPane.add(btnBack);

		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.setForeground(Color.BLACK);
		btnLogOut.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnLogOut.setBackground(new Color(242, 77, 77));
		btnLogOut.setBounds(363, 291, 142, 32);
		contentPane.add(btnLogOut);

		lblEmail = new JLabel("Email:");
		lblEmail.setBounds(72, 131, 433, 24);
		contentPane.add(lblEmail);

		lblLastAccess = new JLabel("Last access:");
		lblLastAccess.setBounds(72, 167, 433, 24);
		contentPane.add(lblLastAccess);

		chckbxRememberSession = new JCheckBox("Remember session");
		chckbxRememberSession.setBounds(72, 198, 433, 23);
		contentPane.add(chckbxRememberSession);

		btnDeleteUser = new JButton("Delete user");
		btnDeleteUser.setForeground(Color.BLACK);
		btnDeleteUser.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnDeleteUser.setBackground(new Color(242, 77, 77));
		btnDeleteUser.setBounds(363, 248, 142, 32);
		contentPane.add(btnDeleteUser);

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

	/**
	 * Loads and displays the data of the currently logged-in user.
	 * <p>
	 * Updates labels for username, email, last access, and the remember session checkbox.
	 * </p>
	 * 
	 * @param user The currently logged-in user
	 * @since 3.0
	 */
	public void upateUserData(User user) {
		lblUser.setText("Logged in as: " + user.getName());
		lblEmail.setText("Associated email: " + user.getEmail());
		lblLastAccess.setText("Last access: " + user.getLastAccess());
		chckbxRememberSession.setSelected(user.isRememberSession());
	}

}
