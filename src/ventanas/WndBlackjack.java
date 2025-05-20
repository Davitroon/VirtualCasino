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

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WndBlackjack extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private Controlador controlador;
	private Jugar jugar;
	private JLabel lblCartasCrupierList;
	private JLabel lblTusCartasList;
	private JLabel lblTusCartas;
	private JLabel lblCartasCrupier;
	
	private Cliente cliente;
	private Blackjack blackjack;
	
	private double apuesta;

	public WndBlackjack(Controlador controlador, Jugar jugar, Cliente cliente, Blackjack blackjack, double apuesta) {
		
		this.controlador = controlador;
		this.jugar = jugar;
		
		this.cliente = cliente;
		this.blackjack = blackjack;
		
		this.apuesta = apuesta;
		
		setResizable(false);
		setBounds(100, 100, 577, 442);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblBlackjack = new JLabel("Blackjack");
		lblBlackjack.setBounds(48, 11, 473, 30);
		contentPane.add(lblBlackjack);
		
		lblCartasCrupier = new JLabel("");
		lblCartasCrupier.setBounds(48, 65, 142, 14);
		contentPane.add(lblCartasCrupier);
		
		lblTusCartas = new JLabel("Tus cartas");
		lblTusCartas.setBounds(48, 238, 142, 14);
		contentPane.add(lblTusCartas);
		
		lblCartasCrupierList = new JLabel("");
		lblCartasCrupierList.setBounds(48, 90, 473, 30);
		contentPane.add(lblCartasCrupierList);
		
		lblTusCartasList = new JLabel("");
		lblTusCartasList.setBounds(48, 263, 473, 30);
		contentPane.add(lblTusCartasList);
		
		JButton btnPedir = new JButton("Pedir");
		btnPedir.setBounds(297, 318, 108, 38);
		contentPane.add(btnPedir);
		
		JButton btnPlantarse = new JButton("Plantarse");
		btnPlantarse.setBounds(150, 318, 108, 38);
		contentPane.add(btnPlantarse);
		
		JLabel lblResultado = new JLabel("");
		lblResultado.setBounds(48, 151, 473, 38);
		contentPane.add(lblResultado);
		
		addWindowListener(new WindowAdapter() {
			// Al cerrar la ventana mediante la X
			@Override
			public void windowClosing(WindowEvent e) {
				jugar.setVisible(true);
			}
		});
		
		
		// Clic boton plantarse
		btnPlantarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblCartasCrupierList.setText(blackjack.mostrarCartas(false, "crupier"));
				while (blackjack.crupierDebePedir()) {
					blackjack.repartirCartas(1, "crupier");
					lblCartasCrupierList.setText(blackjack.mostrarCartas(false, "crupier"));
					if (blackjack.jugadorPierde("crupier")) {
						finJuego(true);
						return;
					}
				}
				finJuego(false);
			}
		});
		
		
		// Clic boton pedir
		btnPedir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				blackjack.repartirCartas(1, "cliente");
				lblTusCartasList.setText(blackjack.mostrarCartas(false, "cliente"));
				
				if (blackjack.jugadorPierde("cliente")) {
					finJuego(false);
				}
				if (blackjack.sumarCartas(blackjack.getCartasCliente()) == 21) {
					finJuego(true);
				}
			}
		});
	}
	
	
	public void finJuego(boolean clienteGana) {
		String[] opciones = {"Volver", "Apostar de nuevo"};
		double apuestaResultado = blackjack.jugar(apuesta);
		boolean irMensajeFinal;
		
		do {
			irMensajeFinal = false;
			int eleccion = JOptionPane.showOptionDialog(
				    null,
				    (clienteGana  ? "¡Has ganado" : "¡Has perdido") + "¿Qué deseas hacer?",
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
				while (true) {
					String apuestaNueva = JOptionPane.showInputDialog("Ingresa una apuesta");
					if (apuestaNueva == null) {
						irMensajeFinal = true;
						break;
					}
					String mensajeError = null;
					
					mensajeError = controlador.validarApuesta(apuestaNueva, cliente.getSaldo(), blackjack.getDinero());
					
					if (mensajeError != null) {
						 JOptionPane.showMessageDialog(null, mensajeError, "Error", JOptionPane.ERROR_MESSAGE);
						 
					} else {
						
						apuesta = Integer.parseInt(apuestaNueva);
						iniciarJuego();
						irMensajeFinal = false;
						break;					
					}
				}
			}
		} while (irMensajeFinal);
	}
	
	public void iniciarJuego() {
		lblCartasCrupier.setText("Cartas crupier (" + blackjack.getDinero() + "$)");
		lblTusCartas.setText("Tus cartas (" + cliente.getSaldo() + "$)");
		blackjack.barajarCartas();
		blackjack.repartirCartas(2, "crupier");
		blackjack.repartirCartas(1, "cliente");
		lblCartasCrupierList.setText(blackjack.mostrarCartas(true, "crupier"));
		lblTusCartasList.setText(blackjack.mostrarCartas(false, "cliente"));
	}
}
