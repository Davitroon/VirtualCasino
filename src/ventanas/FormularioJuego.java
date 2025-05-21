package ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.*;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

/**
 * Ventana para el formulario de juegos.
 * @author David
 * @since 3.0
 */
public class FormularioJuego extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	private Gestion gestion;
	private Controlador controlador;
	private JTextField textDinero;
	
	private boolean dineroValido;
	private JButton btnAnadir;
	
	private Modelo modelo;
	

	private JLabel lblErrorDinero;
	private JComboBox comboTipo;
	

	/**
	 * Constructor de la ventana formulario juego
	 * @param controlador 
	 * @param gestion2 
	 * @since 3.0
	 */
	public FormularioJuego(Gestion gestion, Controlador controlador, Modelo modelo) {
		
		this.gestion = gestion;		this.controlador = controlador;
		this.modelo = modelo;
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	
		setBounds(100, 100, 440, 314);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAnadirJuego = new JLabel("Añadir juego", SwingConstants.CENTER);
		lblAnadirJuego.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAnadirJuego.setBounds(10, 11, 387, 39);
		contentPane.add(lblAnadirJuego);
		
		btnAnadir = new JButton("Añadir");
		btnAnadir.setEnabled(false);
		btnAnadir.setBounds(286, 228, 111, 32);
		contentPane.add(btnAnadir);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(165, 228, 111, 32);
		contentPane.add(btnVolver);
		
		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setBounds(40, 84, 49, 14);
		contentPane.add(lblTipo);
		
		JLabel lblDinero = new JLabel("Dinero");
		lblDinero.setBounds(215, 84, 49, 14);
		contentPane.add(lblDinero);
		
		textDinero = new JTextField();
		textDinero.setBounds(215, 109, 182, 20);
		contentPane.add(textDinero);
		textDinero.setColumns(10);
		
		lblErrorDinero = new JLabel("");
		lblErrorDinero.setForeground(new Color(255, 0, 0));
		lblErrorDinero.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lblErrorDinero.setBounds(215, 137, 182, 14);
		contentPane.add(lblErrorDinero);
		
		comboTipo = new JComboBox();
		comboTipo.setModel(new DefaultComboBoxModel(new String[] {"Blackjack", "Tragaperras"}));
		comboTipo.setBounds(40, 108, 111, 22);
		contentPane.add(comboTipo);
		
		
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
				controlador.cambiarVentana(FormularioJuego.this, gestion);
			}
		});
		
		
		// Clic boton añadir
		btnAnadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Double dinero = Double.parseDouble(textDinero.getText());
				String tipo = String.valueOf(comboTipo.getSelectedItem());
				
				if (tipo.equalsIgnoreCase("Blackjack")) {
					Blackjack blackjack = new Blackjack(dinero);
					modelo.agregarJuego(blackjack);
				}
				
				if (tipo.equalsIgnoreCase("Tragaperras")) {
					Tragaperras tragaperras = new Tragaperras(dinero);
					modelo.agregarJuego(tragaperras);
				}		
				
				limpiarCampos();
				controlador.cambiarVentana(FormularioJuego.this, gestion);
			}
		});
		
		
		// Al cerrar la ventana mediante la X
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				limpiarCampos();
				controlador.cambiarVentana(FormularioJuego.this, gestion);
			}
		});
	}
	
	
	/**
	 * Método para revisar que el usuario haya rellenado todos los datos en el formulario.
	 * @since 3.0
	 */
	public void revisarFormulario() {
		
		if (dineroValido) {
			btnAnadir.setEnabled(true);
			return;
		}
		
		btnAnadir.setEnabled(false);
	}
	
	
	/**
	 * Método para limpiar todos los campos del formulario.
	 * @since 3.0
	 */
	public void limpiarCampos() {
		btnAnadir.setEnabled(false);
		textDinero.setText("");
		comboTipo.setSelectedIndex(0);
		lblErrorDinero.setText("");
	}
}
