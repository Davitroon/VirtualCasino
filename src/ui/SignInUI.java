package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLIntegrityConstraintViolationException;

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

import exceptions.MailException;
import logic.Controller;
import logic.Model;
import logic.Session;
import logic.User;
import logic.Validator;
import ui.ConnectUI;
import ui.SignInUI;

/**
 * Ventana donde se podra crear un usuario e iniciar sesión como el.
 */
public class SignInUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textNombre;
	
	private boolean nombreValido;
	private boolean contraseñaValida;
	private boolean contraseñaValida2;
	private boolean correoValido;
	
	private JPasswordField passwordField;
	private JButton btnAceptar;
	private JLabel lblErrorNombre;
	private JCheckBox chckbxRecordarSesion;
	private JPasswordField passwordField2;
	private JLabel lblCorreo;
	private JTextField textCorreo;
	private JCheckBox chckbxMostrar;

	private User usuario;
	private JLabel lblContrasenaAviso;
	/**
	 * Create the frame.
	 */
	public SignInUI(Model modelo, Controller controlador, Session sesion, ConnectUI login, Validator validador) {
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 658, 363);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCrearUsuario = new JLabel("Crear usuario", SwingConstants.CENTER);
		lblCrearUsuario.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblCrearUsuario.setBounds(10, 29, 622, 31);
		contentPane.add(lblCrearUsuario);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(Color.GRAY);
		btnVolver.setBounds(27, 276, 105, 32);
		contentPane.add(btnVolver);
		
		JLabel lblNombre = new JLabel("Nombre de usuario");
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNombre.setBounds(27, 106, 133, 14);
		contentPane.add(lblNombre);
		
		textNombre = new JTextField();
		textNombre.setBounds(161, 101, 172, 26);
		textNombre.setColumns(10);
		contentPane.add(textNombre);
		
		
		JLabel lblContrasena = new JLabel("Contraseña");
		lblContrasena.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblContrasena.setBounds(27, 161, 129, 15);
		contentPane.add(lblContrasena);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setEnabled(false);
		btnAceptar.setBounds(517, 276, 105, 32);
		contentPane.add(btnAceptar);
		
		lblErrorNombre = new JLabel("");
		lblErrorNombre.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErrorNombre.setForeground(new Color(255, 0, 0));
		lblErrorNombre.setBounds(27, 131, 306, 14);
		contentPane.add(lblErrorNombre);
		
		lblContrasenaAviso = new JLabel("La contraseña debe tener mínimo 8 caracteres");
		lblContrasenaAviso.setForeground(Color.RED);
		lblContrasenaAviso.setBounds(27, 187, 272, 20);
		contentPane.add(lblContrasenaAviso);
		
		passwordField = new JPasswordField();
		passwordField.setEchoChar('*');
		passwordField.setBounds(120, 156, 165, 26);
		contentPane.add(passwordField);
		
		chckbxMostrar = new JCheckBox("Mostrar");
		chckbxMostrar.setBounds(305, 186, 69, 23);
		contentPane.add(chckbxMostrar);
		
		chckbxRecordarSesion = new JCheckBox("Recordar inicio de sesión");
		chckbxRecordarSesion.setBounds(27, 219, 201, 23);
		contentPane.add(chckbxRecordarSesion);
		
		JLabel lblContrasena2 = new JLabel("Repetir contraseña");
		lblContrasena2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblContrasena2.setBounds(295, 161, 129, 15);
		contentPane.add(lblContrasena2);
		
		passwordField2 = new JPasswordField();
		passwordField2.setEchoChar('*');
		passwordField2.setBounds(434, 156, 149, 26);
		contentPane.add(passwordField2);
		
		lblCorreo = new JLabel("Correo");
		lblCorreo.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblCorreo.setBounds(352, 106, 58, 14);
		contentPane.add(lblCorreo);
		
		textCorreo = new JTextField();
		textCorreo.setColumns(10);
		textCorreo.setBounds(411, 101, 172, 26);
		contentPane.add(textCorreo);
		
		JLabel lblErrorCorreo = new JLabel("");
		lblErrorCorreo.setForeground(Color.RED);
		lblErrorCorreo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErrorCorreo.setBounds(351, 131, 232, 14);
		contentPane.add(lblErrorCorreo);
		
		// Clic boton volver
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reiniciarContenido();
				controlador.cambiarVentana(SignInUI.this, login);
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
				
				if (passwordField.getPassword().length > 7)  {
					contraseñaValida = true;
					lblContrasenaAviso.setForeground(Color.GRAY);
					
				} else {
					lblContrasenaAviso.setForeground(Color.RED);
				}

				revisarFormulario();
			}
		});
		
		// Al escribir en el campo repetir contraseña
		passwordField2.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				contraseñaValida2 = false;
				char[] passwordChars = passwordField2.getPassword();
				String contraseña = new String(passwordChars);
				
				if (contraseña.length() > 7)  {
					contraseñaValida2 = true;
				}
				revisarFormulario();
			}
		});
		
		// Al escribir en el campo correo
		textCorreo.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyReleased(KeyEvent e) {
		        correoValido = false;
		        String correo = textCorreo.getText().trim();

		        if (correo.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
		            correoValido = true;
		            lblErrorCorreo.setText(""); 
		            
		        } else {
		            lblErrorCorreo.setText("Formato de correo no válido");
		        }

		        revisarFormulario();
		    }
		});
			
		// Mostrar u ocultar contraseña
		chckbxMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    if (chckbxMostrar.isSelected()) {
			        passwordField.setEchoChar((char) 0); // Mostrar
			        passwordField2.setEchoChar((char) 0);
			        
			    } else {
			        passwordField.setEchoChar('*'); // Ocultar
			        passwordField2.setEchoChar('*');
			    }
			}
		});
		
		// Clic boton aceptar
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				char[] passwordChars = passwordField.getPassword();
				String contraseña = new String(passwordChars);
				
				char[] passwordChars2 = passwordField2.getPassword();
				String contraseña2 = new String(passwordChars2);
				
				if (!contraseña.equals(contraseña2)) {
					JOptionPane.showMessageDialog(
						    null,
						    "Las contraseñas no coinciden.",
						    "Advertencia",
						    JOptionPane.WARNING_MESSAGE
						);
				} else {
					// Crea el usuario e inicia sesión como el.
					try {
						usuario = new User(textNombre.getText(), contraseña, textCorreo.getText(), chckbxRecordarSesion.isSelected());
						usuario = modelo.agregarUsuario(usuario, chckbxRecordarSesion.isSelected() );	
						sesion.iniciarSesion(usuario, SignInUI.this);
						modelo.agregarUsuarioDefault();
						reiniciarContenido();
						
					} catch (MailException e1) {
						 JOptionPane.showMessageDialog(null, e1.getMessage(), "Correo no válido", JOptionPane.WARNING_MESSAGE);
						 
					} catch (SQLIntegrityConstraintViolationException e2) {
						JOptionPane.showMessageDialog(null, e2.getMessage(), "Correo no válido", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		
		// Al cerrar la ventana mediante la X
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				reiniciarContenido();
				controlador.cambiarVentana(SignInUI.this, login);
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
		passwordField2.setText("");
		textCorreo.setText("");
		chckbxRecordarSesion.setSelected(false);
		chckbxMostrar.setSelected(false);
        passwordField.setEchoChar('*');
        passwordField2.setEchoChar('*');
	}
		
	/**
	 * Revisa el formulario para asegurarse que los datos sean correctos.
	 */
	public void revisarFormulario() {
		if (contraseñaValida && contraseñaValida2 && nombreValido && correoValido) {		
			btnAceptar.setEnabled(true);				
		
		} else {
			btnAceptar.setEnabled(false);
		}
	}
}
