package ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import logica.Controlador;
import logica.Modelo;
import logica.Usuario;

/**
 * Ventana donde se mostrará información del usuario con el que se ha iniciado sesión.
 */
public class Perfil extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblUsuario;
	private JLabel lblUltimoAcceso;
	private JCheckBox chckbxRecordarSesion;
	private JLabel lblCorreo;
	private Usuario usuario;
	private JButton btBorrarUsuario;

	/**
	 * Create the frame.
	 */
	public Perfil(MenuPrincipal menu, Controlador controlador, Modelo modelo) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 542, 390);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPerfil = new JLabel("Perfil", SwingConstants.CENTER);
		lblPerfil.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblPerfil.setBounds(10, 24, 506, 59);
		contentPane.add(lblPerfil);
		
		lblUsuario = new JLabel("Registrado como:");
		lblUsuario.setBounds(72, 95, 433, 24);
		contentPane.add(lblUsuario);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(Color.GRAY);
		btnVolver.setBounds(29, 293, 103, 31);
		contentPane.add(btnVolver);
		
		JButton btnCerrarSesion = new JButton("CerrarSesion");
		btnCerrarSesion.setForeground(Color.BLACK);
		btnCerrarSesion.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnCerrarSesion.setBackground(new Color(242, 77, 77));
		btnCerrarSesion.setBounds(363, 291, 142, 32);
		contentPane.add(btnCerrarSesion);
		
		lblCorreo = new JLabel("Correo:");
		lblCorreo.setBounds(72, 131, 433, 24);
		contentPane.add(lblCorreo);
		
		lblUltimoAcceso = new JLabel("Último acceso:");
		lblUltimoAcceso.setBounds(72, 167, 433, 24);
		contentPane.add(lblUltimoAcceso);
		
		chckbxRecordarSesion = new JCheckBox("Recordar sesión");
		chckbxRecordarSesion.setBounds(72, 198, 433, 23);
		contentPane.add(chckbxRecordarSesion);
		
		btBorrarUsuario = new JButton("Borrar usuario");
		btBorrarUsuario.setForeground(Color.BLACK);
		btBorrarUsuario.setFont(new Font("SansSerif", Font.BOLD, 12));
		btBorrarUsuario.setBackground(new Color(242, 77, 77));
		btBorrarUsuario.setBounds(363, 248, 142, 32);
		contentPane.add(btBorrarUsuario);
		
		// Clic boton volver
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlador.cambiarVentana(Perfil.this, menu);
			}
		});		
		
		// Al cerrar la ventana mediante la X
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controlador.cambiarVentana(Perfil.this, menu);
			}
		});
		
		// Boton cerrar sesión
		btnCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlador.cambiarVentana(Perfil.this, menu.getConectarse());
				modelo.actualizarUltimoAcceso(usuario.getNombre());
			}
		});
		
		// Alternar recordar inicio sesion
		chckbxRecordarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				modelo.alternarRecordarSesion(usuario.getNombre(), chckbxRecordarSesion.isSelected());
			}
		});
		
		// Boton borrar usuario
		btBorrarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int respuesta = JOptionPane.showConfirmDialog(
					    null,
					    "¿Estás seguro de que quieres borrar este usuario?",
					    "Confirmación",
					    JOptionPane.YES_NO_OPTION,
					    JOptionPane.QUESTION_MESSAGE
					);
				
				if (respuesta == JOptionPane.YES_OPTION) {
					controlador.cambiarVentana(Perfil.this, menu.getConectarse());
					modelo.borrarDato(usuario.getId(), "users");
				}
			}
		});
	}
	
	/**
	 * Cargar los datos del usuario con el que se haya iniciado sesión.
	 * @param usuario Usuario actual.
	 */
	public void actualizarDatos(Usuario usuario) {
		this.usuario = usuario;
		lblUsuario.setText("Registrado como: " + usuario.getNombre());
		lblCorreo.setText("Correo asociado: " + usuario.getCorreo());
		lblUltimoAcceso.setText("Último acceso: " + usuario.getUltimoAcceso());
		chckbxRecordarSesion.setSelected(usuario.isRecordarSesion());
	}

}
