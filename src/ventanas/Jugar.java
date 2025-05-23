package ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logica.ApuestaExcepcion;
import logica.Blackjack;
import logica.Cliente;
import logica.Controlador;
import logica.Juego;
import logica.Modelo;
import logica.Tragaperras;

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
	
	private BlackjackVentana wndBlackjack;
	private TragaperrasVentana wndTragaperras;
	

	/**
	 * Create the frame.
	 * @param menu
	 * @param modelo
	 * @param controlador
	 */
	public Jugar(MenuPrincipal menu, Modelo modelo, Controlador controlador) {
		
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
		btnJugar.setBounds(577, 327, 103, 31);
		contentPane.add(btnJugar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(464, 327, 103, 31);
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

		        ResultSet rsetCliente = modelo.consultarDatoUnico("clientes", Integer.parseInt(tableClientes.getValueAt(tableClientes.getSelectedRow(), 0).toString()));
		        ResultSet rsetJuego = modelo.consultarDatoUnico("juegos", Integer.parseInt(tableJuegos.getValueAt(tableJuegos.getSelectedRow(), 0).toString()));

		        Cliente cliente = null;
		        Juego juego = null;

		        try {
		            cliente = new Cliente(rsetCliente.getInt("id"), rsetCliente.getString("nombre"), rsetCliente.getDouble("saldo"));

		            if (rsetJuego.getString("tipo").equals("Blackjack")) {
		                juego = new Blackjack(rsetJuego.getInt("id"), rsetJuego.getDouble("dinero"));
		            } else if (rsetJuego.getString("tipo").equals("Tragaperras")) {
		                juego = new Tragaperras(rsetJuego.getInt("id"), rsetJuego.getDouble("dinero"));
		            }

		            double apuesta = controlador.alertaApuesta(cliente, juego);

		            if (apuesta != 0) {
		                controlador.abrirJuegoVentana(juego, Jugar.this, cliente, apuesta);
		                btnJugar.setEnabled(false);
		            }

		        } catch (SQLException ex) {
		            ex.printStackTrace();
		            
		        } catch (ApuestaExcepcion ex) {
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
	 */
	public void actualizarTablas() {
		
		ResultSet rset1 = modelo.consultarDatos("clientes", true);		
		ResultSet rset2 = modelo.consultarDatos("juegos", true);		
		boolean hayDatos = false;
		
		modeloClientes.setRowCount(0);
		modeloJuegos.setRowCount(0);
		
		// Tabla clientes
		try {
			hayDatos = rset1.next();
			if (hayDatos) {
				controlador.rellenarTablaClientes(rset1, modeloClientes);
			}
			rset1.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}			
				
		// Tabla juegos
		try {
			hayDatos = rset2.next();
			if (hayDatos) {
				controlador.rellenarTablaJuegos(rset2, modeloJuegos);
			}
			rset2.close();
			
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
