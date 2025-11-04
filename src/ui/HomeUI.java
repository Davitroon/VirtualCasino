package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.MainController;
import controller.ViewController;
import exceptions.GameException;

/**
 * Main menu panel of the casino simulator.
 * <p>
 * This panel allows navigation to Play, Management, Statistics, and Profile
 * sections, as well as exiting the application. It also provides an information
 * guide.
 * </p>
 * 
 * @author Davitroon
 * @since 3.1
 */
public class HomeUI extends JPanel {

	private static final long serialVersionUID = 1L;

	private ManagementUI managementUI;
	private PlayUI playUI;
	private StatsUI statsUI;
	private ProfileUI profileUI;

	private ViewController viewController;

	/**
	 * Constructs the main menu panel.
	 * 
	 * @param controller The main controller handling logic and interactions.
	 * @since 3.1
	 */
	public HomeUI(MainController controller) {
		this.viewController = controller.getViewController();

		setBounds(100, 100, 802, 433);
		setLayout(null);
		setBackground(new Color(220, 220, 220));

		JLabel lblTitle = new JLabel("Virtual Casino", SwingConstants.CENTER);
		lblTitle.setFont(new Font("Segoe UI Black", Font.BOLD, 36));
		lblTitle.setBounds(138, 21, 525, 50);
		add(lblTitle);

		JButton btnPlay = new JButton("Play");
		btnPlay.setBackground(new Color(128, 128, 255));
		btnPlay.setBounds(319, 156, 164, 40);
		btnPlay.setForeground(Color.WHITE);
		btnPlay.setFocusPainted(false);
		btnPlay.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnPlay.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnPlay.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		add(btnPlay);

		JButton btnManagement = new JButton("Management");
		btnManagement.setBackground(new Color(128, 128, 255));
		btnManagement.setBounds(319, 207, 164, 40);
		btnManagement.setForeground(Color.WHITE);
		btnManagement.setFocusPainted(false);
		btnManagement.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnManagement.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnManagement.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		add(btnManagement);

		JButton btnStatistics = new JButton("Statistics");
		btnStatistics.setBackground(new Color(128, 128, 255));
		btnStatistics.setBounds(319, 258, 164, 40);
		btnStatistics.setForeground(Color.WHITE);
		btnStatistics.setFocusPainted(false);
		btnStatistics.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnStatistics.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnStatistics.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		add(btnStatistics);

		JButton btnExit = new JButton("Exit");
		btnExit.setForeground(Color.WHITE);
		btnExit.setFocusPainted(false);
		btnExit.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnExit.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		btnExit.setBackground(new Color(128, 128, 128));
		btnExit.setBounds(10, 386, 132, 36);
		add(btnExit);

		JButton btnInfo = new JButton("?");
		btnInfo.setToolTipText("");
		btnInfo.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnInfo.setBackground(new Color(128, 255, 255));
		btnInfo.setBounds(755, 385, 37, 38);
		btnInfo.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		btnInfo.setFocusPainted(false);
		btnInfo.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		add(btnInfo);

		JButton btnProfile = new JButton("Profile");
		btnProfile.setBackground(new Color(128, 128, 128));
		btnProfile.setBounds(713, 11, 79, 32);
		btnProfile.setForeground(Color.WHITE);
		btnProfile.setFocusPainted(false);
		btnProfile.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnProfile.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnProfile.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		add(btnProfile);

		// --- Event Listeners ---

		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					playUI.updateTables();
					viewController.switchPanel(playUI);
				} catch (GameException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		btnManagement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				managementUI.updateClientsTable();
				managementUI.updateGamesTable();
				viewController.switchPanel(managementUI);
			}
		});

		btnStatistics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statsUI.updateUser(controller);
				statsUI.updateData();
				viewController.switchPanel(statsUI);
			}
		});

		btnProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				profileUI.upateUserData(controller.getCurrentUser());
				viewController.switchPanel(profileUI);
			}
		});

		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.closeProgram();
				System.exit(0);
			}
		});

		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = """
						What is this application?

						- It is a program designed to simulate casino management.
						- You can simulate real casino games in the "Play" tab
						  (Currently only Blackjack and Slot Machines are available).
						- To play, you will need to create Users and Games, which you can manage
						  in the "Management" section.
						- All your games will be saved in the database, and you can
						  view your statistics in its corresponding window.

						Have fun and experiment!

						~ Version: 3.3 ~
						""";
				JOptionPane.showMessageDialog(null, message, "Application Guide", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	/**
	 * Initializes references to other UI panels.
	 * <p>
	 * Must be called after all UI classes are created in ViewController.
	 * </p>
	 * 
	 * @since 3.1
	 */
	public void initializeClassesUI() {
		playUI = viewController.getPlayUI();
		profileUI = viewController.getProfileUI();
		statsUI = viewController.getStatsUI();
		managementUI = viewController.getManagementUI();
	}
}
