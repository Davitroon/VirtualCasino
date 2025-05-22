package ventanas;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import logica.Controlador;
import logica.Modelo;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Estadisticas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private MenuPrincipal menuPrincipal;
	private Modelo modelo;
	private Controlador controlador;

	/**
	 * Create the frame.
	 * @param menuPrincipal 
	 * @param modelo 
	 * @param controlador 
	 */
	public Estadisticas(MenuPrincipal menuPrincipal, Modelo modelo, Controlador controlador) {
		this.menuPrincipal = menuPrincipal;
		this.modelo = modelo;
		this.controlador = controlador;
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 682, 399);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEstadisticas = new JLabel("Estadísticas", SwingConstants.CENTER);
		lblEstadisticas.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblEstadisticas.setBounds(10, 40, 648, 39);
		contentPane.add(lblEstadisticas);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(538, 319, 120, 32);
		contentPane.add(btnVolver);
		
		JLabel lblPartidasJugadas = new JLabel("Partidas jugadas:");
		lblPartidasJugadas.setFont(new Font("SimSun", Font.BOLD, 14));
		lblPartidasJugadas.setBounds(20, 122, 142, 24);
		contentPane.add(lblPartidasJugadas);
		
		JLabel lblPartidasGanadas = new JLabel("Partidas ganadas:");
		lblPartidasGanadas.setFont(new Font("SimSun", Font.BOLD, 14));
		lblPartidasGanadas.setBounds(20, 157, 142, 24);
		contentPane.add(lblPartidasGanadas);
		
		JLabel lblPartidasPerdidas = new JLabel("Partidas perdidas:");
		lblPartidasPerdidas.setFont(new Font("SimSun", Font.BOLD, 14));
		lblPartidasPerdidas.setBounds(20, 192, 142, 24);
		contentPane.add(lblPartidasPerdidas);
		
		JLabel lblClienteSaldo = new JLabel("Cliente con más saldo:");
		lblClienteSaldo.setFont(new Font("SimSun", Font.BOLD, 14));
		lblClienteSaldo.setBounds(314, 192, 178, 24);
		contentPane.add(lblClienteSaldo);
		
		JLabel lblJuegoDinero = new JLabel("Juego con más dinero:");
		lblJuegoDinero.setFont(new Font("SimSun", Font.BOLD, 14));
		lblJuegoDinero.setBounds(314, 227, 178, 24);
		contentPane.add(lblJuegoDinero);
		
		JLabel lblPartidasBlackjack = new JLabel("Partidas de blackjack:");
		lblPartidasBlackjack.setFont(new Font("SimSun", Font.BOLD, 14));
		lblPartidasBlackjack.setBounds(20, 227, 178, 24);
		contentPane.add(lblPartidasBlackjack);
		
		JLabel lblPartidasTragaperras = new JLabel("Partidas de tragaperras:");
		lblPartidasTragaperras.setFont(new Font("SimSun", Font.BOLD, 14));
		lblPartidasTragaperras.setBounds(20, 262, 193, 24);
		contentPane.add(lblPartidasTragaperras);
		
		JLabel lblDineroPerdido = new JLabel("Dinero perdido:");
		lblDineroPerdido.setFont(new Font("SimSun", Font.BOLD, 14));
		lblDineroPerdido.setBounds(314, 162, 178, 24);
		contentPane.add(lblDineroPerdido);
		
		JLabel lblDineroGanado = new JLabel("Dinero ganado:");
		lblDineroGanado.setFont(new Font("SimSun", Font.BOLD, 14));
		lblDineroGanado.setBounds(314, 127, 178, 24);
		contentPane.add(lblDineroGanado);
		
		JLabel lblUltimaPartida = new JLabel("Última partida jugada:");
		lblUltimaPartida.setFont(new Font("SimSun", Font.BOLD, 14));
		lblUltimaPartida.setBounds(314, 262, 178, 24);
		contentPane.add(lblUltimaPartida);
		
		// Al cerrar la ventana mediante la X
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controlador.cambiarVentana(Estadisticas.this, menuPrincipal);
			}
		});
		
		// Clic boton volver
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlador.cambiarVentana(Estadisticas.this, menuPrincipal);
			}
		});
		
		
	}

}
