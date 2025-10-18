package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import logic.Controller;
import logic.Model;
import logic.User;
import ui.StatisticsUI;
import ui.HomeUI;

/**
 * Ventana donde guarda información de las partidas
 */
public class StatisticsUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JLabel lblPartidasJugadasVal;
	private JLabel lblPartidasGanadasVal;
	private JLabel lblPartidasPerdidasVal;
	private JLabel lblPartidasBlackjackVal;
	private JLabel lblPartidasTragaperrasVal;
	private JLabel lblUltimaPartidaVal;
	private JLabel lblDineroJuegoVal;
	private JLabel lblDineroPerdidoVal;
	private JLabel lblClienteSaldoVal;
	private JLabel lblDineroGanadoVal;
	private JButton btnBorrarEstadisticas;

	private Model modelo;
	private User usuario;
	private JLabel lblUsuario;

	/**
	 * Create the frame.
	 * @param menuPrincipal 
	 * @param modelo 
	 * @param controlador 
	 */
	public StatisticsUI(HomeUI menuPrincipal, Model modelo, Controller controlador, User usuario) {
		this.modelo = modelo;
		this.usuario = usuario;
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 682, 399);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEstadisticas = new JLabel("Estadísticas", SwingConstants.CENTER);
		lblEstadisticas.setFont(new Font("Stencil", Font.PLAIN, 28));
		lblEstadisticas.setBounds(10, 40, 648, 39);
		contentPane.add(lblEstadisticas);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(128, 128, 128));
		btnVolver.setBounds(20, 297, 120, 32);
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
		lblClienteSaldo.setBounds(314, 192, 170, 24);
		contentPane.add(lblClienteSaldo);
		
		JLabel lblJuegoDinero = new JLabel("Juego con más dinero:");
		lblJuegoDinero.setFont(new Font("SimSun", Font.BOLD, 14));
		lblJuegoDinero.setBounds(314, 227, 162, 24);
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
		lblDineroPerdido.setBounds(314, 157, 120, 24);
		contentPane.add(lblDineroPerdido);
		
		JLabel lblDineroGanado = new JLabel("Dinero ganado:");
		lblDineroGanado.setFont(new Font("SimSun", Font.BOLD, 14));
		lblDineroGanado.setBounds(314, 122, 120, 24);
		contentPane.add(lblDineroGanado);
		
		JLabel lblUltimaPartida = new JLabel("Última partida jugada:");
		lblUltimaPartida.setFont(new Font("SimSun", Font.BOLD, 14));
		lblUltimaPartida.setBounds(314, 262, 178, 24);
		contentPane.add(lblUltimaPartida);
		
		lblPartidasJugadasVal = new JLabel("lorem");
		lblPartidasJugadasVal.setFont(new Font("SimSun", Font.PLAIN, 14));
		lblPartidasJugadasVal.setBounds(155, 127, 141, 14);
		contentPane.add(lblPartidasJugadasVal);
		
		lblPartidasGanadasVal = new JLabel("lorem");
		lblPartidasGanadasVal.setFont(new Font("SimSun", Font.PLAIN, 14));
		lblPartidasGanadasVal.setBounds(155, 163, 149, 14);
		contentPane.add(lblPartidasGanadasVal);
		
		lblPartidasPerdidasVal = new JLabel("lorem");
		lblPartidasPerdidasVal.setFont(new Font("SimSun", Font.PLAIN, 14));
		lblPartidasPerdidasVal.setBounds(164, 197, 140, 14);
		contentPane.add(lblPartidasPerdidasVal);
		
		lblPartidasBlackjackVal = new JLabel("lorem");
		lblPartidasBlackjackVal.setFont(new Font("SimSun", Font.PLAIN, 14));
		lblPartidasBlackjackVal.setBounds(197, 233, 108, 14);
		contentPane.add(lblPartidasBlackjackVal);
		
		lblPartidasTragaperrasVal = new JLabel("lorem");
		lblPartidasTragaperrasVal.setFont(new Font("SimSun", Font.PLAIN, 14));
		lblPartidasTragaperrasVal.setBounds(211, 268, 93, 14);
		contentPane.add(lblPartidasTragaperrasVal);
		
		lblDineroGanadoVal = new JLabel("lorem");
		lblDineroGanadoVal.setFont(new Font("SimSun", Font.PLAIN, 14));
		lblDineroGanadoVal.setBounds(433, 127, 225, 14);
		contentPane.add(lblDineroGanadoVal);
		
		lblDineroPerdidoVal = new JLabel("lorem");
		lblDineroPerdidoVal.setFont(new Font("SimSun", Font.PLAIN, 14));
		lblDineroPerdidoVal.setBounds(439, 163, 219, 14);
		contentPane.add(lblDineroPerdidoVal);
		
		lblClienteSaldoVal = new JLabel("lorem");
		lblClienteSaldoVal.setFont(new Font("SimSun", Font.PLAIN, 14));
		lblClienteSaldoVal.setBounds(488, 197, 170, 14);
		contentPane.add(lblClienteSaldoVal);
		
		lblDineroJuegoVal = new JLabel("lorem");
		lblDineroJuegoVal.setFont(new Font("SimSun", Font.PLAIN, 14));
		lblDineroJuegoVal.setBounds(480, 232, 178, 14);
		contentPane.add(lblDineroJuegoVal);
		
		lblUltimaPartidaVal = new JLabel("lorem");
		lblUltimaPartidaVal.setFont(new Font("SimSun", Font.PLAIN, 14));
		lblUltimaPartidaVal.setBounds(490, 267, 168, 14);
		contentPane.add(lblUltimaPartidaVal);
		
		btnBorrarEstadisticas = new JButton("Borrar estadísticas");
		btnBorrarEstadisticas.setForeground(new Color(0, 0, 0));
		btnBorrarEstadisticas.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnBorrarEstadisticas.setBackground(new Color(242, 77, 77));
		btnBorrarEstadisticas.setBounds(516, 90, 142, 32);
		contentPane.add(btnBorrarEstadisticas);
		
		lblUsuario = new JLabel("Estadísticas de ");
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUsuario.setBounds(20, 95, 178, 14);
		contentPane.add(lblUsuario);
		
		// Al cerrar la ventana mediante la X
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controlador.cambiarVentana(StatisticsUI.this, menuPrincipal);
			}
		});
		
		// Clic boton volver
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlador.cambiarVentana(StatisticsUI.this, menuPrincipal);
			}
		});
		
		// Clic boton borrar estadísticas
		btnBorrarEstadisticas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 int opcion = JOptionPane.showConfirmDialog(
					        null,
					        "¿Estás seguro de que deseas borrar todas las estadísticas?",
					        "Confirmar borrado",
					        JOptionPane.YES_NO_OPTION,
					        JOptionPane.WARNING_MESSAGE
					    );

			    if (opcion == JOptionPane.YES_OPTION) {
			        modelo.borrarDatosTabla("game_sessions");
			        actualizarDatos();
			    }
			}
		});
	}
	
	
	/**
	 * Actualizar las estadisticas consultando a la BD.
	 * Si una consulta no devuelve datos, lo marcará como null.
	 */
	public void actualizarDatos() {
		lblUsuario.setText("Estadísticas de " + usuario.getNombre());
	    ResultSet rset = null;
	    try {
	    	
	    	rset = modelo.consultarDatos("game_sessions", false);
	    	if (!rset.next()) {
	    		btnBorrarEstadisticas.setEnabled(false);
	    		
	    	} else {
	    		btnBorrarEstadisticas.setEnabled(true);
	    	}
	    	
	    	// Partidas jugadas
	    	rset = modelo.consultaEspecifica("SELECT COUNT(*) FROM game_sessions WHERE user_profile = " + usuario.getId() + ";");
	    	rset.next();
	    	lblPartidasJugadasVal.setText(rset.getString(1));

	    	// Partidas ganadas (bet_result > 0)
	    	rset = modelo.consultaEspecifica("SELECT COUNT(*) FROM game_sessions WHERE bet_result > 0 AND user_profile = " + usuario.getId() + ";");
	    	rset.next();
	    	lblPartidasGanadasVal.setText(rset.getString(1));

	    	// Partidas perdidas (bet_result < 0)
	    	rset = modelo.consultaEspecifica("SELECT COUNT(*) FROM game_sessions WHERE bet_result < 0 AND user_profile = " + usuario.getId() + ";");
	    	rset.next();
	    	lblPartidasPerdidasVal.setText(rset.getString(1));

	    	// Partidas Blackjack
	    	rset = modelo.consultaEspecifica("SELECT COUNT(*) FROM game_sessions WHERE game_type = 'Blackjack' AND user_profile = " + usuario.getId() + ";");
	    	rset.next();
	    	lblPartidasBlackjackVal.setText(rset.getString(1));

	    	// Partidas Slot Machine
	    	rset = modelo.consultaEspecifica("SELECT COUNT(*) FROM game_sessions WHERE game_type = 'SlotMachine' AND user_profile = " + usuario.getId() + ";");
	    	rset.next();
	    	lblPartidasTragaperrasVal.setText(rset.getString(1));

	    	// Dinero ganado (suma de bet_result positivos)
	    	rset = modelo.consultaEspecifica("SELECT SUM(bet_result) FROM game_sessions WHERE bet_result > 0 AND user_profile = " + usuario.getId() + ";");
	    	rset.next();
	    	double dineroGanado;
	    	if (rset.wasNull()) {
	    		dineroGanado = 0.0;
	    	
	    	} else {
	    		dineroGanado = rset.getDouble(1);
	    	}
	    	lblDineroGanadoVal.setText(String.format("%.2f$", dineroGanado));

	    	// Dinero perdido (suma de bet_result negativos)
	    	rset = modelo.consultaEspecifica("SELECT SUM(bet_result) FROM game_sessions WHERE bet_result < 0 AND user_profile = " + usuario.getId() + ";");
	    	rset.next();
	    	double dineroPerdido;
	    	if (rset.wasNull()) {
	    		dineroPerdido = 0.0;
	    	
	    	} else {
	    		dineroPerdido = rset.getDouble(1);
	    	}
	    	lblDineroPerdidoVal.setText(String.format("%.2f$", dineroPerdido));

	    	// Cliente con más saldo
	    	rset = modelo.consultaEspecifica("SELECT customer_name, balance FROM customers WHERE user_profile = " + usuario.getId() + " ORDER BY balance DESC LIMIT 1;");
	    	if (rset.next()) {
	    	    lblClienteSaldoVal.setText(
	    	        String.format("%s (%.2f$)", rset.getString(1), rset.getDouble(2)));
	    	} else {
	    	    lblClienteSaldoVal.setText("Ninguno registrado");
	    	}

	    	// Juego con más dinero en pool
	    	rset = modelo.consultaEspecifica("SELECT id, money_pool FROM games WHERE user_profile = " + usuario.getId() + " ORDER BY money_pool DESC LIMIT 1;");
	    	if (rset.next()) {
	    	    lblDineroJuegoVal.setText(
	    	        String.format("Game %d (%.2f$)", rset.getInt(1), rset.getDouble(2)));
	    	} else {
	    	    lblDineroJuegoVal.setText("Ninguno registrado");
	    	}

	    	// Última partida jugada
	    	rset = modelo.consultaEspecifica("SELECT session_date FROM game_sessions WHERE user_profile = " + usuario.getId() + " ORDER BY session_date DESC LIMIT 1;");
	    	if (rset.next()) {
	    	    lblUltimaPartidaVal.setText(rset.getString(1));
	    	} else {
	    	    lblUltimaPartidaVal.setText("Ninguno registrado");
	    	}

	        rset.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}
}
