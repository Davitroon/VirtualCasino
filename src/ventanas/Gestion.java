package ventanas;

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

import logica.Controlador;
import logica.Modelo;
import logica.Validador;

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
	
	private FormularioCliente formularioCliente;
	private FormularioClienteEditar formularioClienteEditar;
	private FormularioJuego formularioJuego;
	private FormularioJuegoEditar formularioJuegoEditar;
	private Modelo modelo;
	private Controlador controlador;
	
	private JButton btnEditarCliente;
	private JButton btnBorrarCliente;
	private JButton btnEditarJuego;
	private JButton btnBorrarJuego;
	private JTabbedPane tabbedPane;
	private JButton btnBorrarClientes;
	private JButton btnBorrarJuegos;

	/**
	 * Create the frame.
	 * @param menuPrincipal 
	 * @param modelo 
	 * @param controlador2 
	 */
	public Gestion(MenuPrincipal menu, Modelo modelo, Controlador controlador, Validador validador) {
		setResizable(false);
		
		this.modelo = modelo;
		this.controlador = controlador;
		
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
		
		JPanel panelUsuarios = new JPanel();
		tabbedPane.addTab("Usuarios", null, panelUsuarios, "Gestión de usuarios");
		panelUsuarios.setLayout(null);
		
		JLabel lblMisClientes = new JLabel("Mis clientes", SwingConstants.CENTER);
		lblMisClientes.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblMisClientes.setBounds(6, 26, 248, 36);
		panelUsuarios.add(lblMisClientes);
		
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
				/**
				 * 
				 */
				private static final long serialVersionUID = 1602146996076819230L; // Los SerialId y SupressWarnings los esta poniendo eclipse, no quiero que salgan advertencias
				@SuppressWarnings("rawtypes")
				Class[] columnTypes = new Class[] {
					Integer.class, String.class, Integer.class, Character.class, String.class, Double.class
				};
			    boolean[] columnEditables = new boolean[] {
			            false, false, false, false, false, false  // todas las columnas NO editables
		        };
				public Class<?> getColumnClass(int columnIndex) {
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
		
		JButton btnAnadircliente = new JButton("Añadir cliente");
		btnAnadircliente.setBackground(new Color(128, 128, 255));
		btnAnadircliente.setBounds(60, 94, 132, 36);
		panelUsuarios.add(btnAnadircliente);
		
		btnEditarCliente = new JButton("Editar cliente");
		btnEditarCliente.setBackground(new Color(128, 128, 255));
		btnEditarCliente.setEnabled(false);
		btnEditarCliente.setBounds(60, 141, 132, 36);
		panelUsuarios.add(btnEditarCliente);
		
		btnBorrarCliente = new JButton("Borrar cliente");
		btnBorrarCliente.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnBorrarCliente.setBackground(new Color(242, 77, 77));
		btnBorrarCliente.setEnabled(false);
		btnBorrarCliente.setBounds(60, 188, 132, 36);
		panelUsuarios.add(btnBorrarCliente);
		
		JButton btnVolverCliente = new JButton("Volver");
		btnVolverCliente.setBackground(new Color(128, 128, 128));
		btnVolverCliente.setBounds(60, 235, 132, 36);
		panelUsuarios.add(btnVolverCliente);
		
		btnBorrarClientes = new JButton("Borrar clientes");
		btnBorrarClientes.setEnabled(false);
		btnBorrarClientes.setForeground(Color.BLACK);
		btnBorrarClientes.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnBorrarClientes.setBackground(new Color(242, 77, 77));
		btnBorrarClientes.setBounds(605, 26, 142, 32);
		panelUsuarios.add(btnBorrarClientes);
		
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
				/**
				 * 
				 */
				private static final long serialVersionUID = -1612780249715316697L;
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
		
		JButton btnAnadirJuego = new JButton("Añadir juego");
		btnAnadirJuego.setBackground(new Color(128, 128, 255));
		btnAnadirJuego.setBounds(60, 94, 132, 36);
		panelJuegos.add(btnAnadirJuego);
		
		btnEditarJuego = new JButton("Editar juego");
		btnEditarJuego.setBackground(new Color(128, 128, 255));
		btnEditarJuego.setEnabled(false);
		btnEditarJuego.setBounds(60, 141, 132, 36);
		panelJuegos.add(btnEditarJuego);
		
		btnBorrarJuego = new JButton("Borrar juego");
		btnBorrarJuego.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnBorrarJuego.setBackground(new Color(242, 77, 77));
		btnBorrarJuego.setEnabled(false);
		btnBorrarJuego.setBounds(60, 188, 132, 36);
		panelJuegos.add(btnBorrarJuego);
		
		JButton btnVolverJuego = new JButton("Volver");
		btnVolverJuego.setBackground(new Color(128, 128, 128));
		btnVolverJuego.setBounds(60, 235, 132, 36);
		panelJuegos.add(btnVolverJuego);				
		
		btnBorrarJuegos = new JButton("Borrar juegos");
		btnBorrarJuegos.setForeground(Color.BLACK);
		btnBorrarJuegos.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnBorrarJuegos.setEnabled(false);
		btnBorrarJuegos.setBackground(new Color(242, 77, 77));
		btnBorrarJuegos.setBounds(604, 27, 142, 32);
		panelJuegos.add(btnBorrarJuegos);
		
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
				
				btnEditarCliente.setEnabled(true);
				btnBorrarCliente.setEnabled(true);
			}
		});		

		// Clic boton añadir cliente
		btnAnadircliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (formularioCliente == null) {
					formularioCliente  = new FormularioCliente(Gestion.this, controlador, modelo, validador);
				}
				
				controlador.cambiarVentana(Gestion.this, formularioCliente);
				reiniciarBotones();
			}
		});		
		
		// Clic boton editar cliente
		btnEditarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (formularioClienteEditar == null) {
					formularioClienteEditar = new FormularioClienteEditar(Gestion.this, controlador, modelo, validador);
				}
				
				int id = Integer.parseInt(tableClientes.getValueAt(tableClientes.getSelectedRow(), 0).toString());
				formularioClienteEditar.cargarClienteOriginal(id);
				controlador.cambiarVentana(Gestion.this, formularioClienteEditar);
				reiniciarBotones();
			}
		});
			
		// Clic boton borrar cliente
		btnBorrarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila = tableClientes.getSelectedRow();
				if (fila == -1 ) return;
				
				String id = String.valueOf(tableClientes.getValueAt(fila, 0)) ;
				modelo.borrarDato(id, "clientes");
		
				actualizarTablaClientes();
				reiniciarBotones();
			}
		});	
		
		// Clic boton borrar clientes
		btnBorrarClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 int opcion = JOptionPane.showConfirmDialog(
					        null,
					        "¿Estás seguro de que deseas borrar todos los clientes?",
					        "Confirmar borrado",
					        JOptionPane.YES_NO_OPTION,
					        JOptionPane.WARNING_MESSAGE
					    );

			    if (opcion == JOptionPane.YES_OPTION) {
			        modelo.borrarDatosTabla("clientes");
			        btnBorrarClientes.setEnabled(false);
			        actualizarTablaClientes();
			    }
			}
		});
		
		// Clic boton volver (cliente)
		btnVolverCliente.addActionListener(new ActionListener() {
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
					btnEditarJuego.setEnabled(false);
					btnBorrarJuego.setEnabled(false);
					return;
				}	
				
				btnEditarJuego.setEnabled(true);
				btnBorrarJuego.setEnabled(true);
			}
		});
				
		// Clic boton añadir juego
		btnAnadirJuego.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (formularioJuego == null) {
					formularioJuego  = new FormularioJuego(Gestion.this, controlador, modelo, validador);
				}
				
				controlador.cambiarVentana(Gestion.this, formularioJuego);
				reiniciarBotones();
			}
		});		
		
		// Clic boton editar juego
		btnEditarJuego.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (formularioJuegoEditar == null) {
					formularioJuegoEditar = new FormularioJuegoEditar(Gestion.this, controlador, modelo, validador);
				}
				
				int id = Integer.parseInt(tableJuegos.getValueAt(tableJuegos.getSelectedRow(), 0).toString());
				formularioJuegoEditar.cargarJuegoOriginal(id);
				controlador.cambiarVentana(Gestion.this, formularioJuegoEditar);
				reiniciarBotones();
			}
		});
			
		// Clic boton borrar juego
		btnBorrarJuego.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila = tableJuegos.getSelectedRow();
				if (fila == -1 ) return;
				
				String id = String.valueOf(tableJuegos.getValueAt(fila, 0)) ;
				modelo.borrarDato(id, "juegos");
		
				actualizarTablaJuegos();
				reiniciarBotones();
			}
		});
		
		// Clic boton borrar juegos
		btnBorrarJuegos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 int opcion = JOptionPane.showConfirmDialog(
					        null,
					        "¿Estás seguro de que deseas borrar todos los juegos?",
					        "Confirmar borrado",
					        JOptionPane.YES_NO_OPTION,
					        JOptionPane.WARNING_MESSAGE
					    );

			    if (opcion == JOptionPane.YES_OPTION) {
			        modelo.borrarDatosTabla("juegos");
			        actualizarTablaJuegos();
			    }
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
				btnBorrarClientes.setEnabled(false);
				
			} else {
				controlador.rellenarTablaClientesCompleto(rset, modeloClientes);
				btnBorrarClientes.setEnabled(true);
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
				btnBorrarJuegos.setEnabled(false);
	            
			} else {
				controlador.rellenarTablaJuegos(rset, modeloJuegos);
				btnBorrarJuegos.setEnabled(true);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}	
	}
	
	
	/**
	 * Método que restablece los botones a su estado original.
	 */
	public void reiniciarBotones() {
		btnEditarCliente.setEnabled(false);
		btnBorrarCliente.setEnabled(false);
		btnEditarJuego.setEnabled(false);
		btnBorrarJuego.setEnabled(false);
	}
}
