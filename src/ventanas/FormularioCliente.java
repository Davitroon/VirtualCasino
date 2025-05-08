package ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.Cliente;
import logica.Controlador;
import logica.Modelo;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
		setBounds(100, 100, 438, 376);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAnadirCliente = new JLabel("Añadir cliente", SwingConstants.CENTER);
		lblAnadirCliente.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAnadirCliente.setBounds(10, 11, 404, 39);
		contentPane.add(lblAnadirCliente);
		
		textNombre = new JTextField();
		textNombre.setBounds(126, 81, 249, 20);
		contentPane.add(textNombre);
		textNombre.setColumns(10);
		
		textEdad = new JTextField();
		textEdad.setBounds(126, 140, 79, 20);
		contentPane.add(textEdad);
		textEdad.setColumns(10);
		
		rdbtnMasculino = new JRadioButton("Masculino");
		buttonGroup.add(rdbtnMasculino);
		rdbtnMasculino.setBounds(76, 216, 99, 23);
		contentPane.add(rdbtnMasculino);
		
		rdbtnFemenino = new JRadioButton("Femenino");
		buttonGroup.add(rdbtnFemenino);
		rdbtnFemenino.setBounds(177, 216, 86, 23);
		contentPane.add(rdbtnFemenino);
		
		rdbtnOtro = new JRadioButton("Otro");
		buttonGroup.add(rdbtnOtro);
		rdbtnOtro.setBounds(275, 216, 64, 23);
		contentPane.add(rdbtnOtro);
		
		textSaldo = new JTextField();
		textSaldo.setBounds(279, 140, 96, 20);
		contentPane.add(textSaldo);
		textSaldo.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setLabelFor(textNombre);
		lblNombre.setBounds(52, 81, 64, 17);
		contentPane.add(lblNombre);
		
		JLabel lblEdad = new JLabel("Edad");
		lblEdad.setLabelFor(textEdad);
		lblEdad.setBounds(52, 142, 64, 17);
		contentPane.add(lblEdad);
		
		JLabel lblSaldo = new JLabel("Saldo");
		lblSaldo.setLabelFor(textSaldo);
		lblSaldo.setBounds(226, 142, 53, 17);
		contentPane.add(lblSaldo);
		
		JLabel lblGenero = new JLabel("Género", SwingConstants.CENTER);
		lblGenero.setBounds(40, 195, 341, 14);
		contentPane.add(lblGenero);
		
		btnAnadir = new JButton("Añadir");
		btnAnadir.setEnabled(false);
		btnAnadir.setBounds(282, 283, 111, 32);
		contentPane.add(btnAnadir);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(152, 283, 111, 32);
		contentPane.add(btnVolver);
		
		lblErrorNombre = new JLabel("");
		lblErrorNombre.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lblErrorNombre.setForeground(new Color(255, 0, 0));
		lblErrorNombre.setBounds(54, 109, 321, 14);
		contentPane.add(lblErrorNombre);
		
		lblErrorEdad = new JLabel("");
		lblErrorEdad.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lblErrorEdad.setForeground(new Color(255, 0, 0));
		lblErrorEdad.setBounds(52, 170, 153, 14);
		contentPane.add(lblErrorEdad);
		
		lblErrorSaldo = new JLabel("");
		lblErrorSaldo.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lblErrorSaldo.setForeground(new Color(255, 0, 0));
		lblErrorSaldo.setBounds(215, 170, 160, 14);
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
		
		
		// Elegir genero masculino
		rdbtnFemenino.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				revisarFormulario();
			}
		});
		
		
		// Elegir genero masculino
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
				modelo.agregarDato("juegos", cliente);
				
				limpiarCampos();
				controlador.cerrarVentana(FormularioCliente.this, gestion, true);
			}
		});
		
		
		// Clic boton volver
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCampos();
				controlador.cerrarVentana(FormularioCliente.this, gestion, true);
			}
		});
		
		
		// Al cerrar la ventana mediante la X
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				limpiarCampos();
				controlador.cerrarVentana(FormularioCliente.this, gestion, true);
			}
		});
	}
	
	
	public void revisarFormulario() {
		
		if (edadValida && saldoValido && nombreValido && obtenerGenero() != 0) {
			btnAnadir.setEnabled(true);

		} else {
			btnAnadir.setEnabled(false);
		}	
	}
	
	
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
	
	
	public char obtenerGenero() {
		if (rdbtnMasculino.isSelected()) return 'M';
		if (rdbtnFemenino.isSelected()) return 'F';
		if (rdbtnOtro.isSelected()) return 'O';
		return 0;
	}
}
