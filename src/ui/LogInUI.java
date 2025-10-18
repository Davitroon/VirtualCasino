package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import logic.Controller;
import logic.Model;
import logic.Session;
import logic.User;
import logic.Validator;
import ui.ConnectUI;
import ui.LogInUI;

/**
 * Ventana donde el usuario podra iniciar sesión.
 */
public class LogInUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textNombre;
	
	private User usuario;
	
	private boolean nombreValido;
	private boolean contraseñaValida;
	private JPasswordField passwordField;
	private JButton btnAceptar;
	private JLabel lblErrorNombre;
	private JCheckBox chckbxRecordarSesion;
	private JCheckBox chckbxMostrar;

	/**
	 * Create the frame.
	 */
	public LogInUI(Model modelo, Controller controlador, Session sesion, ConnectUI login, Validator validador) {
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 533, 367);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblIniciarSesion = new JLabel("Iniciar sesión", SwingConstants.CENTER);
		lblIniciarSesion.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblIniciarSesion.setBounds(10, 29, 486, 31);
		contentPane.add(lblIniciarSesion);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(Color.GRAY);
		btnVolver.setBounds(21, 273, 105, 32);
		contentPane.add(btnVolver);
		
		JLabel lblNombre = new JLabel("Nombre de usuario");
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNombre.setBounds(89, 107, 133, 14);
		contentPane.add(lblNombre);
		
		textNombre = new JTextField();
		textNombre.setBounds(228, 101, 172, 26);
		textNombre.setColumns(10);
		contentPane.add(textNombre);
		
		
		JLabel lblContrasena = new JLabel("Contraseña");
		lblContrasena.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblContrasena.setBounds(89, 165, 129, 15);
		contentPane.add(lblContrasena);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setEnabled(false);
		btnAceptar.setBounds(391, 273, 105, 32);
		contentPane.add(btnAceptar);
		
		lblErrorNombre = new JLabel("");
		lblErrorNombre.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErrorNombre.setForeground(new Color(255, 0, 0));
		lblErrorNombre.setBounds(89, 132, 311, 14);
		contentPane.add(lblErrorNombre);
		
		JLabel lblContrasenaAviso = new JLabel("La contraseña debe tener mínimo 8 caracteres");
		lblContrasenaAviso.setForeground(Color.RED);
		lblContrasenaAviso.setBounds(89, 191, 288, 20);
		contentPane.add(lblContrasenaAviso);
		
		passwordField = new JPasswordField();
		passwordField.setEchoChar('*');
		passwordField.setBounds(189, 160, 149, 26);
		contentPane.add(passwordField);
		
		chckbxMostrar = new JCheckBox("Mostrar");
		chckbxMostrar.setBounds(344, 162, 69, 23);
		contentPane.add(chckbxMostrar);
		
		chckbxRecordarSesion = new JCheckBox("Recordar inicio de sesión");
		chckbxRecordarSesion.setBounds(89, 218, 201, 23);
		contentPane.add(chckbxRecordarSesion);
		
		// Clic boton volver
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reiniciarContenido();
				controlador.cambiarVentana(LogInUI.this, login);
			}
		});	
		
		// Al escribir en el campo nombre
		textNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {			
				nombreValido = false;
				String texto = textNombre.getText();
				
				if (validador.validarNombre(texto, lblErrorNombre)) {
					nombreValido = true;
				}
				
				revisarFormulario();
			}
		});	
				
		// Al escribir en el campo contraseña
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				contraseñaValida = false;
				if (passwordField.getPassword().length >= 8) {
					contraseñaValida = true;
					lblContrasenaAviso.setForeground(Color.GRAY);
					
				} else {
					lblContrasenaAviso.setForeground(Color.RED);
				}
				
				revisarFormulario();
			}
		});
			
		// Mostrar u ocultar contraseña
		chckbxMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    if (chckbxMostrar.isSelected()) {
			        passwordField.setEchoChar((char) 0); // Mostrar
			    } else {
			        passwordField.setEchoChar('*'); // Ocultar
			    }
			}
		});
		
		// Clic boton aceptar
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				char[] passwordChars = passwordField.getPassword();
				String contraseña = new String(passwordChars);
				
				// Revisa si el usuario existe.
				try {
					ResultSet rset = (modelo.consultarUsuario(textNombre.getText()));
					if (rset != null) {
						usuario = new User(rset.getInt("id"), rset.getString("username"), rset.getString("user_password"), rset.getString("email"), rset.getString("last_access"), rset.getBoolean("remember_login"));
						
						// Validar que la contraseña del usuario sea igual que la contraseña escrita
						if (!usuario.getContrasena().equals(contraseña)) {
							JOptionPane.showMessageDialog(
								    null,
								    "Contraseña incorrecta",
								    "Advertencia",
								    JOptionPane.WARNING_MESSAGE
								);
							
						} else {
							// Si se ha marcado que se recuerde la sesion, lo modifica antes de entrar
							if (chckbxRecordarSesion.isSelected()) {
								modelo.alternarRecordarSesion(usuario.getNombre(), true);
							}
							sesion.iniciarSesion(usuario, LogInUI.this);
							reiniciarContenido();
						}					
						rset.close();
						
					} else {
						JOptionPane.showMessageDialog(
							    null,
							    "El usuario " + textNombre.getText() + " no está registrado",
							    "Advertencia",
							    JOptionPane.WARNING_MESSAGE
							);
					}			

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		// Al cerrar la ventana mediante la X
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				reiniciarContenido();
				controlador.cambiarVentana(LogInUI.this, login);
			}
		});
	}
	
	
	/**
	 * Reinicia el contenido de la pagina para cerrarse.
	 */
	public void reiniciarContenido() {
		lblErrorNombre.setText("");
		btnAceptar.setEnabled(false);
		textNombre.setText("");
		passwordField.setText("");
		chckbxRecordarSesion.setSelected(false);
		chckbxMostrar.setSelected(false);
	}
	
	
	/**
	 * Revisa el formulario para asegurarse que los datos sean correctos.
	 */
	public void revisarFormulario() {
		if (nombreValido && contraseñaValida) {
			btnAceptar.setEnabled(true);
			
		} else {
			btnAceptar.setEnabled(false);
		}
	}


	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}
}
