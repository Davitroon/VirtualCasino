package ventanas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Excepciones.ApuestaExcepcion;
import logica.Cliente;
import logica.Controlador;
import logica.Juego;
import logica.Modelo;
import logica.Tragaperras;
import java.awt.Color;

public class TragaperrasVentana extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private Controlador controlador;
	private Modelo modelo;
	private Jugar jugar;
	private JButton btnTirar;
	
	private Cliente cliente;
	private Tragaperras tragaperras;
	
	private double apuesta;
	private boolean partidaTerminada;
	private JLabel lblNum1;
	private JLabel lblNum2;
	private JLabel lblNum3;
	private JLabel lblApuestaActual;
	private JLabel lblCliente;
	private JLabel lblJuego;
	private JButton btnVolver;


	public TragaperrasVentana(Controlador controlador, Modelo modelo, Jugar jugar, Cliente cliente, Juego juego, double apuesta) {
		
		this.controlador = controlador;
		this.modelo = modelo;

		this.jugar = jugar;
		this.cliente = cliente;
		this.tragaperras = (Tragaperras) juego;
		
		this.apuesta = apuesta;
		
		setResizable(false);
		setBounds(100, 100, 523, 421);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTragaperras = new JLabel("Tragaperras");
		lblTragaperras.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblTragaperras.setHorizontalAlignment(SwingConstants.CENTER);
		lblTragaperras.setBounds(49, 56, 421, 30);
		contentPane.add(lblTragaperras);
		
		btnTirar = new JButton("Tirar");
		btnTirar.setBackground(new Color(0, 128, 64));
		btnTirar.setBounds(264, 319, 108, 38);
		contentPane.add(btnTirar);
		
		lblNum1 = new JLabel("0");
		lblNum1.setBackground(new Color(0, 64, 0));
		lblNum1.setFont(new Font("Cambria Math", Font.BOLD, 18));
		lblNum1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNum1.setBounds(153, 118, 62, 38);
		contentPane.add(lblNum1);
		
		lblNum2 = new JLabel("0");
		lblNum2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNum2.setFont(new Font("Cambria Math", Font.BOLD, 18));
		lblNum2.setBounds(225, 118, 62, 38);
		contentPane.add(lblNum2);
		
		lblNum3 = new JLabel("0");
		lblNum3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNum3.setFont(new Font("Cambria Math", Font.BOLD, 18));
		lblNum3.setBounds(297, 118, 62, 38);
		contentPane.add(lblNum3);
		
		lblApuestaActual = new JLabel("lorem");
		lblApuestaActual.setHorizontalAlignment(SwingConstants.CENTER);
		lblApuestaActual.setFont(new Font("Tw Cen MT", Font.BOLD, 15));
		lblApuestaActual.setBounds(10, 287, 489, 21);
		contentPane.add(lblApuestaActual);
		
		lblCliente = new JLabel("lorem");
		lblCliente.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCliente.setBounds(10, 224, 304, 21);
		contentPane.add(lblCliente);
		
		lblJuego = new JLabel("lorem");
		lblJuego.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblJuego.setBounds(10, 192, 304, 21);
		contentPane.add(lblJuego);
		
		btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(128, 128, 128));
		btnVolver.setBounds(132, 319, 108, 38);
		contentPane.add(btnVolver);
		
		JButton btnInfo = new JButton("?");
		btnInfo.setBackground(new Color(128, 255, 255));
		btnInfo.setBounds(462, 321, 37, 35);
		contentPane.add(btnInfo);
		
		addWindowListener(new WindowAdapter() {
			// Al cerrar la ventana mediante la X
			@Override
			public void windowClosing(WindowEvent e) {
				avisoCerrar();			
			}
		});		
		
		// Clic boton pedir
		btnTirar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tragaperras.generarNumeros();
				lblNum1.setText(String.valueOf(tragaperras.getNumeros()[0]));
				lblNum2.setText(String.valueOf(tragaperras.getNumeros()[1]));
				lblNum3.setText(String.valueOf(tragaperras.getNumeros()[2]));
				finJuego();
			}
		});
		
		// Clic boton volver
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				avisoCerrar();
			}
		});
			
		// Clic boton info
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Un String de 3 comillas permite dar formato al texto con saltos de linea y espacios.
		        String mensaje = """
		                ¿Cómo jugar a las Tragaperras?

		                - Primero realiza una apuesta con tu saldo disponible.
		                - Luego pulsa el botón "Tirar" para generar tres números al azar entre 1 y 9.
		                - Los números aparecerán en pantalla inmediatamente.

		                ¿Cómo se determina si ganas?
		                - Si no hay números repetidos, pierdes tu apuesta.
		                - Si hay dos números iguales, ganas 1.9 veces tu apuesta.
		                - Si los tres números son iguales, ganas 3.5 veces tu apuesta.
		                - Si los tres números son 7, ganas 6.5 veces tu apuesta.

		                Ten en cuenta que este juego es completamente aleatorio.
		                """;

		        JOptionPane.showMessageDialog(null, mensaje, "Guía de Tragaperras", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}
	
	
	/**
	 * Método para finalizar una partida de Tragaperras, ajustando los saldos en base al resultado.
	 */
	public void finJuego() {
	    double apuestaResultado = tragaperras.jugar(apuesta);
	    boolean irMensajeFinal;
	    partidaTerminada = true;

	    controlador.actualizarSaldos(cliente, tragaperras, apuestaResultado, false);
	    btnTirar.setEnabled(false);
	    lblCliente.setText(String.format("Saldo de %s: %.2f$", cliente.getNombre(), cliente.getSaldo()));
	    lblJuego.setText(String.format("Dinero del juego: %.2f$", tragaperras.getDinero()));

	    String estadoFin = controlador.tragaperrasEstadoFin(cliente, tragaperras, apuestaResultado);

	    do {
	        irMensajeFinal = false;
	        int eleccion = controlador.juegosEstadoFin(estadoFin);

	        if (eleccion == 0) {
	            this.dispose();
	            jugar.setVisible(true);
	            break;
	        }

	        if (eleccion == 1) {
	            try {
	                double apuestaNueva = controlador.alertaApuesta(cliente, tragaperras);
	                apuesta = apuestaNueva;
	                iniciarJuego();
	                
	            } catch (ApuestaExcepcion ex) {
	                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	                irMensajeFinal = true; // Reintentar
	            }
	        }
	    } while (irMensajeFinal);
	}
	
	
	/**
	 * Método para iniciar una partida de tragaperras, reiniciando los campos y actualizando los textos.
	 */
	public void iniciarJuego() {
		btnTirar.setEnabled(true);
		lblNum1.setText("0");
		lblNum2.setText("0");
		lblNum3.setText("0");
		lblApuestaActual.setText(String.format("Apuesta actual: %.2f$", apuesta));
		lblCliente.setText(String.format("Saldo de %s: %.2f$", cliente.getNombre(), cliente.getSaldo()));
		lblJuego.setText(String.format("Dinero del juego: %.2f$", tragaperras.getDinero()));
	}
	
	
	/**
	 * Método que llamará al controlador para mostrar un mensaje de aviso al intentar cerrar la ventana.
	 */
	public void avisoCerrar() {
		if (!partidaTerminada) {
			if (controlador.avisoCerrarJuego(cliente, tragaperras, apuesta)) {
				dispose();
				jugar.setVisible(true);
			}					
				
		} else {
			dispose();
			jugar.setVisible(true);
		}	
	}
}