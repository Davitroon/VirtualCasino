package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.DataBaseController;
import controller.MainController;
import controller.ViewController;
import model.User;

/**
 * Window displaying game statistics for a specific user.
 * <p>
 * Shows information such as games played, games won/lost, money won/lost,
 * number of Blackjack and Slot Machine games, last game played, client with
 * highest balance, and game with most money.
 * </p>
 * <p>
 * Provides the ability to delete all statistics and return to the main menu.
 * </p>
 * 
 * @author Davitroon
 * @since 3.0
 */
public class StatsUI extends JPanel {

	private static final long serialVersionUID = 1L;

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
	 * Constructs the StatsUI window.
	 * <p>
	 * Initializes all labels, buttons, and layout for displaying user statistics.
	 * Adds event listeners for the Back and Delete Statistics buttons.
	 * </p>
	 * 
	 * @param controller The main controller handling application logic and window
	 *                   changes.
	 * @since 3.0
	 */
	public StatsUI(MainController controller) {
		dbController = controller.getDataBaseController();
		ViewController viewController = controller.getViewController();

		setBounds(100, 100, 802, 433);
		setLayout(null);

		JLabel lblStatistics = new JLabel("Statistics", SwingConstants.CENTER);
		lblStatistics.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblStatistics.setBounds(10, 40, 782, 39);
		add(lblStatistics);

		JButton btnBack = new JButton("Back");
		btnBack.setBackground(new Color(128, 128, 128));
		btnBack.setBounds(10, 386, 132, 36);
		add(btnBack);

		JLabel lblGamesPlayed = new JLabel("Games played:");
		lblGamesPlayed.setFont(new Font("SimSun", Font.BOLD, 14));
		lblGamesPlayed.setBounds(88, 154, 142, 24);
		add(lblGamesPlayed);

		JLabel lblGamesWon = new JLabel("Games won:");
		lblGamesWon.setFont(new Font("SimSun", Font.BOLD, 14));
		lblGamesWon.setBounds(88, 189, 142, 24);
		add(lblGamesWon);

		JLabel lblGamesLost = new JLabel("Games lost:");
		lblGamesLost.setFont(new Font("SimSun", Font.BOLD, 14));
		lblGamesLost.setBounds(88, 224, 142, 24);
		add(lblGamesLost);

		JLabel lblClientBalance = new JLabel("Client with highest balance:");
		lblClientBalance.setFont(new Font("SimSun", Font.BOLD, 14));
		lblClientBalance.setBounds(382, 224, 170, 24);
		add(lblClientBalance);

		JLabel lblGameMoney = new JLabel("Game with most money:");
		lblGameMoney.setFont(new Font("SimSun", Font.BOLD, 14));
		lblGameMoney.setBounds(382, 259, 162, 24);
		add(lblGameMoney);

		JLabel lblBlackjackGames = new JLabel("Blackjack games:");
		lblBlackjackGames.setFont(new Font("SimSun", Font.BOLD, 14));
		lblBlackjackGames.setBounds(88, 259, 178, 24);
		add(lblBlackjackGames);

		JLabel lblSlotGames = new JLabel("Slot machine games:");
		lblSlotGames.setFont(new Font("SimSun", Font.BOLD, 14));
		lblSlotGames.setBounds(88, 294, 193, 24);
		add(lblSlotGames);

		JLabel lblMoneyLost = new JLabel("Money lost:");
		lblMoneyLost.setFont(new Font("SimSun", Font.BOLD, 14));
		lblMoneyLost.setBounds(382, 189, 120, 24);
		add(lblMoneyLost);

		JLabel lblMoneyWon = new JLabel("Money won:");
		lblMoneyWon.setFont(new Font("SimSun", Font.BOLD, 14));
		lblMoneyWon.setBounds(382, 154, 120, 24);
		add(lblMoneyWon);

		JLabel lblLastGame = new JLabel("Last game played:");
		lblLastGame.setFont(new Font("SimSun", Font.BOLD, 14));
		lblLastGame.setBounds(382, 294, 178, 24);
		add(lblLastGame);

		lblGamesPlayedVal = new JLabel("lorem");
		lblGamesPlayedVal.setFont(new Font("SimSun", Font.PLAIN, 14));
		lblGamesPlayedVal.setBounds(223, 159, 141, 14);
		add(lblGamesPlayedVal);

		lblGamesWonVal = new JLabel("lorem");
		lblGamesWonVal.setFont(new Font("SimSun", Font.PLAIN, 14));
		lblGamesWonVal.setBounds(223, 195, 149, 14);
		add(lblGamesWonVal);

		lblGamesLostVal = new JLabel("lorem");
		lblGamesLostVal.setFont(new Font("SimSun", Font.PLAIN, 14));
		lblGamesLostVal.setBounds(232, 229, 140, 14);
		add(lblGamesLostVal);

		lblBlackjackGamesVal = new JLabel("lorem");
		lblBlackjackGamesVal.setFont(new Font("SimSun", Font.PLAIN, 14));
		lblBlackjackGamesVal.setBounds(265, 265, 108, 14);
		add(lblBlackjackGamesVal);

		lblSlotGamesVal = new JLabel("lorem");
		lblSlotGamesVal.setFont(new Font("SimSun", Font.PLAIN, 14));
		lblSlotGamesVal.setBounds(279, 300, 93, 14);
		add(lblSlotGamesVal);

		lblMoneyWonVal = new JLabel("lorem");
		lblMoneyWonVal.setFont(new Font("SimSun", Font.PLAIN, 14));
		lblMoneyWonVal.setBounds(501, 159, 225, 14);
		add(lblMoneyWonVal);

		lblMoneyLostVal = new JLabel("lorem");
		lblMoneyLostVal.setFont(new Font("SimSun", Font.PLAIN, 14));
		lblMoneyLostVal.setBounds(507, 195, 219, 14);
		add(lblMoneyLostVal);

		lblClientBalanceVal = new JLabel("lorem");
		lblClientBalanceVal.setFont(new Font("SimSun", Font.PLAIN, 14));
		lblClientBalanceVal.setBounds(556, 229, 170, 14);
		add(lblClientBalanceVal);

		lblGameMoneyVal = new JLabel("lorem");
		lblGameMoneyVal.setFont(new Font("SimSun", Font.PLAIN, 14));
		lblGameMoneyVal.setBounds(548, 264, 178, 14);
		add(lblGameMoneyVal);

		lblLastGameVal = new JLabel("lorem");
		lblLastGameVal.setFont(new Font("SimSun", Font.PLAIN, 14));
		lblLastGameVal.setBounds(558, 299, 168, 14);
		add(lblLastGameVal);

		btnDeleteStatistics = new JButton("Delete statistics");
		btnDeleteStatistics.setForeground(new Color(0, 0, 0));
		btnDeleteStatistics.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnDeleteStatistics.setBackground(new Color(242, 77, 77));
		btnDeleteStatistics.setBounds(660, 385, 132, 36);
		add(btnDeleteStatistics);

		lblUser = new JLabel("Statistics for ");
		lblUser.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUser.setBounds(88, 127, 178, 14);
		add(lblUser);

		// Click "Back" button
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewController.switchPanel(viewController.getHomeUI());
			}
		});

		// Click "Delete statistics" button
		btnDeleteStatistics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete all statistics?",
						"Confirm deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

				if (option == JOptionPane.YES_OPTION) {
					dbController.deleteGameSessions();
					updateData();
				}
			}
		});
	}

	/**
	 * Updates all statistics displayed in the window.
	 * <p>
	 * Queries the database to refresh values such as games played, money won/lost,
	 * client balances, last game played, and other relevant information. Handles
	 * cases where data might be missing.
	 * </p>
	 * 
	 * @since 3.0
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
			rset = dbController
					.specificQuery("SELECT COUNT(*) FROM game_sessions WHERE user_profile = " + user.getId() + ";");
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
			rset = dbController.specificQuery("SELECT client_name, balance FROM clients WHERE user_profile = "
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
			rset = dbController.specificQuery("SELECT session_date FROM game_sessions WHERE user_profile = "
					+ user.getId() + " ORDER BY session_date DESC LIMIT 1;");
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

	/**
	 * Updates the current user for which statistics are displayed.
	 * <p>
	 * This method should be called whenever the active user changes.
	 * </p>
	 * 
	 * @param controller The main controller to retrieve the current user from.
	 * @since 3.3
	 */
	public void updateUser(MainController controller) {
		user = controller.getCurrentUser();
	}
}
