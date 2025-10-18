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
 * Ventana para comenzar los juegos.
 * @author David
 * @since 3.0
 */
public class PlayUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableClientes;
	private JTable tableJuegos;
	
	private DefaultTableModel modeloClientes;
	private DefaultTableModel modeloJuegos;
	
	private Model modelo;
	private Controller controlador;
	private JButton btnJugar;
	private HomeUI menu;
	

	/**
	 * Create the frame.
	 * @param menu
	 * @param modelo
	 * @param controlador
	 */
	public PlayUI(HomeUI menu, Model modelo, Controller controlador) {
		
		this.menu = menu;
		this.modelo = modelo;
		this.controlador = controlador;	
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 716, 435);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblJuego = new JLabel("Jugar", SwingConstants.CENTER);
		lblJuego.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblJuego.setBounds(28, 28, 652, 31);
		contentPane.add(lblJuego);
		
		JLabel lblClientes = new JLabel("Clientes", SwingConstants.CENTER);
		lblClientes.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblClientes.setBounds(28, 82, 287, 31);
		contentPane.add(lblClientes);
		
		JScrollPane scrollPaneCliente = new JScrollPane();
		scrollPaneCliente.setBounds(28, 124, 287, 166);
		contentPane.add(scrollPaneCliente);
		
		modeloClientes = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Id", "Nombre", "Activo", "Saldo"
				}
			) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 4840318942523039213L;
				@SuppressWarnings("rawtypes")
				Class[] columnTypes = new Class[] {
					Integer.class, String.class, String.class, Double.class
				};
				boolean[] columnEditables = new boolean[] {
					false, false, false, false
				};
				public Class<?> getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
				
			};
		tableClientes = new JTable();
		tableClientes.setModel(modeloClientes);
		tableClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneCliente.setViewportView(tableClientes);
		
		JLabel lblJuegos = new JLabel("Juegos", SwingConstants.CENTER);
		lblJuegos.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblJuegos.setBounds(357, 86, 323, 23);
		contentPane.add(lblJuegos);
		
		JScrollPane scrollPaneJuegos = new JScrollPane();
		scrollPaneJuegos.setBounds(357, 124, 323, 166);
		contentPane.add(scrollPaneJuegos);
		
		modeloJuegos = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Id", "Tipo", "Activo", "Dinero"
				}
			) {
				/**
				 * 
				 */
				private static final long serialVersionUID = -6299895431999927772L;
				@SuppressWarnings("rawtypes")
				Class[] columnTypes = new Class[] {
					Integer.class, String.class, String.class, Double.class
				};
				boolean[] columnEditables = new boolean[] {
					false, false, false, false
				};
				public Class<?> getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			};
		tableJuegos = new JTable();
		tableJuegos.setModel(modeloJuegos);
		tableJuegos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneJuegos.setViewportView(tableJuegos);
		
		btnJugar = new JButton("Jugar");
		btnJugar.setBackground(new Color(128, 128, 255));
		btnJugar.setEnabled(false);
		btnJugar.setBounds(577, 327, 103, 31);
		contentPane.add(btnJugar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(128, 128, 128));
		btnVolver.setBounds(28, 327, 103, 31);
		contentPane.add(btnVolver);
		
		// Clic boton volver
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlador.cambiarVentana(PlayUI.this, menu);
				btnJugar.setEnabled(false);
			}
		});
		
		// Al cerrar la ventana mediante la X
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controlador.cambiarVentana(PlayUI.this, menu);
				btnJugar.setEnabled(false);
			}
		});
		
		
		// Clic boton jugar
		btnJugar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {

		        ResultSet rsetCliente = modelo.consultarDatoUnico("customers", Integer.parseInt(tableClientes.getValueAt(tableClientes.getSelectedRow(), 0).toString()));
		        ResultSet rsetJuego = modelo.consultarDatoUnico("games", Integer.parseInt(tableJuegos.getValueAt(tableJuegos.getSelectedRow(), 0).toString()));

		        Client cliente = null;
		        Game juego = null;

		        try {
		            cliente = new Client(rsetCliente.getInt("id"), rsetCliente.getString("customer_name"), rsetCliente.getDouble("balance"));

		            if (rsetJuego.getString("game_type").equals("Blackjack")) {
		                juego = new Blackjack(rsetJuego.getInt("id"), rsetJuego.getDouble("money_pool"));
		                
		            } else if (rsetJuego.getString("game_type").equals("SlotMachine")) {
		                juego = new Slotmachine(rsetJuego.getInt("id"), rsetJuego.getDouble("money_pool"));
		            }

		            double apuesta = controlador.alertaApuesta(cliente, juego);

		            if (apuesta != 0) {
		                controlador.abrirJuegoVentana(juego, PlayUI.this, cliente, apuesta);
		                btnJugar.setEnabled(false);
		            }

		        } catch (SQLException ex) {
		            ex.printStackTrace();
		            
		        } catch (BetException ex) {
		            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});
		
		
		// Clic fila de la tabla clientes
		tableClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {				
				comprobarTablas();
			}
		});
		
		
		// Clic fila de la tabla juegos
		tableJuegos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				comprobarTablas();
			}
		});
	}
	
	
	/**
	 * Método para actualizar las tablas de clientes y juegos, consultando a la BD mediante la clase modelo.
	 * @throws GameException 
	 */
	public void actualizarTablas() throws GameException {
		
		ResultSet rset1 = modelo.consultarDatos("customers", true);		
		ResultSet rset2 = modelo.consultarDatos("games", true);		
		boolean hayDatos = false;
		
		modeloClientes.setRowCount(0);
		modeloJuegos.setRowCount(0);
		
		// Tabla clientes
		try {
			hayDatos = rset1.next();
			if (hayDatos) {
				controlador.rellenarTablaClientes(rset1, modeloClientes);
				
			} else {
				throw new GameException("No puedes jugar sin haber registrado ningún cliente.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
				
		// Tabla juegos
		try {
			hayDatos = rset2.next();
			if (hayDatos) {
				controlador.rellenarTablaJuegos(rset2, modeloJuegos);
				
			} else {
				throw new GameException("No puedes jugar sin haber registrado ningún juego.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
	
	
	/**
	 * Método que comprueba que se hayan elegido campos en la tabla clientes y tabla juegos.
	 */
	public void comprobarTablas() {
		if (tableClientes.getSelectedRow() == -1 || tableJuegos.getSelectedRow() == -1 ) {
			btnJugar.setEnabled(false);
			return;
		}	
		
		btnJugar.setEnabled(true);
	}


	public HomeUI getMenu() {
		return menu;
	}
	
	
	
}
