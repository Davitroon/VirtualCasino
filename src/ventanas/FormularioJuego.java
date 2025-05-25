package ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
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
import logica.Validador;

/**
 * Ventana para el formulario de juegos.
 * @author David
 * @since 3.0
 */
public class FormularioJuego extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JTextField textDinero;
	
	private boolean dineroValido;
	private JButton btnAnadir;
	
	private JLabel lblErrorDinero;
	private JComboBox<Object> comboTipo;
	

	/**
	 * Constructor de la ventana formulario juego
	 * @param controlador 
	 * @param gestion2 
	 * @since 3.0
	 */
	public FormularioJuego(Gestion gestion, Controlador controlador, Modelo modelo, Validador validador) {
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	
		setBounds(100, 100, 414, 314);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAnadirJuego = new JLabel("Añadir juego", SwingConstants.CENTER);
		lblAnadirJuego.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblAnadirJuego.setBounds(10, 21, 387, 39);
		contentPane.add(lblAnadirJuego);
		
		btnAnadir = new JButton("Añadir");
		btnAnadir.setBackground(new Color(128, 128, 255));
		btnAnadir.setEnabled(false);
		btnAnadir.setBounds(270, 228, 111, 32);
		contentPane.add(btnAnadir);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(128, 128, 128));
		btnVolver.setBounds(20, 228, 111, 32);
		contentPane.add(btnVolver);
		
		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTipo.setBounds(36, 84, 49, 14);
		contentPane.add(lblTipo);
		
		JLabel lblDinero = new JLabel("Dinero");
		lblDinero.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDinero.setBounds(36, 146, 49, 14);
		contentPane.add(lblDinero);
		
		textDinero = new JTextField();
		textDinero.setBounds(97, 137, 182, 32);
		contentPane.add(textDinero);
		textDinero.setColumns(10);
		
		lblErrorDinero = new JLabel("");
		lblErrorDinero.setForeground(new Color(255, 0, 0));
		lblErrorDinero.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErrorDinero.setBounds(36, 172, 233, 14);
		contentPane.add(lblErrorDinero);
		
		comboTipo = new JComboBox<Object>();
		comboTipo.setModel(new DefaultComboBoxModel<Object>(new String[] {"Blackjack", "SlotMachine"}));
		comboTipo.setBounds(97, 80, 111, 22);
		contentPane.add(comboTipo);		
		
		// Al escribir en el campo saldo
		textDinero.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {			
				dineroValido = false;				
				String texto = textDinero.getText();
				
				if (validador.validarDineroJuego(texto, lblErrorDinero)) {
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
				
				if (tipo.equalsIgnoreCase("SlotMachine")) {
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
	 * Método para limpiar todos los campos del formulario.
	 * @since 3.0
	 */
	public void limpiarCampos() {
		btnAnadir.setEnabled(false);
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
			btnAnadir.setEnabled(true);
			return;
		}
		
		btnAnadir.setEnabled(false);
	}
}
