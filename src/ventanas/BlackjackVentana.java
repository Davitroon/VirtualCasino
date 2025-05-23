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
import logica.Blackjack;
import logica.Cliente;
import logica.Controlador;
import logica.Juego;
import logica.Modelo;
import java.awt.Color;

public class BlackjackVentana extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private Controlador controlador;
	private Modelo modelo;
	private Jugar jugar;
	private JLabel lblCartasCrupierList;
	private JLabel lblTusCartasList;
	private JLabel lblTusCartas;
	private JLabel lblCartasCrupier;
	private JButton btnPlantarse;
	private JButton btnPedir;
	
	private Cliente cliente;
	private Blackjack blackjack;
	
	private double apuesta;
	private JLabel lblApuestaActual;
	private boolean partidaTerminada;
	private JButton btnVolver;
	private JButton btnInfo;


	public BlackjackVentana(Controlador controlador, Modelo modelo, Jugar jugar, Cliente cliente, Juego juego, double apuesta) {
		setResizable(false);
		
		
		this.controlador = controlador;
		this.modelo = modelo;

		this.jugar = jugar;
		this.cliente = cliente;
		this.blackjack = (Blackjack) juego;
		
		this.apuesta = apuesta;
		setBounds(100, 100, 577, 442);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblBlackjack = new JLabel("Blackjack");
		lblBlackjack.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblBlackjack.setHorizontalAlignment(SwingConstants.CENTER);
		lblBlackjack.setBounds(48, 36, 473, 30);
		contentPane.add(lblBlackjack);
		
		lblCartasCrupier = new JLabel("lorem");
		lblCartasCrupier.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCartasCrupier.setBounds(48, 88, 473, 21);
		contentPane.add(lblCartasCrupier);
		
		lblTusCartas = new JLabel("lorem");
		lblTusCartas.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTusCartas.setBounds(48, 189, 473, 21);
		contentPane.add(lblTusCartas);
		
		btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(128, 128, 128));
		btnVolver.setBounds(102, 305, 108, 38);
		contentPane.add(btnVolver);
		
		lblCartasCrupierList = new JLabel("lorem");
		lblCartasCrupierList.setFont(new Font("VL Gothic", Font.PLAIN, 15));
		lblCartasCrupierList.setBounds(48, 120, 473, 30);
		contentPane.add(lblCartasCrupierList);
		
		lblTusCartasList = new JLabel("lorem");
		lblTusCartasList.setFont(new Font("VL Gothic", Font.PLAIN, 15));
		lblTusCartasList.setBounds(48, 221, 473, 30);
		contentPane.add(lblTusCartasList);
		
		btnPedir = new JButton("Pedir");
		btnPedir.setBackground(new Color(0, 128, 64));
		btnPedir.setBounds(338, 305, 108, 38);
		contentPane.add(btnPedir);
		
		btnPlantarse = new JButton("Plantarse");
		btnPlantarse.setBackground(new Color(255, 128, 128));
		btnPlantarse.setBounds(220, 305, 108, 38);
		contentPane.add(btnPlantarse);
		
		lblApuestaActual = new JLabel("lorem");
		lblApuestaActual.setHorizontalAlignment(SwingConstants.CENTER);
		lblApuestaActual.setFont(new Font("Tw Cen MT", Font.BOLD, 15));
		lblApuestaActual.setBounds(48, 273, 473, 21);
		contentPane.add(lblApuestaActual);
		
		btnInfo = new JButton("?");
		btnInfo.setBackground(new Color(128, 255, 255));
		btnInfo.setBounds(516, 305, 37, 38);
		contentPane.add(btnInfo);
		
		addWindowListener(new WindowAdapter() {
			// Al cerrar la ventana mediante la X
			@Override
			public void windowClosing(WindowEvent e) {
				avisoCerrar();
			}		
		});
		
		
		// Clic boton volver
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				avisoCerrar();	
			}
		});
		
		
		// Clic boton plantarse
		btnPlantarse.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        boolean clienteGana = controlador.blackjackPlantarse(blackjack, BlackjackVentana.this);
		        finJuego(clienteGana);
		    }
		});
		
		
		// Clic boton pedir
		btnPedir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (controlador.blackjackPedirCarta(blackjack)) {
					finJuego(false);
				}
				lblTusCartasList.setText("(" + blackjack.sumarCartas(blackjack.getCartasCliente()) + ") " + blackjack.mostrarCartas(false, "cliente"));

				if (blackjack.sumarCartas(blackjack.getCartasCliente()) == 21) finJuego(true);
			}
		});
		
		// Clic boton info
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        String mensaje = """
		                ¿Cómo jugar al Blackjack?

		                - El objetivo es sumar lo más cerca posible a 21 sin pasarte.
		                - Tú y el crupier recibís cartas al inicio.
		                - Puedes pedir más cartas (botón "Pedir") si crees que no te pasarás de 21.
		                - Si te pasas de 21, pierdes automáticamente.
		                - Cuando decidas plantarte (botón "Plantarse"), el crupier revelará sus cartas.
		                - Ganas si tienes más puntos que el crupier sin pasarte de 21.
		                - Si haces 21 con solo dos cartas (un As y una figura o un 10), ¡es un Blackjack!

		                Si ganas, se te sumará dinero a tu saldo. Si pierdes, ... bueno, la proxima vez será.
		                ¡Suerte y juega con cabeza!
		                """;

		        JOptionPane.showMessageDialog(null, mensaje, "Guía de Blackjack", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}
	
	
	/**
	 * Método para finalizar una partida de Blackjack, comparando la baraja del cliente y crupier y ajustando sus saldos en base a la apuesta.
	 * @param clienteGana Verdadero si el cliente ha ganado y falso en el caso contrario.
	 */
	public void finJuego(boolean clienteGana) {

	    double apuestaResultado = blackjack.jugar(apuesta);
	    boolean irMensajeFinal;
	    partidaTerminada = true;

	    controlador.actualizarSaldos(cliente, blackjack, apuestaResultado, false);

	    lblCartasCrupierList.setText("(" + blackjack.sumarCartas(blackjack.getCartasCrupier()) + ") " + blackjack.mostrarCartas(false, "crupier"));
	    lblTusCartasList.setText("(" + blackjack.sumarCartas(blackjack.getCartasCliente()) + ") " + blackjack.mostrarCartas(false, "cliente"));
	    lblCartasCrupier.setText(String.format("Baraja del crupier (%.2f$)", blackjack.getDinero()));
	    lblTusCartas.setText(String.format("Baraja de %s (%.2f$)", cliente.getNombre(), cliente.getSaldo()));

	    btnPedir.setEnabled(false);
	    btnPlantarse.setEnabled(false);

	    String estadoFin = controlador.blackjackEstadoFin(clienteGana, cliente, blackjack, apuestaResultado);

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
	                double apuestaNueva = controlador.alertaApuesta(cliente, blackjack);
	                apuesta = apuestaNueva;
	                iniciarJuego();
	                
	            } catch (ApuestaExcepcion ex) {
	                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	                irMensajeFinal = true; // Repetir el bucle hasta que ingrese una apuesta válida o cancele
	            }
	        }
	    } while (irMensajeFinal);
	}
	
	/**
	 * Método para iniciar una partida de Blackjack. Se reparten las cartas a cada jugador y se muestran. Si el jugador saca 21, gana automaticamente.
	 */
	public void iniciarJuego() {
		controlador.blackjackIniciar(blackjack);
		int barajaCrupier = blackjack.sumarCartas(blackjack.getCartasCrupier());
		int barajaCliente = blackjack.sumarCartas(blackjack.getCartasCliente());
		partidaTerminada = false;
		
		lblCartasCrupier.setText(String.format("Baraja del crupier (%.2f$)", blackjack.getDinero()));
		lblTusCartas.setText(String.format("Baraja de %s (%.2f$)", cliente.getNombre(), cliente.getSaldo()));		
		lblCartasCrupierList.setText("(" + (blackjack.getCartasCrupier().get(0) == 1 ? "11/1" : blackjack.getCartasCrupier().get(0)) + ") " + blackjack.mostrarCartas(true, "crupier"));
		lblTusCartasList.setText("(" + barajaCliente + ") " + blackjack.mostrarCartas(false, "cliente"));
		lblApuestaActual.setText(String.format("Apuesta actual: %.2f$", apuesta));
		
		btnPedir.setEnabled(true);
		btnPlantarse.setEnabled(true);	
		
		if (barajaCliente == 21) finJuego(true);
		if (barajaCrupier == 21) finJuego(false);
	}
	
	
	/**
	 * Actualizar la lista de cartas del crupier.
	 * @param cartas Sus cartas.
	 * @param suma La suma de sus cartas.
	 */
	public void actualizarCartasCrupier(String cartas, int suma) {
	    lblCartasCrupierList.setText(cartas + " (" + suma + ")");
	}
	
	
	/**
	 * Método que llamará al controlador para mostrar un mensaje de aviso al intentar cerrar la ventana.
	 */
	public void avisoCerrar() {
		if (!partidaTerminada) {
			if (controlador.avisoCerrarJuego(cliente, blackjack, apuesta)) {
				dispose();
				jugar.setVisible(true);
			}					
				
		} else {
			dispose();
			jugar.setVisible(true);
		}	
	}
}
