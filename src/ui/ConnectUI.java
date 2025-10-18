package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import logic.Controller;
import logic.Model;
import logic.Session;
import logic.Validator;
import ui.ConnectUI;
import ui.SignInUI;
import ui.LogInUI;
import ui.HomeUI;

/**
 * Ventana donde el usuario podrá conectarse con un perfil de la BD. Será el inicio del programa si no se ha indicado un inicio de sesión automático.
 */
public class ConnectUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private LogInUI iniciarSesion;
	private SignInUI crearUsuario;
	private HomeUI menu;

	/**
	 * Create the frame.
	 * @param menu 
	 * @param usuario 
	 */
	public ConnectUI(Model modelo, Controller controlador, Session sesion, Validator validador, HomeUI menu) {
		this.menu = menu;
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 501, 301);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitulo = new JLabel("Conectarse", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblTitulo.setBounds(10, 28, 465, 31);
		contentPane.add(lblTitulo);
		
		JButton btnCrearUsuario = new JButton("Crear usuario");
		btnCrearUsuario.setBackground(new Color(128, 128, 255));
		btnCrearUsuario.setBounds(113, 112, 117, 45);
		contentPane.add(btnCrearUsuario);
		
		JButton btnIniciarSesion = new JButton("Iniciar sesión");
		btnIniciarSesion.setBackground(new Color(128, 128, 255));
		btnIniciarSesion.setBounds(253, 112, 117, 45);
		contentPane.add(btnIniciarSesion);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBackground(Color.GRAY);
		btnSalir.setBounds(10, 219, 105, 32);
		contentPane.add(btnSalir);
		
		// Cerrar ventana
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        modelo.cerrarConexion();
		        System.exit(0);
		    }
		});
		
		// Clic boton salir
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modelo.cerrarConexion();
				System.exit(0);
			}
		});		
		
		// Clic boton crear usuario
		btnCrearUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (crearUsuario == null) {
					crearUsuario = new SignInUI(modelo, controlador, sesion, ConnectUI.this, validador);
				}		
				setVisible(false);
				crearUsuario.setVisible(true);
			}
		});
		
		// Clic boton iniciar sesion
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (iniciarSesion == null) {
					iniciarSesion = new LogInUI(modelo, controlador, sesion, ConnectUI.this, validador);
				}		
				setVisible(false);
				iniciarSesion.setVisible(true);
			}
		});
		
		
	}

	public HomeUI getMenu() {
		return menu;
	}
}
