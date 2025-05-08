package ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import logica.Controlador;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class Jugar extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableUsuarios;
	private JTable table;
	
	private MenuPrincipal menu;
	private Controlador controlador;

	/**
	 * Create the frame.
	 * @param controlador 
	 */
	public Jugar(MenuPrincipal menu, Controlador controlador) {
		
		this.menu = menu;
		this.controlador = controlador;
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 623, 435);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblJuego = new JLabel("Jugar", SwingConstants.CENTER);
		lblJuego.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblJuego.setBounds(10, 11, 513, 31);
		contentPane.add(lblJuego);
		
		JLabel lblUsuarios = new JLabel("Usuarios", SwingConstants.CENTER);
		lblUsuarios.setBounds(40, 82, 213, 31);
		contentPane.add(lblUsuarios);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 124, 213, 166);
		contentPane.add(scrollPane);
		
		tableUsuarios = new JTable();
		scrollPane.setViewportView(tableUsuarios);
		
		JLabel lblJuegos = new JLabel("Juegos", SwingConstants.CENTER);
		lblJuegos.setBounds(328, 86, 213, 23);
		contentPane.add(lblJuegos);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(328, 124, 213, 166);
		contentPane.add(scrollPane_1);
		
		table = new JTable();
		scrollPane_1.setViewportView(table);
		
		JButton btnJugar = new JButton("Jugar");
		btnJugar.setEnabled(false);
		btnJugar.setBounds(438, 327, 103, 31);
		contentPane.add(btnJugar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(325, 327, 103, 31);
		contentPane.add(btnVolver);
		
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlador.cerrarVentana(Jugar.this, menu, true);
			}
		});
		
		// Al cerrar la ventana mediante la X
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controlador.cerrarVentana(Jugar.this, menu, true);
			}
		});
	}
}
