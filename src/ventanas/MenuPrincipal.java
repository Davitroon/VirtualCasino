package ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import excepciones.JugarExcepcion;
import logica.Controlador;
import logica.Modelo;
import logica.Usuario;
import logica.Validador;

/**
 * Ventana del menú principal.
 * @author David
 * @since 3.0
 */
public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;	
	
	private Gestion gestion;
	private Jugar jugar;
	private Estadisticas estadisticas;
	private Usuario usuario;
	private Perfil perfil;
	private Conectarse conectarse;
	
	private JButton btnEstadisticas;

	/**
	 * Create the frame.
	 * @param controlador 
	 * @param modelo 
	 * @param usuario 
	 */
	public MenuPrincipal(Modelo modelo, Controlador controlador, Validador validador) {
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 370);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitulo = new JLabel("Simulador casino", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblTitulo.setBounds(60, 21, 375, 59);
		contentPane.add(lblTitulo);
		
		JButton btnJugar = new JButton("Jugar");
		btnJugar.setBackground(new Color(128, 128, 255));
		btnJugar.setBounds(193, 101, 124, 40);
		contentPane.add(btnJugar);
		
		JButton btnAdministracion = new JButton("Administracion");
		btnAdministracion.setBackground(new Color(128, 128, 255));
		btnAdministracion.setBounds(193, 152, 124, 40);
		contentPane.add(btnAdministracion);
		
		btnEstadisticas = new JButton("Estadisticas");
		btnEstadisticas.setBackground(new Color(128, 128, 255));
		btnEstadisticas.setBounds(193, 203, 124, 40);
		contentPane.add(btnEstadisticas);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBackground(new Color(128, 128, 128));
		btnSalir.setBounds(10, 284, 105, 32);
		contentPane.add(btnSalir);
		
		JButton btnInfo = new JButton("?");
		btnInfo.setBackground(new Color(128, 255, 255));
		btnInfo.setBounds(465, 288, 37, 32);
		contentPane.add(btnInfo);
		
		JButton btnPerfil = new JButton("Perfil");
		btnPerfil.setBounds(445, 11, 57, 32);
		contentPane.add(btnPerfil);
		
		// Cerrar ventana
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	modelo.actualizarUltimoAcceso(usuario.getNombre());
		        modelo.cerrarConexion();
		        System.exit(0);
		    }
		});
		
		// Clic boton jugar
		btnJugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (jugar == null) {
						jugar = new Jugar(MenuPrincipal.this, modelo, controlador);
					}
					jugar.actualizarTablas();
					controlador.cambiarVentana(MenuPrincipal.this, jugar);
				
				} catch (JugarExcepcion ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage() , "Advertencia", JOptionPane.WARNING_MESSAGE);
				}
				
			}
		});
		
		// Clic boton administración
		btnAdministracion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (gestion == null) {
					gestion = new Gestion(MenuPrincipal.this, modelo, controlador, validador);
				}			
				controlador.cambiarVentana(MenuPrincipal.this, gestion);
				
			}
		});
		
		// Clic boton estadisticas
		btnEstadisticas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (estadisticas == null) {
					estadisticas = new Estadisticas(MenuPrincipal.this, modelo, controlador, usuario);
					
				} else {
					estadisticas.setUsuario(usuario);
				}
				estadisticas.actualizarDatos();
				controlador.cambiarVentana(MenuPrincipal.this, estadisticas);
			}
		});
		
		// Clic boton perfil
		btnPerfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				perfil.actualizarDatos(usuario);
				controlador.cambiarVentana(MenuPrincipal.this, perfil);
			}
		});
		
		// Clic boton salir
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modelo.actualizarUltimoAcceso(usuario.getNombre());
				modelo.cerrarConexion();
				System.exit(0);
			}
		});
		
		// Clic botón info
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        String mensaje = """
		                ¿Que es esta aplicación?
		                
		                - Es un programa pensado para simular la gestión de un casino.
		                - Podrás simular juegos reales de un casino en la pestaña de "Jugar"
		               (Por ahora solo hay Blackjack y Tragaperras).
		        		- Para jugar necesitarás crear Usuarios y Juegos, los cuales los podrás gestionar
		        		en el apartado de "Gestión".
		        		- Todas tus partidas serán guardadas en la base de datos, si eres curioso puedes
		        		ver tus estadísticas en su ventana correspondiente.
		        		
		        		¡Experimenta y diviertete!
		        		
		        		~ Version: 3.0 ~               
		                """;

		        JOptionPane.showMessageDialog(null, mensaje, "Guía de la aplicación", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public Perfil getPerfil() {
		return perfil;
	}
	
	
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}


	public Conectarse getConectarse() {
		return conectarse;
	}


	public void setConectarse(Conectarse conectarse) {
		this.conectarse = conectarse;
	}
	
	
	
}