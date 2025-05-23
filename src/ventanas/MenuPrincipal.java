package ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import logica.Controlador;
import logica.Modelo;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

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
	
	private Modelo modelo;
	private Controlador controlador;
	private JButton btnEstadisticas;

	/**
	 * Create the frame.
	 * @param controlador 
	 * @param modelo 
	 */
	public MenuPrincipal(Modelo modelo, Controlador controlador) {
		this.modelo = modelo;
		this.controlador = controlador;
		
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
		lblTitulo.setBounds(10, 21, 492, 59);
		contentPane.add(lblTitulo);
		
		JButton btnJugar = new JButton("Jugar");
		btnJugar.setBounds(193, 101, 124, 40);
		contentPane.add(btnJugar);
		
		JButton btnAdministracion = new JButton("Administracion");
		btnAdministracion.setBounds(193, 152, 124, 40);
		contentPane.add(btnAdministracion);
		
		btnEstadisticas = new JButton("Estadisticas");
		btnEstadisticas.setBounds(193, 203, 124, 40);
		contentPane.add(btnEstadisticas);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBounds(193, 254, 124, 42);
		contentPane.add(btnSalir);
		
		JButton btnNewButton = new JButton("Info");
		btnNewButton.setBounds(437, 271, 65, 49);
		contentPane.add(btnNewButton);
		
		// Clic boton jugar
		btnJugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jugar == null) {
					jugar = new Jugar(MenuPrincipal.this, modelo, controlador);
				}
				controlador.cambiarVentana(MenuPrincipal.this, jugar);
			}
		});
		
		// Clic boton administración
		btnAdministracion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (gestion == null) {
					gestion = new Gestion(MenuPrincipal.this, modelo, controlador);
				}			
				controlador.cambiarVentana(MenuPrincipal.this, gestion);
				
			}
		});
		
		// Clic boton estadisticas
		btnEstadisticas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (estadisticas == null) {
					estadisticas = new Estadisticas(MenuPrincipal.this, modelo, controlador);
				}
				estadisticas.actualizarDatos();
				controlador.cambiarVentana(MenuPrincipal.this, estadisticas);
			}
		});
		
		
		// Clic boton salir
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(ABORT);
			}
		});
		
		// Clic botón info
		btnNewButton.addActionListener(new ActionListener() {
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
}