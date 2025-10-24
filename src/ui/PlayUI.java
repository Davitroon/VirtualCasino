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

import controller.DataBaseController;
import controller.MainController;
import controller.ViewController;
import exceptions.BetException;
import exceptions.GameException;
import model.Blackjack;
import model.Client;
import model.Game;
import model.Slotmachine;

/**
 * Window for starting games.
 * 
 * @author Davitroon
 * @since 3.0
 */
public class PlayUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableClients;
	private JTable tableGames;

	private DefaultTableModel modelClients;
	private DefaultTableModel modelGames;

	private MainController controller;
	private JButton btnPlay;
	
	private ViewController viewController;
	private DataBaseController dbController;

	/**
	 * Creates the frame.
	 * 
	 * @param menu       The main menu window
	 * @param model      The data model
	 * @param controller The controller for handling events
	 * @since 3.0
	 */
	public PlayUI(MainController controller) {

		this.controller = controller;
		viewController = controller.getViewController();
		dbController = controller.getDataBaseController();

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
				viewController.switchWindow(PlayUI.this, viewController.getHomeUI());
				btnPlay.setEnabled(false);
			}
		});

		// When closing the window using the X
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				viewController.switchWindow(PlayUI.this, viewController.getHomeUI());
				btnPlay.setEnabled(false);
			}
		});

		// Click the play button
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Create both client and game from the selected ones in the tables
				Client client = new Client((int) tableClients.getValueAt(tableClients.getSelectedRow(), 0),
						(String) tableClients.getValueAt(tableClients.getSelectedRow(), 1),
						(double) tableClients.getValueAt(tableClients.getSelectedRow(), 3));

				int gameId = (int) tableGames.getValueAt(tableGames.getSelectedRow(), 0);
				String gameType = (String) tableGames.getValueAt(tableGames.getSelectedRow(), 1);
				double moneyPool = (double) tableGames.getValueAt(tableGames.getSelectedRow(), 3);
				Game game = null;
				
				if (gameType.equals("Blackjack")) {
					game = new Blackjack(gameId, moneyPool);

				} else if (gameType.equals("SlotMachine")) {
					game = new Slotmachine(gameId, moneyPool);
				}

				// Insert the bet
				try {
					double bet = controller.promptBet(client, game);

					if (bet != 0) {
						viewController.openGameWindow(PlayUI.this, game, client, bet);
						btnPlay.setEnabled(false);
					}

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

		ResultSet clientsResult = dbController.queryClients(true);
		ResultSet gamesResult = dbController.queryGames(true);
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
}