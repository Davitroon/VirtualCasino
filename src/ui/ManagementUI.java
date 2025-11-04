package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import controller.DatabaseController;
import controller.MainController;
import controller.ViewController;

/**
 * Window for managing users (clients) and games.
 * <p>
 * Provides tabs for Users and Games. Each tab displays a table with current
 * data and allows adding, editing, and deleting entries. Buttons are
 * dynamically enabled depending on table selection.
 * </p>
 * 
 * @author Davitroon
 * @since 3.0
 */
public class ManagementUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable tableClients;
	private JTable tableGames;
	private DefaultTableModel modelClients;
	private DefaultTableModel modelGames;

	private DatabaseController dbController;
	private MainController controller;

	private JButton btnEditClient;
	private JButton btnDeleteClient;
	private JButton btnEditGame;
	private JButton btnDeleteGame;
	private JTabbedPane tabbedPane;
	private JButton btnDeleteClients;
	private JButton btnDeleteGames;

	/**
	 * Constructs the management window frame.
	 * <p>
	 * Initializes all UI components, tables, and buttons for managing clients and
	 * games. Adds event listeners for table row selection, add/edit/delete actions,
	 * and window events.
	 * </p>
	 * 
	 * @param controller the main controller handling UI actions and database
	 *                   operations
	 * @since 3.0
	 */
	public ManagementUI(MainController controller) {

		this.controller = controller;
		dbController = controller.getDataBaseController();
		ViewController viewController = controller.getViewController();
		setBounds(100, 100, 802, 433);
		setLayout(null);
		setBackground(new Color(220, 220, 220));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 67, 782, 312);
		add(tabbedPane);
		
		JLabel lblManagement = new JLabel("Management");
		lblManagement.setFont(new Font("Segoe UI Black", Font.BOLD, 36));
		lblManagement.setBounds(138, 21, 525, 50);
		lblManagement.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblManagement);

		JPanel panelClients = new JPanel();
		panelClients.setBackground(new Color(220, 220, 220));
		tabbedPane.addTab("Clients", null, panelClients, "User management");
		panelClients.setLayout(null);

		JLabel lblMyClients = new JLabel("My Clients", SwingConstants.CENTER);
		lblMyClients.setFont(new Font("Segoe UI Black", Font.BOLD, 24));
		lblMyClients.setBounds(10, 27, 235, 36);
		panelClients.add(lblMyClients);

		JScrollPane scrollPaneUsers = new JScrollPane();
		scrollPaneUsers.setBounds(239, 66, 508, 188);
		scrollPaneUsers.getViewport().setBackground(Color.WHITE);
		panelClients.add(scrollPaneUsers);

		modelClients = new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Name", "Age", "Gender", "Active", "Balance" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1602146996076819230L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { Integer.class, String.class, Integer.class, Character.class,
					String.class, Double.class };
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false // all columns are NOT
																									// editable
			};

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		tableClients = new JTable();
		tableClients.setModel(modelClients);
		tableClients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneUsers.setViewportView(tableClients);

		JButton btnAddClient = new JButton("Add Client");
		btnAddClient.setBackground(new Color(128, 128, 255));
		btnAddClient.setBounds(59, 74, 132, 36);
		btnAddClient.setForeground(Color.WHITE);
		btnAddClient.setFocusPainted(false);
		btnAddClient.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnAddClient.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAddClient.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		panelClients.add(btnAddClient);

		btnEditClient = new JButton("Edit Client");
		btnEditClient.setBackground(new Color(128, 128, 255));
		btnEditClient.setEnabled(false);
		btnEditClient.setBounds(59, 121, 132, 36);
		btnEditClient.setFocusPainted(false);
		btnEditClient.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnEditClient.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnEditClient.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		btnEditClient.setForeground(Color.WHITE);
		panelClients.add(btnEditClient);

		btnDeleteClient = new JButton("Delete Client");
		btnDeleteClient.setBackground(new Color(242, 77, 77));
		btnDeleteClient.setEnabled(false);
		btnDeleteClient.setBounds(59, 168, 132, 36);
		btnDeleteClient.setForeground(Color.WHITE);
		btnDeleteClient.setFocusPainted(false);
		btnDeleteClient.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnDeleteClient.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnDeleteClient.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		panelClients.add(btnDeleteClient);

		btnDeleteClients = new JButton("Delete Clients");
		btnDeleteClients.setEnabled(false);
		btnDeleteClients.setBackground(new Color(242, 77, 77));
		btnDeleteClients.setBounds(605, 27, 142, 32);
		btnDeleteClients.setForeground(Color.WHITE);
		btnDeleteClients.setFocusPainted(false);
		btnDeleteClients.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnDeleteClients.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnDeleteClients.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		panelClients.add(btnDeleteClients);

		JPanel panelGames = new JPanel();
		panelGames.setLayout(null);
		panelGames.setBackground(new Color(220, 220, 220));
		tabbedPane.addTab("Games", null, panelGames, "Game management");

		JLabel lblMyGames = new JLabel("My Games", SwingConstants.CENTER);
		lblMyGames.setFont(new Font("Segoe UI Black", Font.BOLD, 24));
		lblMyGames.setBounds(10, 27, 235, 36);
		panelGames.add(lblMyGames);

		JScrollPane scrollPaneGames = new JScrollPane();
		scrollPaneGames.setBounds(239, 66, 508, 188);
		scrollPaneGames.getViewport().setBackground(Color.WHITE);
		panelGames.add(scrollPaneGames);

		modelGames = new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Type", "Active", "Money" }) {
			private static final long serialVersionUID = -1612780249715316697L;
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

		JButton btnAddGame = new JButton("Add Game");
		btnAddGame.setBackground(new Color(128, 128, 255));
		btnAddGame.setBounds(59, 74, 132, 36);
		btnAddGame.setForeground(Color.WHITE);
		btnAddGame.setFocusPainted(false);
		btnAddGame.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnAddGame.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAddGame.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		panelGames.add(btnAddGame);

		btnEditGame = new JButton("Edit Game");
		btnEditGame.setBackground(new Color(128, 128, 255));
		btnEditGame.setEnabled(false);
		btnEditGame.setBounds(59, 121, 132, 36);
		btnEditGame.setFocusPainted(false);
		btnEditGame.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnEditGame.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnEditGame.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		btnEditGame.setForeground(Color.WHITE);
		panelGames.add(btnEditGame);

		btnDeleteGame = new JButton("Delete Game");
		btnDeleteGame.setBackground(new Color(242, 77, 77));
		btnDeleteGame.setEnabled(false);
		btnDeleteGame.setBounds(59, 168, 132, 36);
		btnDeleteGame.setForeground(Color.WHITE);
		btnDeleteGame.setFocusPainted(false);
		btnDeleteGame.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnDeleteGame.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnDeleteGame.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		panelGames.add(btnDeleteGame);

		btnDeleteGames = new JButton("Delete Games");
		btnDeleteGames.setEnabled(false);
		btnDeleteGames.setBackground(new Color(242, 77, 77));
		btnDeleteGames.setBounds(605, 27, 142, 32);
		btnDeleteGames.setForeground(Color.WHITE);
		btnDeleteGames.setFocusPainted(false);
		btnDeleteGames.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnDeleteGames.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnDeleteGames.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		panelGames.add(btnDeleteGames);

		JButton btnBack = new JButton("Back");
		btnBack.setForeground(Color.WHITE);
		btnBack.setFocusPainted(false);
		btnBack.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnBack.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		btnBack.setBackground(new Color(128, 128, 128));
		btnBack.setBounds(10, 386, 132, 36);
		add(btnBack);

		// When the window is loaded
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				updateClientsTable();
				updateGamesTable();
			}
		});

		// Click on a row in the clients table
		tableClients.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tableClients.getSelectedRow();

				if (row == -1) {
					resetButtons();
					return;
				}

				btnEditClient.setEnabled(true);
				btnDeleteClient.setEnabled(true);
			}
		});

		// Click add client button
		btnAddClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewController.switchPanel(viewController.getClientUI());
				resetButtons();
			}
		});

		// Click edit client button
		btnEditClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id = Integer.parseInt(tableClients.getValueAt(tableClients.getSelectedRow(), 0).toString());

				viewController.getClientEditUI().loadOriginalClient(id);
				viewController.switchPanel(viewController.getClientEditUI());
				resetButtons();
			}
		});

		// Click delete client button
		btnDeleteClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tableClients.getSelectedRow();
				if (row == -1)
					return;

				int id = (int) tableClients.getValueAt(row, 0);
				dbController.deleteClient(id);

				updateClientsTable();
				resetButtons();
			}
		});

		// Click delete all clients button
		btnDeleteClients.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete all clients?",
						"Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

				if (option == JOptionPane.YES_OPTION) {
					dbController.deleteClients();
					btnDeleteClients.setEnabled(false);
					updateClientsTable();
				}
			}
		});

		// Click back button (clients)
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetButtons();
				viewController.switchPanel(viewController.getHomeUI());
				tabbedPane.setSelectedIndex(0);
			}
		});

		// Click on a row in the games table
		tableGames.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tableGames.getSelectedRow();

				if (row == -1) {
					btnEditGame.setEnabled(false);
					btnDeleteGame.setEnabled(false);
					return;
				}

				btnEditGame.setEnabled(true);
				btnDeleteGame.setEnabled(true);
			}
		});

		// Click add game button
		btnAddGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewController.switchPanel(viewController.getGameUI());
				resetButtons();
			}
		});

		// Click edit game button
		btnEditGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id = Integer.parseInt(tableGames.getValueAt(tableGames.getSelectedRow(), 0).toString());
				viewController.getGameEditUI().loadOriginalGame(id);

				viewController.switchPanel(viewController.getGameEditUI());
				resetButtons();
			}
		});

		// Click delete game button
		btnDeleteGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tableGames.getSelectedRow();
				if (row == -1)
					return;

				int gameId = (int) tableGames.getValueAt(row, 0);
				dbController.deleteGame(gameId);

				updateGamesTable();
				resetButtons();
			}
		});

		// Click delete all games button
		btnDeleteGames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete all games?",
						"Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

				if (option == JOptionPane.YES_OPTION) {
					dbController.deleteGames();
					updateGamesTable();
				}
			}
		});
		
		// Change tab
		tabbedPane.addChangeListener(e -> {
		    resetButtons(); // Deshabilita botones y limpia selección
		});
	}

	/**
	 * Updates the clients table with current data from the database.
	 * <p>
	 * Queries the database for clients and populates the table model. Enables or
	 * disables the delete all clients button based on data presence.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public void updateClientsTable() {
		modelClients.setRowCount(0);
		ResultSet rset = dbController.queryClients(false);

		try {
			boolean hasData = rset.next();
			if (!hasData) {
				resetButtons();
				btnDeleteClients.setEnabled(false);

			} else {
				controller.fillFullClientTable(rset, modelClients);
				btnDeleteClients.setEnabled(true);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates the games table with current data from the database.-
	 * <p>
	 * Queries the database for games and populates the table model. Enables or
	 * disables the delete all games button based on data presence.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public void updateGamesTable() {
		modelGames.setRowCount(0);
		ResultSet rset = dbController.queryGames(false);

		try {
			boolean hasData = rset.next();
			if (!hasData) {
				resetButtons();
				btnDeleteGames.setEnabled(false);

			} else {
				controller.fillGameTable(rset, modelGames);
				btnDeleteGames.setEnabled(true);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Resets all edit and delete buttons to their default disabled state.
	 * <p>
	 * Called when switching tabs, after deletion, or after table refresh to prevent
	 * invalid operations.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public void resetButtons() {
		btnEditClient.setEnabled(false);
		btnDeleteClient.setEnabled(false);
		btnEditGame.setEnabled(false);
		btnDeleteGame.setEnabled(false);
		tableClients.clearSelection();
		tableGames.clearSelection();
	}

}