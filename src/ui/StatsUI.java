package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
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
 * Window where game statistics are stored.
 * @author David
 * @since 3.0
 */
public class StatsUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private JLabel lblGamesPlayedVal;
	private JLabel lblGamesWonVal;
	private JLabel lblGamesLostVal;
	private JLabel lblBlackjackGamesVal;
	private JLabel lblSlotGamesVal;
	private JLabel lblLastGameVal;
	private JLabel lblGameMoneyVal;
	private JLabel lblMoneyLostVal;
	private JLabel lblClientBalanceVal;
	private JLabel lblMoneyWonVal;
	private JButton btnDeleteStatistics;

	private DataBaseController dbController;
	private User user;
	private JLabel lblUser;

	/**
     * Creates the frame.
     * 
     * @param controller The controller handling application logic.
     */
    public StatsUI(MainController controller) {
    	dbController = controller.getDataBaseController();
        user = controller.getCurrentUser();
        ViewController viewController = controller.getViewController();

        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 682, 399);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblStatistics = new JLabel("Statistics", SwingConstants.CENTER);
        lblStatistics.setFont(new Font("Stencil", Font.PLAIN, 28));
        lblStatistics.setBounds(10, 40, 648, 39);
        contentPane.add(lblStatistics);

        JButton btnBack = new JButton("Back");
        btnBack.setBackground(new Color(128, 128, 128));
        btnBack.setBounds(20, 297, 120, 32);
        contentPane.add(btnBack);

        JLabel lblGamesPlayed = new JLabel("Games played:");
        lblGamesPlayed.setFont(new Font("SimSun", Font.BOLD, 14));
        lblGamesPlayed.setBounds(20, 122, 142, 24);
        contentPane.add(lblGamesPlayed);

        JLabel lblGamesWon = new JLabel("Games won:");
        lblGamesWon.setFont(new Font("SimSun", Font.BOLD, 14));
        lblGamesWon.setBounds(20, 157, 142, 24);
        contentPane.add(lblGamesWon);

        JLabel lblGamesLost = new JLabel("Games lost:");
        lblGamesLost.setFont(new Font("SimSun", Font.BOLD, 14));
        lblGamesLost.setBounds(20, 192, 142, 24);
        contentPane.add(lblGamesLost);

        JLabel lblClientBalance = new JLabel("Client with highest balance:");
        lblClientBalance.setFont(new Font("SimSun", Font.BOLD, 14));
        lblClientBalance.setBounds(314, 192, 170, 24);
        contentPane.add(lblClientBalance);

        JLabel lblGameMoney = new JLabel("Game with most money:");
        lblGameMoney.setFont(new Font("SimSun", Font.BOLD, 14));
        lblGameMoney.setBounds(314, 227, 162, 24);
        contentPane.add(lblGameMoney);

        JLabel lblBlackjackGames = new JLabel("Blackjack games:");
        lblBlackjackGames.setFont(new Font("SimSun", Font.BOLD, 14));
        lblBlackjackGames.setBounds(20, 227, 178, 24);
        contentPane.add(lblBlackjackGames);

        JLabel lblSlotGames = new JLabel("Slot machine games:");
        lblSlotGames.setFont(new Font("SimSun", Font.BOLD, 14));
        lblSlotGames.setBounds(20, 262, 193, 24);
        contentPane.add(lblSlotGames);

        JLabel lblMoneyLost = new JLabel("Money lost:");
        lblMoneyLost.setFont(new Font("SimSun", Font.BOLD, 14));
        lblMoneyLost.setBounds(314, 157, 120, 24);
        contentPane.add(lblMoneyLost);

        JLabel lblMoneyWon = new JLabel("Money won:");
        lblMoneyWon.setFont(new Font("SimSun", Font.BOLD, 14));
        lblMoneyWon.setBounds(314, 122, 120, 24);
        contentPane.add(lblMoneyWon);

        JLabel lblLastGame = new JLabel("Last game played:");
        lblLastGame.setFont(new Font("SimSun", Font.BOLD, 14));
        lblLastGame.setBounds(314, 262, 178, 24);
        contentPane.add(lblLastGame);

        lblGamesPlayedVal = new JLabel("lorem");
        lblGamesPlayedVal.setFont(new Font("SimSun", Font.PLAIN, 14));
        lblGamesPlayedVal.setBounds(155, 127, 141, 14);
        contentPane.add(lblGamesPlayedVal);

        lblGamesWonVal = new JLabel("lorem");
        lblGamesWonVal.setFont(new Font("SimSun", Font.PLAIN, 14));
        lblGamesWonVal.setBounds(155, 163, 149, 14);
        contentPane.add(lblGamesWonVal);

        lblGamesLostVal = new JLabel("lorem");
        lblGamesLostVal.setFont(new Font("SimSun", Font.PLAIN, 14));
        lblGamesLostVal.setBounds(164, 197, 140, 14);
        contentPane.add(lblGamesLostVal);

        lblBlackjackGamesVal = new JLabel("lorem");
        lblBlackjackGamesVal.setFont(new Font("SimSun", Font.PLAIN, 14));
        lblBlackjackGamesVal.setBounds(197, 233, 108, 14);
        contentPane.add(lblBlackjackGamesVal);

        lblSlotGamesVal = new JLabel("lorem");
        lblSlotGamesVal.setFont(new Font("SimSun", Font.PLAIN, 14));
        lblSlotGamesVal.setBounds(211, 268, 93, 14);
        contentPane.add(lblSlotGamesVal);

        lblMoneyWonVal = new JLabel("lorem");
        lblMoneyWonVal.setFont(new Font("SimSun", Font.PLAIN, 14));
        lblMoneyWonVal.setBounds(433, 127, 225, 14);
        contentPane.add(lblMoneyWonVal);

        lblMoneyLostVal = new JLabel("lorem");
        lblMoneyLostVal.setFont(new Font("SimSun", Font.PLAIN, 14));
        lblMoneyLostVal.setBounds(439, 163, 219, 14);
        contentPane.add(lblMoneyLostVal);

        lblClientBalanceVal = new JLabel("lorem");
        lblClientBalanceVal.setFont(new Font("SimSun", Font.PLAIN, 14));
        lblClientBalanceVal.setBounds(488, 197, 170, 14);
        contentPane.add(lblClientBalanceVal);

        lblGameMoneyVal = new JLabel("lorem");
        lblGameMoneyVal.setFont(new Font("SimSun", Font.PLAIN, 14));
        lblGameMoneyVal.setBounds(480, 232, 178, 14);
        contentPane.add(lblGameMoneyVal);

        lblLastGameVal = new JLabel("lorem");
        lblLastGameVal.setFont(new Font("SimSun", Font.PLAIN, 14));
        lblLastGameVal.setBounds(490, 267, 168, 14);
        contentPane.add(lblLastGameVal);

        btnDeleteStatistics = new JButton("Delete statistics");
        btnDeleteStatistics.setForeground(new Color(0, 0, 0));
        btnDeleteStatistics.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnDeleteStatistics.setBackground(new Color(242, 77, 77));
        btnDeleteStatistics.setBounds(516, 90, 142, 32);
        contentPane.add(btnDeleteStatistics);

        lblUser = new JLabel("Statistics for ");
        lblUser.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblUser.setBounds(20, 95, 178, 14);
        contentPane.add(lblUser);

     // When closing the window using the X button
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	viewController.switchWindow(StatsUI.this, viewController.getHomeUI());
            }
        });

        // Click "Back" button
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	viewController.switchWindow(StatsUI.this, viewController.getHomeUI());
            }
        });

        // Click "Delete statistics" button
        btnDeleteStatistics.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete all statistics?", "Confirm deletion",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (option == JOptionPane.YES_OPTION) {
                	dbController.deleteGameSessions();
                    updateData();
                }
            }
        });
    }

	/**
	 * Updates the statistics by querying the database. If a query returns no data,
	 * it will be marked as null.
	 */
	public void updateData() {
		lblUser.setText("Statistics for " + user.getName());
		ResultSet rset = null;
		try {

			rset = dbController.queryGameSessions();
			if (!rset.next()) {
				btnDeleteStatistics.setEnabled(false);

			} else {
				btnDeleteStatistics.setEnabled(true);
			}

			// Games played
			rset = dbController.specificQuery("SELECT COUNT(*) FROM game_sessions WHERE user_profile = " + user.getId() + ";");
			rset.next();
			lblGamesPlayedVal.setText(rset.getString(1));

			// Games won (bet_result > 0)
			rset = dbController.specificQuery(
					"SELECT COUNT(*) FROM game_sessions WHERE bet_result > 0 AND user_profile = " + user.getId() + ";");
			rset.next();
			lblGamesWonVal.setText(rset.getString(1));

			// Games lost (bet_result < 0)
			rset = dbController.specificQuery(
					"SELECT COUNT(*) FROM game_sessions WHERE bet_result < 0 AND user_profile = " + user.getId() + ";");
			rset.next();
			lblGamesLostVal.setText(rset.getString(1));

			// Blackjack games
			rset = dbController.specificQuery(
					"SELECT COUNT(*) FROM game_sessions WHERE game_type = 'Blackjack' AND user_profile = "
							+ user.getId() + ";");
			rset.next();
			lblBlackjackGamesVal.setText(rset.getString(1));

			// Slot Machine games
			rset = dbController.specificQuery(
					"SELECT COUNT(*) FROM game_sessions WHERE game_type = 'SlotMachine' AND user_profile = "
							+ user.getId() + ";");
			rset.next();
			lblSlotGamesVal.setText(rset.getString(1));

			// Money won (sum of positive bet_result)
			rset = dbController
					.specificQuery("SELECT SUM(bet_result) FROM game_sessions WHERE bet_result > 0 AND user_profile = "
							+ user.getId() + ";");
			rset.next();
			double moneyWon;
			if (rset.wasNull()) {
				moneyWon = 0.0;
			} else {
				moneyWon = rset.getDouble(1);
			}
			lblMoneyWonVal.setText(String.format("%.2f$", moneyWon));

			// Money lost (sum of negative bet_result)
			rset = dbController
					.specificQuery("SELECT SUM(bet_result) FROM game_sessions WHERE bet_result < 0 AND user_profile = "
							+ user.getId() + ";");
			rset.next();
			double moneyLost;
			if (rset.wasNull()) {
				moneyLost = 0.0;
				
			} else {
				moneyLost = rset.getDouble(1);
			}
			
			lblMoneyLostVal.setText(String.format("%.2f$", moneyLost));

			// Client with highest balance
			rset = dbController.specificQuery("SELECT customer_name, balance FROM customers WHERE user_profile = "
					+ user.getId() + " ORDER BY balance DESC LIMIT 1;");
			if (rset.next()) {
				lblClientBalanceVal.setText(String.format("%s (%.2f$)", rset.getString(1), rset.getDouble(2)));
			} else {
				lblClientBalanceVal.setText("No record");
			}

			// Game with highest money pool
			rset = dbController.specificQuery("SELECT id, money_pool FROM games WHERE user_profile = " + user.getId()
					+ " ORDER BY money_pool DESC LIMIT 1;");
			if (rset.next()) {
				lblGameMoneyVal.setText(String.format("Game %d (%.2f$)", rset.getInt(1), rset.getDouble(2)));
			} else {
				lblGameMoneyVal.setText("No record");
			}

			// Last game played
			rset = dbController.specificQuery("SELECT session_date FROM game_sessions WHERE user_profile = " + user.getId()
					+ " ORDER BY session_date DESC LIMIT 1;");
			if (rset.next()) {
				lblLastGameVal.setText(rset.getString(1));
			} else {
				lblLastGameVal.setText("No record");
			}

			rset.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setUser(User user) {
		this.user = user;
	}
}
