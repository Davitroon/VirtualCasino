package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import exceptions.BetException;
import exceptions.GameException;
import logic.Blackjack;
import logic.Client;
import logic.Controller;
import logic.Game;
import logic.Model;
import logic.Slotmachine;
import ui.PlayUI;
import ui.HomeUI;

/**
 * Window for starting games.
 * 
 * @author David
 * @since 3.0
 */
public class PlayUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableClients;
	private JTable tableGames;

	private DefaultTableModel modelClients;
	private DefaultTableModel modelGames;

	private Model model;
	private Controller controller;
	private JButton btnPlay;
	private HomeUI menu;

	/**
	 * Creates the frame.
	 * 
	 * @param menu       The main menu window
	 * @param model      The data model
	 * @param controller The controller for handling events
	 * @since 3.0
	 */
	public PlayUI(HomeUI menu, Model model, Controller controller) {

		this.menu = menu;
		this.model = model;
		this.controller = controller;

		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 716, 435);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblGame = new JLabel("Play", SwingConstants.CENTER);
		lblGame.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblGame.setBounds(28, 28, 652, 31);
		contentPane.add(lblGame);

		JLabel lblClients = new JLabel("Clients", SwingConstants.CENTER);
		lblClients.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblClients.setBounds(28, 82, 287, 31);
		contentPane.add(lblClients);

		JScrollPane scrollPaneClients = new JScrollPane();
		scrollPaneClients.setBounds(28, 124, 287, 166);
		contentPane.add(scrollPaneClients);

		modelClients = new DefaultTableModel(new Object[][] {}, new String[] { "Id", "Name", "Active", "Balance" }) {
			private static final long serialVersionUID = 4840318942523039213L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { Integer.class, String.class, String.class, Double.class };
			boolean[] columnEditables = new boolean[] { false, false, false, false };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		tableClients = new JTable();
		tableClients.setModel(modelClients);
		tableClients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneClients.setViewportView(tableClients);

		JLabel lblGames = new JLabel("Games", SwingConstants.CENTER);
		lblGames.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblGames.setBounds(357, 86, 323, 23);
		contentPane.add(lblGames);

		JScrollPane scrollPaneGames = new JScrollPane();
		scrollPaneGames.setBounds(357, 124, 323, 166);
		contentPane.add(scrollPaneGames);

		modelGames = new DefaultTableModel(new Object[][] {}, new String[] { "Id", "Type", "Active", "Money" }) {
			private static final long serialVersionUID = -6299895431999927772L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { Integer.class, String.class, String.class, Double.class };
			boolean[] columnEditables = new boolean[] { false, false, false, false };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		tableGames = new JTable();
		tableGames.setModel(modelGames);
		tableGames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneGames.setViewportView(tableGames);

		btnPlay = new JButton("Play");
		btnPlay.setBackground(new Color(128, 128, 255));
		btnPlay.setEnabled(false);
		btnPlay.setBounds(577, 327, 103, 31);
		contentPane.add(btnPlay);

		JButton btnBack = new JButton("Back");
		btnBack.setBackground(new Color(128, 128, 128));
		btnBack.setBounds(28, 327, 103, 31);
		contentPane.add(btnBack);

		// Click the back button
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.switchWindow(PlayUI.this, menu);
				btnPlay.setEnabled(false);
			}
		});

		// When closing the window using the X
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controller.switchWindow(PlayUI.this, menu);
				btnPlay.setEnabled(false);
			}
		});

		// Click the play button
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ResultSet clientResult = model.querySingleData("customers",
						Integer.parseInt(tableClients.getValueAt(tableClients.getSelectedRow(), 0).toString()));
				ResultSet gameResult = model.querySingleData("games",
						Integer.parseInt(tableGames.getValueAt(tableGames.getSelectedRow(), 0).toString()));

				Client client = null;
				Game game = null;

				try {
					client = new Client(clientResult.getInt("id"), clientResult.getString("customer_name"),
							clientResult.getDouble("balance"));

					if (gameResult.getString("game_type").equals("Blackjack")) {
						game = new Blackjack(gameResult.getInt("id"), gameResult.getDouble("money_pool"));

					} else if (gameResult.getString("game_type").equals("SlotMachine")) {
						game = new Slotmachine(gameResult.getInt("id"), gameResult.getDouble("money_pool"));
					}

					double bet = controller.promptBet(client, game);

					if (bet != 0) {
						controller.openGameWindow(game, PlayUI.this, client, bet);
						btnPlay.setEnabled(false);
					}

				} catch (SQLException ex) {
					ex.printStackTrace();

				} catch (BetException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// Click on a row in the clients table
		tableClients.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				checkTables();
			}
		});

		// Click on a row in the games table
		tableGames.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				checkTables();
			}
		});
	}

	/**
	 * Method to update the clients and games tables by querying the database
	 * through the model class.
	 * 
	 * @throws GameException
	 */
	public void updateTables() throws GameException {

		ResultSet clientsResult = model.queryTableData("customers", true);
		ResultSet gamesResult = model.queryTableData("games", true);
		boolean hasData = false;

		modelClients.setRowCount(0);
		modelGames.setRowCount(0);

		// Clients table
		try {
			hasData = clientsResult.next();
			if (hasData) {
				controller.fillClientTable(clientsResult, modelClients);
			} else {
				throw new GameException("You cannot play without having registered any client.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Games table
		try {
			hasData = gamesResult.next();
			if (hasData) {
				controller.fillGameTable(gamesResult, modelGames);
			} else {
				throw new GameException("You cannot play without having registered any game.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method that checks whether rows have been selected in both the clients and
	 * games tables.
	 */
	public void checkTables() {
		if (tableClients.getSelectedRow() == -1 || tableGames.getSelectedRow() == -1) {
			btnPlay.setEnabled(false);
			return;
		}

		btnPlay.setEnabled(true);
	}

	public HomeUI getMenu() {
		return menu;
	}
}