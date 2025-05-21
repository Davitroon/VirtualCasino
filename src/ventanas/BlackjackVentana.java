package ventanas;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.Blackjack;
import logica.Cliente;
import logica.Controlador;
import logica.Modelo;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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


	public BlackjackVentana(Controlador controlador, Modelo modelo, Jugar jugar, Cliente cliente, Blackjack blackjack, double apuesta) {
		
		this.controlador = controlador;
		this.modelo = modelo;

		this.jugar = jugar;
		this.cliente = cliente;
		this.blackjack = blackjack;
		
		this.apuesta = apuesta;
		
		setResizable(false);
		setBounds(100, 100, 577, 442);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblBlackjack = new JLabel("Blackjack");
		lblBlackjack.setBounds(48, 11, 473, 30);
		contentPane.add(lblBlackjack);
		
		lblCartasCrupier = new JLabel("");
		lblCartasCrupier.setBounds(48, 65, 142, 14);
		contentPane.add(lblCartasCrupier);
		
		lblTusCartas = new JLabel("Tus cartas");
		lblTusCartas.setBounds(48, 200, 142, 14);
		contentPane.add(lblTusCartas);
		
		lblCartasCrupierList = new JLabel("");
		lblCartasCrupierList.setBounds(48, 90, 473, 30);
		contentPane.add(lblCartasCrupierList);
		
		lblTusCartasList = new JLabel("");
		lblTusCartasList.setBounds(48, 225, 473, 30);
		contentPane.add(lblTusCartasList);
		
		btnPedir = new JButton("Pedir");
		btnPedir.setBounds(297, 318, 108, 38);
		contentPane.add(btnPedir);
		
		btnPlantarse = new JButton("Plantarse");
		btnPlantarse.setBounds(150, 318, 108, 38);
		contentPane.add(btnPlantarse);
		
		JLabel lblResultado = new JLabel("");
		lblResultado.setBounds(48, 151, 473, 38);
		contentPane.add(lblResultado);
		
		lblApuestaActual = new JLabel("");
		lblApuestaActual.setBounds(48, 266, 179, 14);
		contentPane.add(lblApuestaActual);
		
		addWindowListener(new WindowAdapter() {
			// Al cerrar la ventana mediante la X
			@Override
			public void windowClosing(WindowEvent e) {
				if (!partidaTerminada) {
					int respuesta = JOptionPane.showConfirmDialog(
						    null,
						    "¿Estás seguro que quieres salir? Contará como que has perdido la partida",
						    "Confirmación",
						    JOptionPane.YES_NO_OPTION,
						    JOptionPane.QUESTION_MESSAGE
						);

						if (respuesta == JOptionPane.YES_OPTION) {
							cliente.setSaldo(cliente.getSaldo() - apuesta);
							modelo.modificarSaldoCliente(cliente);
							
							blackjack.setDinero(blackjack.getDinero() + apuesta);
							modelo.modificarDineroJuego(blackjack);
							dispose();
							jugar.setVisible(true);
						}
						
				} else {
					dispose();
					jugar.setVisible(true);
				}			
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
				lblTusCartasList.setText(blackjack.mostrarCartas(false, "cliente") + " (" + blackjack.sumarCartas(blackjack.getCartasCliente()) + ")");

				if (blackjack.sumarCartas(blackjack.getCartasCliente()) == 21) finJuego(true);
			}
		});
	}
	
	
	/**
	 * Método para finalizar una partida de Blackjack, comparando la baraja del cliente y crupier y ajustando sus saldos en base a la apuesta.
	 * @param clienteGana Verdadero si el cliente ha ganado y falso en el caso contrario.
	 */
	public void finJuego(boolean clienteGana) {
		String[] opciones = {"Volver", "Apostar de nuevo"};
		double apuestaResultado = blackjack.jugar(apuesta);
		boolean irMensajeFinal;
		partidaTerminada = true;
		
		controlador.actualizarSaldos(cliente, blackjack, apuestaResultado);
		
		lblCartasCrupierList.setText(blackjack.mostrarCartas(false, "crupier") + " (" + blackjack.sumarCartas(blackjack.getCartasCrupier()) + ")");
		lblTusCartasList.setText(blackjack.mostrarCartas(false, "cliente") + " (" + blackjack.sumarCartas(blackjack.getCartasCliente()) + ")");
		lblCartasCrupier.setText("Baraja crupier (" + blackjack.getDinero() + "$)");
		lblTusCartas.setText("Baraja " + cliente.getNombre() + "(" + cliente.getSaldo() + "$)");
		
		btnPedir.setEnabled(false);
		btnPlantarse.setEnabled(false);
		
		String estadoFin = controlador.blackjackEstadoFin(clienteGana, cliente, blackjack);
		
		do {
			irMensajeFinal = false;
			int eleccion = JOptionPane.showOptionDialog(
				    null,
				    estadoFin + apuestaResultado +  "$! ¿Qué deseas hacer?",
				    "Fin de partida",
				    JOptionPane.DEFAULT_OPTION,
				    JOptionPane.QUESTION_MESSAGE,
				    null,
				    opciones,
				    opciones[0]
				);
				
			if (eleccion == 0) {
				this.dispose();
				jugar.setVisible(true);
				break;
			}
			
			if (eleccion == 1) {
				
				double apuestaNueva = controlador.alertaApuesta(cliente, blackjack);
				
				if (apuestaNueva == 0) {
					irMensajeFinal = true;
					
				} else {
					apuesta = apuestaNueva;
					irMensajeFinal = false;
					iniciarJuego();
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
		
		lblCartasCrupier.setText("Cartas crupier (" + blackjack.getDinero() + "$)");
		lblTusCartas.setText("Cartas " + cliente.getNombre() + "(" + cliente.getSaldo() + "$)");		
		lblCartasCrupierList.setText(blackjack.mostrarCartas(true, "crupier") + " (" + (blackjack.getCartasCrupier().get(0) == 1 ? "11/1" : blackjack.getCartasCrupier().get(0)) + ")");
		lblTusCartasList.setText(blackjack.mostrarCartas(false, "cliente") + " (" + barajaCliente + ")");
		lblApuestaActual.setText("Apuesta actual: " + apuesta + "$");
		
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
}
