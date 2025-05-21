package ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import logica.*;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Ventana para la gestión de usuarios y juegos.
 * @author David
 * @since 3.0
 */
public class Gestion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableClientes;
	private JTable tableJuegos;
	private DefaultTableModel modeloClientes;
	private DefaultTableModel modeloJuegos;
	
	private MenuPrincipal menu;
	private FormularioCliente formularioCliente;
	private FormularioClienteEditar formularioClienteEditar;
	private FormularioJuego formularioJuego;
	private FormularioJuegoEditar formularioJuegoEditar;
	private Modelo modelo;
	private Controlador controlador;
	
	private JButton btnEditarClientes;
	private JButton btnBorrarClientes;
	private JButton btnEditarJuegos;
	private JButton btnBorrarJuegos;
	private JTabbedPane tabbedPane;

	/**
	 * Create the frame.
	 * @param menuPrincipal 
	 * @param modelo 
	 * @param controlador2 
	 */
	public Gestion(MenuPrincipal menu, Modelo modelo, Controlador controlador) {
		
		this.menu = menu;
		this.modelo = modelo;
		this.controlador = controlador;
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);		
		setBounds(100, 100, 802, 399);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 788, 362);
		contentPane.add(tabbedPane);
		
		JPanel panelUsuarios = new JPanel();
		tabbedPane.addTab("Usuarios", null, panelUsuarios, "Gestión de usuarios");
		panelUsuarios.setLayout(null);
		
		JLabel lblMisUsuarios = new JLabel("Mis Usuarios", SwingConstants.CENTER);
		lblMisUsuarios.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblMisUsuarios.setBounds(6, 26, 248, 36);
		panelUsuarios.add(lblMisUsuarios);
		
		JScrollPane scrollPaneUsuarios = new JScrollPane();
		scrollPaneUsuarios.setBounds(256, 66, 491, 241);
		panelUsuarios.add(scrollPaneUsuarios);
		
		modeloClientes = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"ID", "Nombre", "Edad", "Género", "Activo", "Saldo"
				}
			) {
				Class[] columnTypes = new Class[] {
					Integer.class, String.class, Integer.class, Character.class, String.class, Double.class
				};
			    boolean[] columnEditables = new boolean[] {
			            false, false, false, false, false, false  // todas las columnas NO editables
		        };
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			    @Override
			    public boolean isCellEditable(int row, int column) {
			        return columnEditables[column];
			    }
			};	
			
		tableClientes = new JTable();
		tableClientes.setModel(modeloClientes);
		tableClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	
		scrollPaneUsuarios.setViewportView(tableClientes);
		
		JButton btnAnadirUsuario = new JButton("Añadir usuario");
		btnAnadirUsuario.setBounds(60, 94, 132, 36);
		panelUsuarios.add(btnAnadirUsuario);
		
		btnEditarClientes = new JButton("Editar usuario");
		btnEditarClientes.setEnabled(false);
		btnEditarClientes.setBounds(60, 141, 132, 36);
		panelUsuarios.add(btnEditarClientes);
		
		btnBorrarClientes = new JButton("Borrar usuario");
		btnBorrarClientes.setEnabled(false);
		btnBorrarClientes.setBounds(60, 188, 132, 36);
		panelUsuarios.add(btnBorrarClientes);
		
		JButton btnVolverUsuario = new JButton("Volver");
		btnVolverUsuario.setBounds(60, 235, 132, 36);
		panelUsuarios.add(btnVolverUsuario);
		
		JPanel panelJuegos = new JPanel();
		panelJuegos.setLayout(null);
		tabbedPane.addTab("Juegos", null, panelJuegos, "Gestión de juegos");
		
		JLabel lblMisJuegos = new JLabel("Mis Juegos", SwingConstants.CENTER);
		lblMisJuegos.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblMisJuegos.setBounds(6, 27, 248, 36);
		panelJuegos.add(lblMisJuegos);
		
		JScrollPane scrollPaneJuegos = new JScrollPane();
		scrollPaneJuegos.setBounds(256, 66, 491, 241);
		panelJuegos.add(scrollPaneJuegos);
		
		modeloJuegos = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"ID", "Tipo", "Activo", "Dinero"
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
		
		JButton btnAnadirJuego = new JButton("Añadir juego");
		btnAnadirJuego.setBounds(60, 94, 132, 36);
		panelJuegos.add(btnAnadirJuego);
		
		btnEditarJuegos = new JButton("Editar juego");
		btnEditarJuegos.setEnabled(false);
		btnEditarJuegos.setBounds(60, 141, 132, 36);
		panelJuegos.add(btnEditarJuegos);
		
		btnBorrarJuegos = new JButton("Borrar juego");
		btnBorrarJuegos.setEnabled(false);
		btnBorrarJuegos.setBounds(60, 188, 132, 36);
		panelJuegos.add(btnBorrarJuegos);
		
		JButton btnVolverJuego = new JButton("Volver");
		btnVolverJuego.setBounds(60, 235, 132, 36);
		panelJuegos.add(btnVolverJuego);
		
		String[] opciones = {"Pedir carta", "Plantarse"};
		
		
		addWindowListener(new WindowAdapter() {
			// Al cerrar la ventana mediante la X
			@Override
			public void windowClosing(WindowEvent e) {
				reiniciarBotones();
				controlador.cambiarVentana(Gestion.this, menu);
				tabbedPane.setSelectedIndex(0);
				
			}
		});
		
		
		// Al cargar la ventana
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				
				actualizarTablaClientes();
				actualizarTablaJuegos();
			}
		});
		
		
		// Clic fila de la tabla clientes
		tableClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int fila = tableClientes.getSelectedRow();
				
				if (fila == -1 ) {
					reiniciarBotones();
					return;
				}	
				
				btnEditarClientes.setEnabled(true);
				btnBorrarClientes.setEnabled(true);
			}
		});
		

		// Clic boton añadir cliente
		btnAnadirUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (formularioCliente == null) {
					formularioCliente  = new FormularioCliente(Gestion.this, controlador, modelo);
				}
				
				controlador.cambiarVentana(Gestion.this, formularioCliente);
				reiniciarBotones();
			}
		});
		
		
		// Clic boton editar cliente
		btnEditarClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (formularioClienteEditar == null) {
					formularioClienteEditar = new FormularioClienteEditar(Gestion.this, controlador, modelo);
				}
				
				int id = Integer.parseInt(tableClientes.getValueAt(tableClientes.getSelectedRow(), 0).toString());
				formularioClienteEditar.cargarClienteOriginal(id);
				controlador.cambiarVentana(Gestion.this, formularioClienteEditar);
				reiniciarBotones();
			}
		});
		
		
		// Clic boton borrar cliente
		btnBorrarClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila = tableClientes.getSelectedRow();
				if (fila == -1 ) return;
				
				String id = String.valueOf(tableClientes.getValueAt(fila, 0)) ;
				modelo.borrarDato(id, "clientes");
		
				actualizarTablaClientes();
				reiniciarBotones();
			}
		});
		
		
		// Clic boton volver (cliente)
		btnVolverUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reiniciarBotones();
				controlador.cambiarVentana(Gestion.this, menu);
				tabbedPane.setSelectedIndex(0);
			}
		});
		
		
		// Clic fila de la tabla juegos
		tableJuegos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int fila = tableJuegos.getSelectedRow();
				
				if (fila == -1 ) {
					btnEditarJuegos.setEnabled(false);
					btnBorrarJuegos.setEnabled(false);
					return;
				}	
				
				btnEditarJuegos.setEnabled(true);
				btnBorrarJuegos.setEnabled(true);
			}
		});
		
		
		// Clic boton añadir juego
		btnAnadirJuego.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (formularioJuego == null) {
					formularioJuego  = new FormularioJuego(Gestion.this, controlador, modelo);
				}
				
				controlador.cambiarVentana(Gestion.this, formularioJuego);
				reiniciarBotones();
			}
		});
		
		
		// Clic boton editar juego
		btnEditarJuegos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (formularioJuegoEditar == null) {
					formularioJuegoEditar = new FormularioJuegoEditar(Gestion.this, controlador, modelo);
				}
				
				int id = Integer.parseInt(tableJuegos.getValueAt(tableJuegos.getSelectedRow(), 0).toString());
				formularioJuegoEditar.cargarJuegoOriginal(id);
				controlador.cambiarVentana(Gestion.this, formularioJuegoEditar);
				reiniciarBotones();
			}
		});
		
		
		// Clic boton borrar juego
		btnBorrarJuegos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila = tableJuegos.getSelectedRow();
				if (fila == -1 ) return;
				
				String id = String.valueOf(tableJuegos.getValueAt(fila, 0)) ;
				modelo.borrarDato(id, "juegos");
		
				actualizarTablaJuegos();
				reiniciarBotones();
			}
		});
		
		
		// Clic boton volver (Juego)
		btnVolverJuego.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reiniciarBotones();
				controlador.cambiarVentana(Gestion.this, menu);
				tabbedPane.setSelectedIndex(0);
			}
		});
	
	}
	
	
	/**
	 * Método que consulta a la tabla cliente en la BD para actualizar la tabla visual.
	 */
	public void actualizarTablaClientes() {
		
		modeloClientes.setRowCount(0);
		
		ResultSet rset = modelo.consultarDatos("clientes", false);
		
		try {		
			boolean hayDatos = rset.next();
			
			if (!hayDatos) {
				reiniciarBotones();
			} else {
				do {
					// "ID", "Nombre", "Edad", "Género", "Activo", "Saldo"
			        Object[] clienteLista = new Object[6];
			        clienteLista[0] = rset.getInt(1);
			        clienteLista[1] = rset.getString(2);
			        clienteLista[2] = rset.getInt(3);
			        clienteLista[3] = rset.getString(4);
			        clienteLista[4] = (rset.getBoolean(5) == true ? "SI" : "NO");
			        clienteLista[5] = rset.getDouble(6);
			        
			        modeloClientes.addRow(clienteLista);
			        
				} while (rset.next());
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Método que consulta a la tabla juegos en la BD para actualizar la tabla visual.
	 */
	public void actualizarTablaJuegos() {
		
		modeloJuegos.setRowCount(0);
		ResultSet rset = modelo.consultarDatos("juegos", false);
		
		try {
			boolean hayDatos = rset.next();
			
			if (!hayDatos) {
				reiniciarBotones();
	            
			} else {
				do {
					// "ID", "Tipo", "Activo", "Dinero"
			        Object[] juegoLista = new Object[4];
			        juegoLista[0] = rset.getInt(1);
			        juegoLista[1] = rset.getString(2);
			        juegoLista[2] = (rset.getBoolean(3) == true ? "SI" : "NO");
			        juegoLista[3] = rset.getDouble(4);
			        
			        modeloJuegos.addRow(juegoLista);
				} while (rset.next());
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}	
	}
	
	
	/**
	 * Método que restablece los botones a su estado original.
	 */
	public void reiniciarBotones() {
		btnEditarClientes.setEnabled(false);
		btnBorrarClientes.setEnabled(false);
		btnEditarJuegos.setEnabled(false);
		btnBorrarJuegos.setEnabled(false);
	}
}
