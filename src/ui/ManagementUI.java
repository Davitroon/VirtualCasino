package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import controller.Validator;
import dao.DBManagement;
import ui.ClientUI;
import ui.ClientEditUI;
import ui.GameUI;
import ui.GameUpdateUI;
import ui.ManagementUI;
import ui.HomeUI;

/**
 * Window for managing users and games.
 * 
 * @author David
 * @since 3.0
 */
public class ManagementUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableClients;
	private JTable tableGames;
	private DefaultTableModel modelClients;
	private DefaultTableModel modelGames;

	private ClientUI clientForm;
	private ClientEditUI clientEditForm;
	private GameUI gameForm;
	private GameUpdateUI gameEditForm;
	private DBManagement model;
	private Controller controller;

	private JButton btnEditClient;
	private JButton btnDeleteClient;
	private JButton btnEditGame;
	private JButton btnDeleteGame;
	private JTabbedPane tabbedPane;
	private JButton btnDeleteClients;
	private JButton btnDeleteGames;

	/**
	 * Creates the frame.
	 * 
	 * @param mainMenu   The main menu window
	 * @param model      The data model of the application
	 * @param controller The controller handling UI actions
	 * @since 3.0
	 */
	public ManagementUI(HomeUI mainMenu, DBManagement model, Controller controller, Validator validator) {
		setResizable(false);

		this.model = model;
		this.controller = controller;

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 802, 399);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 788, 362);
		contentPane.add(tabbedPane);

		JPanel panelUsers = new JPanel();
		tabbedPane.addTab("Users", null, panelUsers, "User management");
		panelUsers.setLayout(null);

		JLabel lblMyClients = new JLabel("My Clients", SwingConstants.CENTER);
		lblMyClients.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblMyClients.setBounds(6, 26, 248, 36);
		panelUsers.add(lblMyClients);

		JScrollPane scrollPaneUsers = new JScrollPane();
		scrollPaneUsers.setBounds(256, 66, 491, 241);
		panelUsers.add(scrollPaneUsers);

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
		btnAddClient.setBounds(60, 94, 132, 36);
		panelUsers.add(btnAddClient);

		btnEditClient = new JButton("Edit Client");
		btnEditClient.setBackground(new Color(128, 128, 255));
		btnEditClient.setEnabled(false);
		btnEditClient.setBounds(60, 141, 132, 36);
		panelUsers.add(btnEditClient);

		btnDeleteClient = new JButton("Delete Client");
		btnDeleteClient.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnDeleteClient.setBackground(new Color(242, 77, 77));
		btnDeleteClient.setEnabled(false);
		btnDeleteClient.setBounds(60, 188, 132, 36);
		panelUsers.add(btnDeleteClient);

		JButton btnBackClient = new JButton("Back");
		btnBackClient.setBackground(new Color(128, 128, 128));
		btnBackClient.setBounds(60, 235, 132, 36);
		panelUsers.add(btnBackClient);

		btnDeleteClients = new JButton("Delete Clients");
		btnDeleteClients.setEnabled(false);
		btnDeleteClients.setForeground(Color.BLACK);
		btnDeleteClients.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnDeleteClients.setBackground(new Color(242, 77, 77));
		btnDeleteClients.setBounds(605, 26, 142, 32);
		panelUsers.add(btnDeleteClients);

		JPanel panelGames = new JPanel();
		panelGames.setLayout(null);
		tabbedPane.addTab("Games", null, panelGames, "Game management");

		JLabel lblMyGames = new JLabel("My Games", SwingConstants.CENTER);
		lblMyGames.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblMyGames.setBounds(6, 27, 248, 36);
		panelGames.add(lblMyGames);

		JScrollPane scrollPaneGames = new JScrollPane();
		scrollPaneGames.setBounds(256, 66, 491, 241);
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
		btnAddGame.setBounds(60, 94, 132, 36);
		panelGames.add(btnAddGame);

		btnEditGame = new JButton("Edit Game");
		btnEditGame.setBackground(new Color(128, 128, 255));
		btnEditGame.setEnabled(false);
		btnEditGame.setBounds(60, 141, 132, 36);
		panelGames.add(btnEditGame);

		btnDeleteGame = new JButton("Delete Game");
		btnDeleteGame.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnDeleteGame.setBackground(new Color(242, 77, 77));
		btnDeleteGame.setEnabled(false);
		btnDeleteGame.setBounds(60, 188, 132, 36);
		panelGames.add(btnDeleteGame);

		JButton btnBackGame = new JButton("Back");
		btnBackGame.setBackground(new Color(128, 128, 128));
		btnBackGame.setBounds(60, 235, 132, 36);
		panelGames.add(btnBackGame);

		btnDeleteGames = new JButton("Delete Games");
		btnDeleteGames.setForeground(Color.BLACK);
		btnDeleteGames.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnDeleteGames.setEnabled(false);
		btnDeleteGames.setBackground(new Color(242, 77, 77));
		btnDeleteGames.setBounds(604, 27, 142, 32);
		panelGames.add(btnDeleteGames);

		addWindowListener(new WindowAdapter() {
			// When closing the window using the X button
			@Override
			public void windowClosing(WindowEvent e) {
				resetButtons();
				controller.switchWindow(ManagementUI.this, mainMenu);
				tabbedPane.setSelectedIndex(0);
			}
		});

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
				if (clientForm == null) {
					clientForm = new ClientUI(ManagementUI.this, controller, model, validator);
				}

				controller.switchWindow(ManagementUI.this, clientForm);
				resetButtons();
			}
		});

		// Click edit client button
		btnEditClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (clientEditForm == null) {
					clientEditForm = new ClientEditUI(ManagementUI.this, controller, model, validator);
				}

				int id = Integer.parseInt(tableClients.getValueAt(tableClients.getSelectedRow(), 0).toString());
				clientEditForm.loadOriginalClient(id);
				controller.switchWindow(ManagementUI.this, clientEditForm);
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
				model.deleteData(id, "customers");

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
					model.deleteTableData("customers");
					btnDeleteClients.setEnabled(false);
					updateClientsTable();
				}
			}
		});

		// Click back button (clients)
		btnBackClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetButtons();
				controller.switchWindow(ManagementUI.this, mainMenu);
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
				if (gameForm == null) {
					gameForm = new GameUI(ManagementUI.this, controller, model, validator);
				}

				controller.switchWindow(ManagementUI.this, gameForm);
				resetButtons();
			}
		});

		// Click edit game button
		btnEditGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (gameEditForm == null) {
					gameEditForm = new GameUpdateUI(ManagementUI.this, controller, model, validator);
				}

				int id = Integer.parseInt(tableGames.getValueAt(tableGames.getSelectedRow(), 0).toString());
				gameEditForm.loadOriginalGame(id);
				controller.switchWindow(ManagementUI.this, gameEditForm);
				resetButtons();
			}
		});

		// Click delete game button
		btnDeleteGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tableGames.getSelectedRow();
				if (row == -1)
					return;

				int id = (int) tableGames.getValueAt(row, 0);
				model.deleteData(id, "games");

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
					model.deleteTableData("games");
					updateGamesTable();
				}
			}
		});

		// Click back button (Games)
		btnBackGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetButtons();
				controller.switchWindow(ManagementUI.this, mainMenu);
				tabbedPane.setSelectedIndex(0);
			}
		});
	}

	/**
	 * Method that queries the clients table in the database to update the visual
	 * table.
	 */
	public void updateClientsTable() {
		modelClients.setRowCount(0);

		ResultSet rset = model.queryTableData("customers", false);

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
	 * Method that queries the games table in the database to update the visual
	 * table.
	 */
	public void updateGamesTable() {
		modelGames.setRowCount(0);
		ResultSet rset = model.queryTableData("games", false);

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
	 * Method that resets the buttons to their original state.
	 */
	public void resetButtons() {
		btnEditClient.setEnabled(false);
		btnDeleteClient.setEnabled(false);
		btnEditGame.setEnabled(false);
		btnDeleteGame.setEnabled(false);
	}

}