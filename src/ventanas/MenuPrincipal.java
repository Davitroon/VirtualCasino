package ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import logica.Controlador;
import logica.Modelo;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
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
	private Modelo modelo;
	private Controlador controlador;

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
		setBounds(100, 100, 627, 435);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitulo = new JLabel("Simulador casino", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitulo.setBounds(0, 11, 613, 59);
		contentPane.add(lblTitulo);
		
		JButton btnJugar = new JButton("Jugar");
		btnJugar.setBounds(64, 121, 124, 32);
		contentPane.add(btnJugar);
		
		JButton btnAdministracion = new JButton("Administracion");
		btnAdministracion.setBounds(64, 164, 124, 32);
		contentPane.add(btnAdministracion);
		
		JButton btnEstadisticas = new JButton("Estadisticas");
		btnEstadisticas.setEnabled(false);
		btnEstadisticas.setBounds(64, 207, 124, 32);
		contentPane.add(btnEstadisticas);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBounds(64, 250, 124, 32);
		contentPane.add(btnSalir);
		
		JButton btnLogros = new JButton("Logros");
		btnLogros.setEnabled(false);
		btnLogros.setBounds(484, 338, 55, 49);
		contentPane.add(btnLogros);
		
		JButton btnNewButton = new JButton("Info");
		btnNewButton.setEnabled(false);
		btnNewButton.setBounds(549, 338, 54, 49);
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
		
		
		// Clic boton salir
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(ABORT);
			}
		});

	}
}
