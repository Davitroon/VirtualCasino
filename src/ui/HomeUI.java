package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
 * This panel allows navigation to Play, Management, Statistics, and Profile sections,
 * as well as exiting the application. It also provides an information guide.
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
    private JButton btnStatistics;

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

        JLabel lblTitle = new JLabel("Casino Simulator", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Stencil", Font.PLAIN, 28));
        lblTitle.setBounds(10, 39, 782, 59);
        add(lblTitle);

        JButton btnPlay = new JButton("Play");
        btnPlay.setBackground(new Color(128, 128, 255));
        btnPlay.setBounds(320, 156, 124, 40);
        add(btnPlay);

        JButton btnManagement = new JButton("Management");
        btnManagement.setBackground(new Color(128, 128, 255));
        btnManagement.setBounds(320, 207, 124, 40);
        add(btnManagement);

        btnStatistics = new JButton("Statistics");
        btnStatistics.setBackground(new Color(128, 128, 255));
        btnStatistics.setBounds(320, 258, 124, 40);
        add(btnStatistics);

        JButton btnExit = new JButton("Exit");
        btnExit.setBackground(new Color(128, 128, 128));
        btnExit.setBounds(10, 390, 132, 36);
        add(btnExit);

        JButton btnInfo = new JButton("?");
        btnInfo.setBackground(new Color(128, 255, 255));
        btnInfo.setBounds(755, 390, 37, 32);
        add(btnInfo);

        JButton btnProfile = new JButton("Profile");
        btnProfile.setBounds(694, 11, 98, 32);
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

                        ~ Version: 3.1 ~
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
     * @since 3.1
     */
    public void initializeClassesUI() {
        playUI = viewController.getPlayUI();
        profileUI = viewController.getProfileUI();
        statsUI = viewController.getStatsUI();
        managementUI = viewController.getManagementUI();
    }
}
