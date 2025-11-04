package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import controller.DatabaseController;
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

	private DatabaseController dbController;
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
		setBackground(new Color(220, 220, 220));

		JLabel lblTitle = new JLabel("Statistics", SwingConstants.CENTER);
		lblTitle.setFont(new Font("Segoe UI Black", Font.BOLD, 36));
		lblTitle.setBounds(138, 21, 525, 50);
		add(lblTitle);

		lblUser = new JLabel("Statistics for: <user>", SwingConstants.CENTER);
		lblUser.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblUser.setForeground(Color.DARK_GRAY);
		lblUser.setBounds(10, 70, 782, 20);
		add(lblUser);

		// ==== Panel 1: Results ====
		JPanel panelResults = createCategoryPanel("Results", 40, 110, 320, 120);
		add(panelResults);

		addLabel(panelResults, "Games played:", 20, 35);
		lblGamesPlayedVal = addValue(panelResults, "—", 180, 35);

		addLabel(panelResults, "Games won:", 20, 60);
		lblGamesWonVal = addValue(panelResults, "—", 180, 60);

		addLabel(panelResults, "Games lost:", 20, 85);
		lblGamesLostVal = addValue(panelResults, "—", 180, 85);

		// ==== Panel 2: Money ====
		JPanel panelMoney = createCategoryPanel("Money", 420, 110, 320, 120);
		add(panelMoney);

		addLabel(panelMoney, "Money won:", 20, 35);
		lblMoneyWonVal = addValue(panelMoney, "—", 180, 35);

		addLabel(panelMoney, "Money lost:", 20, 60);
		lblMoneyLostVal = addValue(panelMoney, "—", 180, 60);

		// ==== Panel 3: Games ====
		JPanel panelGames = createCategoryPanel("Games", 40, 250, 320, 120);
		add(panelGames);

		addLabel(panelGames, "Blackjack games:", 20, 35);
		lblBlackjackGamesVal = addValue(panelGames, "—", 180, 35);

		addLabel(panelGames, "Slot games:", 20, 60);
		lblSlotGamesVal = addValue(panelGames, "—", 180, 60);

		addLabel(panelGames, "Last game played:", 20, 85);
		lblLastGameVal = addValue(panelGames, "—", 180, 85);

		// ==== Panel 4: Other ====
		JPanel panelOther = createCategoryPanel("Other", 420, 250, 320, 120);
		add(panelOther);

		addLabel(panelOther, "Client with most money:", 20, 35);
		lblClientBalanceVal = addValue(panelOther, "—", 180, 35);

		addLabel(panelOther, "Game with most money:", 20, 60);
		lblGameMoneyVal = addValue(panelOther, "—", 180, 60);

		JButton btnBack = new JButton("Back");
		btnBack.setForeground(Color.WHITE);
		btnBack.setFocusPainted(false);
		btnBack.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnBack.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		btnBack.setBackground(new Color(128, 128, 128));
		btnBack.setBounds(10, 386, 132, 36);
		add(btnBack);

		btnDeleteStatistics = new JButton("Delete statistics");
		btnDeleteStatistics.setForeground(Color.WHITE);
		btnDeleteStatistics.setFocusPainted(false);
		btnDeleteStatistics.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnDeleteStatistics.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnDeleteStatistics.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		btnDeleteStatistics.setBackground(new Color(242, 77, 77));
		btnDeleteStatistics.setBounds(660, 386, 132, 36);
		add(btnDeleteStatistics);

		// Back button
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewController.switchPanel(viewController.getHomeUI());
			}
		});

		// Delete button
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
	 * Creates a category panel with a border and a title.
	 *
	 * @param title  The title text displayed at the top of the panel.
	 * @param x      The X coordinate of the panel.
	 * @param y      The Y coordinate of the panel.
	 * @param width  The width of the panel.
	 * @param height The height of the panel.
	 * @return A JPanel instance with the specified layout, border, and title label.
	 * @since 3.3
	 */
	private JPanel createCategoryPanel(String title, int x, int y, int width, int height) {
		JPanel panel = new JPanel(null);
		panel.setBackground(new Color(245, 245, 245)); // un poco más claro
		panel.setBorder(new LineBorder(Color.GRAY, 1));
		panel.setBounds(x, y, width, height);

		JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
		lblTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblTitle.setForeground(Color.BLACK);
		lblTitle.setBounds(0, 5, width, 20);
		panel.add(lblTitle);

		return panel;
	}

	/**
	 * Adds a standard text label to the specified panel.
	 *
	 * @param panel The parent panel to which the label will be added.
	 * @param text  The text content of the label.
	 * @param x     The X coordinate for label placement.
	 * @param y     The Y coordinate for label placement.
	 * @return The created JLabel component.
	 * @since 3.3
	 */
	private JLabel addLabel(JPanel panel, String text, int x, int y) {
		JLabel label = new JLabel(text);
		label.setFont(new Font("SansSerif", Font.PLAIN, 13));
		label.setForeground(Color.BLACK);
		label.setBounds(x, y, 150, 20);
		panel.add(label);
		return label;
	}

	/**
	 * Adds a value label to the specified panel.
	 *
	 * @param panel The parent panel to which the value label will be added.
	 * @param text  The text content of the value label.
	 * @param x     The X coordinate for label placement.
	 * @param y     The Y coordinate for label placement.
	 * @return The created JLabel component representing the value.
	 * @since 3.3
	 */
	private JLabel addValue(JPanel panel, String text, int x, int y) {
		JLabel value = new JLabel(text);
		value.setFont(new Font("SansSerif", Font.BOLD, 13));
		value.setForeground(Color.BLACK);
		value.setBounds(x, y, 130, 20);
		panel.add(value);
		return value;
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
