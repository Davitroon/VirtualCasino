package ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logica.Blackjack;
import logica.Cliente;
import logica.Controlador;
import logica.Modelo;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

/**
 * Ventana para comenzar los juegos.
 * @author David
 * @since 3.0
 */
public class Jugar extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableClientes;
	private JTable tableJuegos;
	
	private DefaultTableModel modeloClientes;
	private DefaultTableModel modeloJuegos;
	
	private MenuPrincipal menu;
	private Modelo modelo;
	private Controlador controlador;
	private JButton btnJugar;
	
	private WndBlackjack wndBlackjack;

	/**
	 * Create the frame.
	 * @param modelo 
	 * @param controlador 
	 */
	public Jugar(MenuPrincipal menu, Modelo modelo, Controlador controlador) {
		
		this.menu = menu;
		this.modelo = modelo;
		this.controlador = controlador;	
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 623, 435);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblJuego = new JLabel("Jugar", SwingConstants.CENTER);
		lblJuego.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblJuego.setBounds(40, 11, 536, 31);
		contentPane.add(lblJuego);
		
		JLabel lblClientes = new JLabel("Clientes", SwingConstants.CENTER);
		lblClientes.setBounds(40, 82, 248, 31);
		contentPane.add(lblClientes);
		
		JScrollPane scrollPaneCliente = new JScrollPane();
		scrollPaneCliente.setBounds(40, 124, 248, 166);
		contentPane.add(scrollPaneCliente);
		
		modeloClientes = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Id", "Nombre", "Activo", "Saldo"
				}
			) {
				Class[] columnTypes = new Class[] {
					Integer.class, String.class, String.class, Double.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				boolean[] columnEditables = new boolean[] {
					false, false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
				
			};
		tableClientes = new JTable();
		tableClientes.setModel(modeloClientes);
		tableClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneCliente.setViewportView(tableClientes);
		
		JLabel lblJuegos = new JLabel("Juegos", SwingConstants.CENTER);
		lblJuegos.setBounds(328, 86, 248, 23);
		contentPane.add(lblJuegos);
		
		JScrollPane scrollPaneJuegos = new JScrollPane();
		scrollPaneJuegos.setBounds(328, 124, 248, 166);
		contentPane.add(scrollPaneJuegos);
		
		modeloJuegos = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Id", "Tipo", "Activo", "Dinero"
				}
			) {
				Class[] columnTypes = new Class[] {
					Integer.class, String.class, String.class, Double.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				boolean[] columnEditables = new boolean[] {
					false, false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			};
		tableJuegos = new JTable();
		tableJuegos.setModel(modeloJuegos);
		tableJuegos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneJuegos.setViewportView(tableJuegos);
		
		btnJugar = new JButton("Jugar");
		btnJugar.setEnabled(false);
		btnJugar.setBounds(473, 327, 103, 31);
		contentPane.add(btnJugar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(360, 327, 103, 31);
		contentPane.add(btnVolver);
		
		actualizarTablas();
		
		// Clic boton volver
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlador.cambiarVentana(Jugar.this, menu);
				btnJugar.setEnabled(false);
			}
		});
		
		// Al cerrar la ventana mediante la X
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controlador.cambiarVentana(Jugar.this, menu);
				btnJugar.setEnabled(false);
			}
		});
		
		
		
		// Al cargar la ventana
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				actualizarTablas();
			}
		});
		
		
		// Clic boton jugar
		btnJugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				
				ResultSet rsetCliente = modelo.consultarDatoUnico("clientes", Integer.parseInt(tableClientes.getValueAt(tableClientes.getSelectedRow(), 0).toString()) );
				ResultSet rsetJuego = modelo.consultarDatoUnico("juegos", Integer.parseInt(tableJuegos.getValueAt(tableJuegos.getSelectedRow(), 0).toString()) );
				
				Cliente cliente = null;
				Blackjack blackjack = null;
				
				try {
				   cliente = new Cliente (rsetCliente.getInt("id"), rsetCliente.getDouble("saldo"));
				   blackjack = new Blackjack (rsetJuego.getInt("id"), rsetJuego.getDouble("dinero"));
				    
				} catch (SQLException e1) {
				    e1.printStackTrace();
				}
				
				while (true) {
					String apuesta = JOptionPane.showInputDialog("Ingresa una apuesta");
					if (apuesta == null) break;
					String mensajeError = null;
					
					mensajeError = controlador.validarApuesta(apuesta, cliente.getSaldo(), blackjack.getDinero());
					
					if (mensajeError != null) {
						 JOptionPane.showMessageDialog(null, mensajeError, "Error", JOptionPane.ERROR_MESSAGE);
						 
					} else {
						if (wndBlackjack == null) {
							wndBlackjack = new WndBlackjack(controlador, Jugar.this, cliente, blackjack, Double.parseDouble(apuesta));
						}					
						
						wndBlackjack.iniciarJuego();
						controlador.cambiarVentana(Jugar.this, wndBlackjack);
						btnJugar.setEnabled(false);
						break;
					}
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
	 */
	public void actualizarTablas() {
		
		ResultSet rset1 = modelo.consultarDatos("clientes");		
		ResultSet rset2 = modelo.consultarDatos("juegos");		
		
		modeloClientes.setRowCount(0);
		modeloJuegos.setRowCount(0);
		
		// Tabla clientes
		try {
			boolean hayDatos = rset1.next();
			
			if (hayDatos) {
				do {
					// "ID", "Nombre", "Activo", "Saldo"
			        Object[] clienteLista = new Object[4];
			        clienteLista[0] = rset1.getInt(1);
			        clienteLista[1] = rset1.getString(2);
			        clienteLista[2] = (rset1.getBoolean(5) == true ? "SI" : "NO");
			        clienteLista[3] = rset1.getDouble(6);
			        
			        modeloClientes.addRow(clienteLista);
				} while (rset1.next());
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}	
				
		// Tabla juegos
		try {
			boolean hayDatos = rset2.next();
			
			if (hayDatos) {
				do {
					// "ID", "Tipo", "Activo", "Dinero"
			        Object[] juegoLista = new Object[4];
			        juegoLista[0] = rset2.getInt(1);
			        juegoLista[1] = rset2.getString(2);
			        juegoLista[2] = (rset2.getBoolean(3) == true ? "SI" : "NO");
			        juegoLista[3] = rset2.getDouble(4);
			        
			        modeloJuegos.addRow(juegoLista);
				} while (rset2.next());
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
}
