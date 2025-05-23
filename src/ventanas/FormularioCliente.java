package ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import logica.Cliente;
import logica.Controlador;
import logica.Modelo;

/**
 * Ventana para el formulario de clientes.
 * @author David
 * @since 3.0
 */
public class FormularioCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textNombre;
	private JTextField textEdad;
	private JTextField textSaldo;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	private Gestion gestion;
	private Controlador controlador;
	private Modelo modelo;
	
	private boolean nombreValido;
	private boolean edadValida;
	private boolean saldoValido;
	private JRadioButton rdbtnMasculino;
	private JRadioButton rdbtnFemenino;
	private JRadioButton rdbtnOtro;
	private JButton btnAnadir;
	

	private JLabel lblErrorNombre;
	private JLabel lblErrorEdad;
	private JLabel lblErrorSaldo;
	

	/**
	 * Create the frame.
	 * @param controlador 
	 * @param modelo 
	 * @param gestion2 
	 */
	public FormularioCliente(Gestion gestion, Controlador controlador, Modelo modelo) {
		
		this.gestion = gestion;		this.controlador = controlador;
		this.modelo = modelo;
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	
		setBounds(100, 100, 496, 397);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAnadirCliente = new JLabel("Añadir cliente", SwingConstants.CENTER);
		lblAnadirCliente.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblAnadirCliente.setBounds(10, 21, 460, 39);
		contentPane.add(lblAnadirCliente);
		
		textNombre = new JTextField();
		textNombre.setBounds(122, 84, 321, 32);
		contentPane.add(textNombre);
		textNombre.setColumns(10);
		
		textEdad = new JTextField();
		textEdad.setBounds(105, 149, 129, 32);
		contentPane.add(textEdad);
		textEdad.setColumns(10);
		
		rdbtnMasculino = new JRadioButton("Masculino");
		buttonGroup.add(rdbtnMasculino);
		rdbtnMasculino.setBounds(100, 246, 99, 23);
		contentPane.add(rdbtnMasculino);
		
		rdbtnFemenino = new JRadioButton("Femenino");
		buttonGroup.add(rdbtnFemenino);
		rdbtnFemenino.setBounds(211, 246, 86, 23);
		contentPane.add(rdbtnFemenino);
		
		rdbtnOtro = new JRadioButton("Otro");
		buttonGroup.add(rdbtnOtro);
		rdbtnOtro.setBounds(309, 246, 64, 23);
		contentPane.add(rdbtnOtro);
		
		textSaldo = new JTextField();
		textSaldo.setBounds(290, 149, 153, 32);
		contentPane.add(textSaldo);
		textSaldo.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNombre.setLabelFor(textNombre);
		lblNombre.setBounds(52, 92, 64, 17);
		contentPane.add(lblNombre);
		
		JLabel lblEdad = new JLabel("Edad");
		lblEdad.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEdad.setLabelFor(textEdad);
		lblEdad.setBounds(52, 157, 64, 17);
		contentPane.add(lblEdad);
		
		JLabel lblSaldo = new JLabel("Saldo");
		lblSaldo.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSaldo.setLabelFor(textSaldo);
		lblSaldo.setBounds(244, 157, 53, 17);
		contentPane.add(lblSaldo);
		
		JLabel lblGenero = new JLabel("Género", SwingConstants.CENTER);
		lblGenero.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblGenero.setBounds(34, 220, 409, 14);
		contentPane.add(lblGenero);
		
		btnAnadir = new JButton("Añadir");
		btnAnadir.setBackground(new Color(128, 128, 255));
		btnAnadir.setEnabled(false);
		btnAnadir.setBounds(359, 295, 111, 32);
		contentPane.add(btnAnadir);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(128, 128, 128));
		btnVolver.setBounds(230, 295, 111, 32);
		contentPane.add(btnVolver);
		
		lblErrorNombre = new JLabel("");
		lblErrorNombre.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lblErrorNombre.setForeground(new Color(255, 0, 0));
		lblErrorNombre.setBounds(54, 121, 389, 14);
		contentPane.add(lblErrorNombre);
		
		lblErrorEdad = new JLabel("");
		lblErrorEdad.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErrorEdad.setForeground(new Color(255, 0, 0));
		lblErrorEdad.setBounds(52, 186, 182, 14);
		contentPane.add(lblErrorEdad);
		
		lblErrorSaldo = new JLabel("");
		lblErrorSaldo.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lblErrorSaldo.setForeground(new Color(255, 0, 0));
		lblErrorSaldo.setBounds(244, 186, 199, 17);
		contentPane.add(lblErrorSaldo);		
		
		// Al escribir en el campo nombre
		textNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {			
				nombreValido = false;
				String texto = textNombre.getText();
				
				if (controlador.validarNombreCliente(texto, lblErrorNombre)) {
					nombreValido = true;
				}
				
				revisarFormulario();
			}
		});		
		
		// Al escribir en el campo edad
		textEdad.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {		
				edadValida = false;
				String texto = textEdad.getText();
				
				if (controlador.validarEdadCliente(texto, lblErrorEdad)) {
					edadValida = true;
				}
				
				revisarFormulario();
			}
		});	
				
		// Al escribir en el campo saldo
		textSaldo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {			
				saldoValido = false;				
				String texto = textSaldo.getText();
				
				if (controlador.validarSaldoCliente(texto, lblErrorSaldo)) {
					saldoValido = true;
				}
				
				revisarFormulario();
			}
		});		
		
		// Elegir genero masculino
		rdbtnMasculino.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				revisarFormulario();
			}
		});		
		
		// Elegir genero femenino
		rdbtnFemenino.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				revisarFormulario();
			}
		});		
		
		// Elegir genero otro
		rdbtnOtro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				revisarFormulario();
			}
		});		
		
		// Clic boton añadir
		btnAnadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = textNombre.getText();
				int edad = Integer.parseInt(textEdad.getText());
				char genero = obtenerGenero();
				double saldo = Double.parseDouble(textSaldo.getText());
				
				Cliente cliente = new Cliente(nombre, edad, genero, saldo);
				modelo.agregarCliente(cliente);
				
				limpiarCampos();
				controlador.cambiarVentana(FormularioCliente.this, gestion);
			}
		});		
		
		// Clic boton volver
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCampos();
				controlador.cambiarVentana(FormularioCliente.this, gestion);
			}
		});		
		
		// Al cerrar la ventana mediante la X
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				limpiarCampos();
				controlador.cambiarVentana(FormularioCliente.this, gestion);
			}
		});
	}
	
	
	/**
	 * Método para limpiar todos los campos del formulario.
	 * @since 3.0
	 */
	public void limpiarCampos() {
		btnAnadir.setEnabled(false);
		buttonGroup.clearSelection();
		textNombre.setText("");
		textEdad.setText("");
		textSaldo.setText("");
		lblErrorNombre.setText("");
		lblErrorEdad.setText("");
		lblErrorSaldo.setText("");
	}
	
	
	/**
	 * Método para obtener el género en base al radio button seleccionado.
	 * @return Género en formato char.
	 * @since 3.0
	 */
	public char obtenerGenero() {
		if (rdbtnMasculino.isSelected()) return 'M';
		if (rdbtnFemenino.isSelected()) return 'F';
		if (rdbtnOtro.isSelected()) return 'O';
		return 0;
	}
	
	
	/**
	 * Método para revisar que el usuario haya rellenado todos los datos en el formulario.
	 * @since 3.0
	 */
	public void revisarFormulario() {
		
		if (edadValida && saldoValido && nombreValido && obtenerGenero() != 0) {
			btnAnadir.setEnabled(true);

		} else {
			btnAnadir.setEnabled(false);
		}	
	}
}
