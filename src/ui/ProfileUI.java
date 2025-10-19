package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import dao.DBManagement;
import model.User;
import ui.HomeUI;
import ui.ProfileUI;

/**
 * Window that displays information about the user who is currently logged in.
 * 
 * @author David
 * @since 3.0
 */
public class ProfileUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblUser;
	private JLabel lblLastAccess;
	private JCheckBox chckbxRememberSession;
	private JLabel lblEmail;
	private User user;
	private JButton btnDeleteUser;

	/**
	 * Constructs the ProfileUI frame, which displays information about the
	 * currently logged-in user.
	 *
	 * @param menu       The main HomeUI window from which this ProfileUI is opened
	 * @param controller The controller responsible for handling window changes and
	 *                   user actions
	 * @param model      The model used to access and update user data
	 */
	public ProfileUI(HomeUI menu, Controller controller, DBManagement model) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 542, 390);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
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
				controller.switchWindow(ProfileUI.this, menu);
			}
		});

		// When closing the window using the X
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controller.switchWindow(ProfileUI.this, menu);
			}
		});

		// Log out button
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.switchWindow(ProfileUI.this, menu.getConnectUI());
				model.updateLastAccess(user.getName());
			}
		});

		// Toggle remember session
		chckbxRememberSession.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.toggleRememberLogin(user.getName(), chckbxRememberSession.isSelected());
			}
		});

		// Delete user button
		btnDeleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this user?",
						"Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (response == JOptionPane.YES_OPTION) {
					controller.switchWindow(ProfileUI.this, menu.getConnectUI());
					model.deleteData(user.getId(), "users");
				}
			}
		});
	}

	/**
	 * Loads the data of the currently logged-in user.
	 * 
	 * @param user Current user.
	 * @since 3.0
	 */
	public void updateData(User user) {
		this.user = user;
		lblUser.setText("Logged in as: " + user.getName());
		lblEmail.setText("Associated email: " + user.getEmail());
		lblLastAccess.setText("Last access: " + user.getLastAccess());
		chckbxRememberSession.setSelected(user.isRememberSession());
	}

}
