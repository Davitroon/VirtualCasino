package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.MainController;
import controller.ViewController;
import exceptions.GameException;

/**
 * Main menu window.
 * 
 * @author David
 * @since 3.0
 */
public class HomeUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private ManagementUI managementUI;
	private PlayUI playUI;
	private StatsUI statsUI;
	private ProfileUI profileUI;
	private JButton btnStatistics;
	
	private ViewController viewController;

	/**
	 * Creates the frame.
	 * 
	 * @param controller The controller handling interactions.
	 */
	public HomeUI(MainController controller) {

		viewController = controller.getViewController();

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 370);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitle = new JLabel("Casino Simulator", SwingConstants.CENTER);
		lblTitle.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblTitle.setBounds(60, 21, 375, 59);
		contentPane.add(lblTitle);

		JButton btnPlay = new JButton("Play");
		btnPlay.setBackground(new Color(128, 128, 255));
		btnPlay.setBounds(193, 101, 124, 40);
		contentPane.add(btnPlay);

		JButton btnManagement = new JButton("Management");
		btnManagement.setBackground(new Color(128, 128, 255));
		btnManagement.setBounds(193, 152, 124, 40);
		contentPane.add(btnManagement);

		btnStatistics = new JButton("Statistics");
		btnStatistics.setBackground(new Color(128, 128, 255));
		btnStatistics.setBounds(193, 203, 124, 40);
		contentPane.add(btnStatistics);

		JButton btnExit = new JButton("Exit");
		btnExit.setBackground(new Color(128, 128, 128));
		btnExit.setBounds(10, 284, 105, 32);
		contentPane.add(btnExit);

		JButton btnInfo = new JButton("?");
		btnInfo.setBackground(new Color(128, 255, 255));
		btnInfo.setBounds(465, 288, 37, 32);
		contentPane.add(btnInfo);

		JButton btnProfile = new JButton("Profile");
		btnProfile.setBounds(445, 11, 57, 32);
		contentPane.add(btnProfile);

		// Close window
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				controller.closeProgram();
			}
		});

		// Click play button
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					playUI.updateTables();
					viewController.switchWindow(HomeUI.this, playUI);

				} catch (GameException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		// Click management button
		btnManagement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewController.switchWindow(HomeUI.this, managementUI);
			}
		});

		// Click statistics button
		btnStatistics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				StatsUI tempStatsUI = HomeUI.this.getStatsUI();
				tempStatsUI.updateUser(controller);
				tempStatsUI.updateData();
				viewController.switchWindow(HomeUI.this, tempStatsUI);
			}
		});

		// Click profile button
		btnProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProfileUI tempProfileUI = HomeUI.this.getProfileUI();
				tempProfileUI.upateUserData(controller.getCurrentUser());
				viewController.switchWindow(HomeUI.this, tempProfileUI);
			}
		});

		// Click exit button
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.closeProgram();
				System.exit(0);
			}
		});

		// Click info button
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = """
						What is this application?

						- It is a program designed to simulate casino management.
						- You can simulate real casino games in the "Play" tab
						  (Currently only Blackjack and Slot Machines are available).
						- To play, you will need to create Users and Games, which you can manage
						  in the "Management" section.
						- All your games will be saved in the database, and if you're curious, you can
						  view your statistics in its corresponding window.

						Have fun and experiment!

						~ Version: 3.0 ~
						""";

				JOptionPane.showMessageDialog(null, message, "Application Guide", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}
	
	public void initializeClassesUI() {
		playUI = viewController.getPlayUI();
		profileUI = viewController.getProfileUI();
		statsUI = viewController.getStatsUI();
		managementUI = viewController.getManagementUI();
	}

	public PlayUI getPlayUI() {
		return playUI;
	}

	public StatsUI getStatsUI() {
		return statsUI;
	}

	public ProfileUI getProfileUI() {
		return profileUI;
	}
	
	
}