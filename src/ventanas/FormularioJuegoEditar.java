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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import logica.Blackjack;
import logica.Controlador;
import logica.Modelo;
import logica.Tragaperras;

/**
 * Ventana para el formulario de editar juegos.
 * @author David
 * @since 3.0
 */
public class FormularioJuegoEditar extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	private Gestion gestion;
	private Controlador controlador;
	private JTextField textDinero;
	private boolean dineroValido;
	private JButton btnModificar;
	
	private Modelo modelo;
	

	private JLabel lblErrorDinero;
	private JComboBox comboTipo;
	private JTextField textId;
	private JLabel lblId;
	private JCheckBox chckbxActivo;
	

	/**
	 * Create the frame.
	 * @param controlador 
	 * @param gestion2 
	 */
	public FormularioJuegoEditar(Gestion gestion, Controlador controlador, Modelo modelo) {
		
		this.gestion = gestion;		this.controlador = controlador;
		this.modelo = modelo;
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	
		setBounds(100, 100, 440, 314);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEditarJuego = new JLabel("Editar juego", SwingConstants.CENTER);
		lblEditarJuego.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblEditarJuego.setBounds(6, 21, 412, 39);
		contentPane.add(lblEditarJuego);
		
		btnModificar = new JButton("Modificar");
		btnModificar.setBackground(new Color(128, 128, 255));
		btnModificar.setEnabled(false);
		btnModificar.setBounds(286, 228, 111, 32);
		contentPane.add(btnModificar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(128, 128, 128));
		btnVolver.setBounds(165, 228, 111, 32);
		contentPane.add(btnVolver);
		
		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTipo.setBounds(129, 84, 49, 14);
		contentPane.add(lblTipo);
		
		JLabel lblDinero = new JLabel("Dinero");
		lblDinero.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDinero.setBounds(40, 152, 49, 14);
		contentPane.add(lblDinero);
		
		textDinero = new JTextField();
		textDinero.setBounds(101, 143, 236, 32);
		contentPane.add(textDinero);
		textDinero.setColumns(10);
		
		lblErrorDinero = new JLabel("");
		lblErrorDinero.setForeground(new Color(255, 0, 0));
		lblErrorDinero.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErrorDinero.setBounds(40, 178, 297, 14);
		contentPane.add(lblErrorDinero);
		
		comboTipo = new JComboBox();
		comboTipo.setModel(new DefaultComboBoxModel(new String[] {"Blackjack", "Tragaperras"}));
		comboTipo.setBounds(172, 80, 111, 22);
		contentPane.add(comboTipo);
		
		lblId = new JLabel("ID");
		lblId.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblId.setBounds(40, 84, 26, 14);
		contentPane.add(lblId);
		
		textId = new JTextField();
		textId.setEnabled(false);
		textId.setEditable(false);
		textId.setBounds(64, 77, 38, 29);
		contentPane.add(textId);
		textId.setColumns(10);
		
		chckbxActivo = new JCheckBox("Activo");
		chckbxActivo.setBounds(304, 80, 89, 23);
		contentPane.add(chckbxActivo);
				
		// Al escribir en el campo saldo
		textDinero.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {			
				dineroValido = false;				
				String texto = textDinero.getText();
				
				if (controlador.validarDineroJuego(texto, lblErrorDinero)) {
					dineroValido = true;
				}
				
				revisarFormulario();
			}
		});		
		
		// Clic boton volver
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCampos();
				controlador.cambiarVentana(FormularioJuegoEditar.this, gestion);
			}
		});
			
		// Clic boton modificar
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				int id = Integer.parseInt(textId.getText());
				String tipo = String.valueOf(comboTipo.getSelectedItem());
				boolean activo = chckbxActivo.isSelected() ? true : false;
				Double dinero = Double.parseDouble(textDinero.getText());
				
				if (tipo.equalsIgnoreCase("Blackjack")) {
					modelo.modificarJuego(new Blackjack(id, tipo, activo, dinero));
				}
				
				if (tipo.equalsIgnoreCase("Tragaperras")) {
					modelo.modificarJuego(new Tragaperras(id, tipo, activo, dinero));
				}	
				
				limpiarCampos();
				controlador.cambiarVentana(FormularioJuegoEditar.this, gestion);
			}
		});		
		
		// Al cerrar la ventana mediante la X
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				limpiarCampos();
				controlador.cambiarVentana(FormularioJuegoEditar.this, gestion);
			}
		});
	}
	
	
	/**
	 * Método para rellenar los campos del formulario con los datos del juego que se vaya a modificar.
	 * @param id Id del juego a consultar por sus datos
	 * @since 3.0
	 */
	public void cargarJuegoOriginal(int id) {	    
		ResultSet rset = modelo.consultarDatoUnico("juegos", id);
	    
	    try {
		    textId.setText(String.valueOf(id));
		    
		    switch (rset.getString(2)) {
			    case "Blackjack":
			    	comboTipo.setSelectedIndex(0);
			    	break;
			    
			    case "Tragaperras":
			    	comboTipo.setSelectedIndex(1);
			    	break;
		    }
		    
			if (rset.getBoolean(3)) chckbxActivo.setSelected(true);
		    textDinero.setText( String.valueOf(rset.getString(4) ) ) ;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    
	    dineroValido = true;
	    revisarFormulario();
	}
	
	
	/**
	 * Método para limpiar todos los campos del formulario.
	 * @since 3.0
	 */
	public void limpiarCampos() {
		btnModificar.setEnabled(false);
		textDinero.setText("");
		comboTipo.setSelectedIndex(0);
		lblErrorDinero.setText("");
	}
	
	
	/**
	 * Método para revisar que el usuario haya rellenado todos los datos en el formulario.
	 * @since 3.0
	 */
	public void revisarFormulario() {	
		if (dineroValido) {
			btnModificar.setEnabled(true);
			return;
		}
		
		btnModificar.setEnabled(false);
	}
}
