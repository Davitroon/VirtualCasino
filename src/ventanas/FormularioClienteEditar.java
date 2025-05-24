package ventanas;

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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import logica.Validador;

/**
 * Ventana para el formulario de editar clientes.
 * @author David
 * @since 3.0
 */
public class FormularioClienteEditar extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textNombre;
	private JTextField textEdad;
	private JTextField textSaldo;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	private Modelo modelo;
	
	private boolean nombreValido;
	private boolean edadValida;
	private boolean saldoValido;
	private JRadioButton rdbtnMasculino;
	private JRadioButton rdbtnFemenino;
	private JRadioButton rdbtnOtro;
	private JButton btnModificar;
	

	private JLabel lblErrorNombre;
	private JLabel lblErrorEdad;
	private JLabel lblErrorSaldo;
	private JTextField textId;
	private JCheckBox chckbxActivo;
	

	/**
	 * Create the frame.
	 * @param controlador 
	 * @param modelo 
	 * @param gestion2 
	 */
	public FormularioClienteEditar(Gestion gestion, Controlador controlador, Modelo modelo, Validador validador) {
		
		this.modelo = modelo;
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	
		setBounds(100, 100, 499, 403);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEditarCliente = new JLabel("Editar cliente", SwingConstants.CENTER);
		lblEditarCliente.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblEditarCliente.setBounds(10, 25, 463, 39);
		contentPane.add(lblEditarCliente);
		
		textNombre = new JTextField();
		textNombre.setBounds(189, 100, 167, 32);
		contentPane.add(textNombre);
		textNombre.setColumns(10);
		
		textEdad = new JTextField();
		textEdad.setBounds(76, 165, 133, 32);
		contentPane.add(textEdad);
		textEdad.setColumns(10);
		
		rdbtnMasculino = new JRadioButton("Masculino");
		buttonGroup.add(rdbtnMasculino);
		rdbtnMasculino.setBounds(110, 246, 99, 23);
		contentPane.add(rdbtnMasculino);
		
		rdbtnFemenino = new JRadioButton("Femenino");
		buttonGroup.add(rdbtnFemenino);
		rdbtnFemenino.setBounds(214, 246, 86, 23);
		contentPane.add(rdbtnFemenino);
		
		rdbtnOtro = new JRadioButton("Otro");
		buttonGroup.add(rdbtnOtro);
		rdbtnOtro.setBounds(302, 246, 64, 23);
		contentPane.add(rdbtnOtro);
		
		textSaldo = new JTextField();
		textSaldo.setBounds(280, 165, 151, 32);
		contentPane.add(textSaldo);
		textSaldo.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNombre.setLabelFor(textNombre);
		lblNombre.setBounds(126, 107, 64, 17);
		contentPane.add(lblNombre);
		
		JLabel lblEdad = new JLabel("Edad");
		lblEdad.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEdad.setLabelFor(textEdad);
		lblEdad.setBounds(37, 172, 64, 17);
		contentPane.add(lblEdad);
		
		JLabel lblSaldo = new JLabel("Saldo");
		lblSaldo.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSaldo.setLabelFor(textSaldo);
		lblSaldo.setBounds(229, 172, 36, 17);
		contentPane.add(lblSaldo);
		
		JLabel lblGenero = new JLabel("Género", SwingConstants.CENTER);
		lblGenero.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblGenero.setBounds(52, 225, 379, 14);
		contentPane.add(lblGenero);
		
		btnModificar = new JButton("Modificar");
		btnModificar.setBackground(new Color(128, 128, 255));
		btnModificar.setBounds(333, 307, 111, 32);
		contentPane.add(btnModificar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(128, 128, 128));
		btnVolver.setBounds(25, 307, 111, 32);
		contentPane.add(btnVolver);
		
		lblErrorNombre = new JLabel("");
		lblErrorNombre.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErrorNombre.setForeground(new Color(255, 0, 0));
		lblErrorNombre.setBounds(126, 134, 267, 14);
		contentPane.add(lblErrorNombre);
		
		lblErrorEdad = new JLabel("");
		lblErrorEdad.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErrorEdad.setForeground(new Color(255, 0, 0));
		lblErrorEdad.setBounds(37, 200, 172, 14);
		contentPane.add(lblErrorEdad);
		
		lblErrorSaldo = new JLabel("");
		lblErrorSaldo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErrorSaldo.setForeground(new Color(255, 0, 0));
		lblErrorSaldo.setBounds(236, 200, 195, 14);
		contentPane.add(lblErrorSaldo);
		
		JLabel lblId = new JLabel("ID");
		lblId.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblId.setBounds(37, 108, 26, 14);
		contentPane.add(lblId);
		
		textId = new JTextField();
		textId.setEnabled(false);
		textId.setEditable(false);
		textId.setBounds(57, 100, 44, 32);
		contentPane.add(textId);
		textId.setColumns(10);
		
		chckbxActivo = new JCheckBox("Activo");
		chckbxActivo.setBounds(368, 105, 76, 23);
		contentPane.add(chckbxActivo);
				
		// Al escribir en el campo nombre
		textNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {			
				nombreValido = false;
				String texto = textNombre.getText();
				
				if (validador.validarNombreCliente(texto, lblErrorNombre)) {
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
				
				if (validador.validarEdadCliente(texto, lblErrorEdad)) {
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
				
				if (validador.validarSaldoCliente(texto, lblErrorSaldo)) {
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
		
		// Clic boton modificar
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = textNombre.getText();
				int edad = Integer.parseInt(textEdad.getText());
				char genero = obtenerGenero();
				double saldo = Double.parseDouble(textSaldo.getText());
				int id = Integer.parseInt(textId.getText());
				boolean activo = chckbxActivo.isSelected() ? true : false;
				
				modelo.modificarCliente(new Cliente (nombre, edad, genero, saldo, id, activo));
				
				limpiarCampos();
				controlador.cambiarVentana(FormularioClienteEditar.this, gestion);
			}
		});		
		
		// Clic boton volver
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCampos();
				controlador.cambiarVentana(FormularioClienteEditar.this, gestion);
			}
		});		
		
		// Al cerrar la ventana mediante la X
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				limpiarCampos();
				controlador.cambiarVentana(FormularioClienteEditar.this, gestion);
			}
		});
	}
	
	
	/**
	 * Método para rellenar los campos del formulario con los datos del cliente que se vaya a modificar.
	 * @param id Id del cliente a consultar por sus datos
	 * @since 3.0
	 */
	public void cargarClienteOriginal(int id) {	    

		ResultSet rset = modelo.consultarDatoUnico("clientes", id);
	    String genero = "";
	    
	    try {
		    textId.setText(String.valueOf(id));
		    textNombre.setText(rset.getString(2)) ;
		    textEdad.setText( String.valueOf(rset.getString(3) ) ) ;
		    genero =  rset.getString(4);
			if (rset.getBoolean(5) ) chckbxActivo.setSelected(true);
		    textSaldo.setText( String.valueOf(rset.getString(6) ) ) ;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	   
	    
	    switch ( genero ) {
		    case "M":
		    	rdbtnMasculino.doClick();
		    	break;
		    case "F":
		    	rdbtnFemenino.doClick();
		    	break;
		    case "O":
		    	rdbtnOtro.doClick();
		    	break;
	    }
	    
	    edadValida = true;
	    saldoValido = true;
	    nombreValido = true;
	    revisarFormulario();
	}
	
	
	/**
	 * Método para limpiar todos los campos del formulario.
	 * @since 3.0
	 */
	public void limpiarCampos() {
		btnModificar.setEnabled(false);
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
			btnModificar.setEnabled(true);

		} else {
			btnModificar.setEnabled(false);
		}	
	}
}
