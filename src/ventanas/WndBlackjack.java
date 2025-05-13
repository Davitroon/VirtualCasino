package ventanas;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.Controlador;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;

public class WndBlackjack extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private Controlador controlador;
	private Jugar jugar;

	public WndBlackjack(Controlador controlador, Jugar jugar) {
		
		this.controlador = controlador;
		this.jugar = jugar;
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 577, 442);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblBlackjack = new JLabel("Blackjack");
		lblBlackjack.setBounds(48, 11, 473, 30);
		contentPane.add(lblBlackjack);
		
		JLabel lblCartasCrupier = new JLabel("Cartas crupier");
		lblCartasCrupier.setBounds(48, 65, 99, 14);
		contentPane.add(lblCartasCrupier);
		
		JLabel lblTusCartas = new JLabel("Tus cartas");
		lblTusCartas.setBounds(48, 202, 79, 14);
		contentPane.add(lblTusCartas);
		
		JLabel lblCartasCrupierList = new JLabel("");
		lblCartasCrupierList.setBounds(48, 90, 473, 30);
		contentPane.add(lblCartasCrupierList);
		
		JLabel lblTusCartasList = new JLabel("");
		lblTusCartasList.setBounds(48, 227, 473, 30);
		contentPane.add(lblTusCartasList);
		
		JButton btnPlantarse = new JButton("Plantarse");
		btnPlantarse.setBounds(297, 292, 108, 38);
		contentPane.add(btnPlantarse);
		
		JButton btnRendirse = new JButton("Rendirse");
		btnRendirse.setBounds(149, 292, 108, 38);
		contentPane.add(btnRendirse);
		
		addWindowListener(new WindowAdapter() {
			// Al cerrar la ventana mediante la X
			@Override
			public void windowClosing(WindowEvent e) {
				controlador.cambiarVentana(WndBlackjack.this, jugar, false);			
			}
		});
	}
}
