package logica;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Excepciones.ApuestaExcepcion;
import ventanas.BlackjackVentana;
import ventanas.Jugar;
import ventanas.TragaperrasVentana;

/**
 * Clase controlador del MVC. Gestiona intercambios, validaciones y lógica.
 * @author David
 * @since 3.0
 */
public class Controlador {

	// Definición constantes
	private static final double saldoMin = 100;
	private static final double saldoMax = 999999;
	private static final int edadMax = 95;
	private static final int longitudNombreMax = 30;
	private static final double dineroMin = 1000;
	private static final double dineroMax = 999999;
	private static final double apuestaMin = 2;
	private static final double apuestaMax = 50000;
	
	private Modelo modelo;
	
	private double ultimaApuesta;
	
	public Controlador(Modelo modelo) {
		this.modelo = modelo;
	}


	/**
	 * Método para ocultar una ventana y mostrar una nueva.
	 * @param ventanaActual Ventana a cerrar
	 * @param ventanaNueva Ventana a abrir
	 * @since 3.0
	 */
	public void cambiarVentana(JFrame ventanaActual, JFrame ventanaNueva) {	
		ventanaActual.setVisible(false);
		ventanaNueva.setVisible(true);

	}
	
	
	/**
	 * Método para validar el nombre de un cliente.
	 * @param texto Nombre del cliente
	 * @param mensajeError Texo visual donde se mostrará el error (si hubiese)
	 * @return True si el nombre es válido, false si no lo es
	 * @since 3.0
	 */
	public boolean validarNombreCliente(String texto, JLabel mensajeError) {
		
		if (texto.isBlank()) {
			mensajeError.setText("");
			return false;
		}
		
		if (!texto.matches("[a-zA-Z ]+")) {
			mensajeError.setText("No se permiten caracteres especiales ni números");
			return false;
		}
		
		if (texto.length() > longitudNombreMax) {
			mensajeError.setText("Nombre demasiado largo (" + longitudNombreMax + " máx.)");
			return false;
		}		
		
		mensajeError.setText("");
		return true;
	}
	
	
	/**
	 * Método para validar la edad de un cliente.
	 * @param texto Edad del cliente
	 * @param mensajeError Texo visual donde se mostrará el error (si hubiese)
	 * @return True si la edad es válida, false si no lo es
	 * @since 3.0
	 */
	public boolean validarEdadCliente(String texto, JLabel mensajeError) {
		
		if (texto.isBlank()) {
			mensajeError.setText("");
			return false;
		}
		
		if (!texto.matches("[0-9]+")) {
			mensajeError.setText("Ingresa únicamente números");
			return false;
		}
		
		int edad = Integer.parseInt(texto);
		
		if (edad < 18) {
			mensajeError.setText("Cliente menor de edad.");
			return false;
		}
		
		if (edad > edadMax) {
			mensajeError.setText("Cliente demasiado mayor (" + edadMax + " máx.)");
			return false;
		}
		
		mensajeError.setText("");
		return true;
	}
	
	
	/**
	 * Método para validar el saldo de un cliente.
	 * @param texto Saldo del cliente
	 * @param mensajeError Texo visual donde se mostrará el error (si hubiese)
	 * @return True el saldo es válido, false si no lo es
	 * @since 3.0
	 */
	public boolean validarSaldoCliente(String texto, JLabel mensajeError) {
		
		double saldo;
		
		if (texto.isBlank()) {
			mensajeError.setText("");
			return false;
		}
		
		try {
			saldo = Double.parseDouble(texto);
		
		} catch (NumberFormatException ee) {
			mensajeError.setText("Ingresa únicamente números");
			return false;
		}
		
		if (saldo < saldoMin) {
			mensajeError.setText("Saldo muy pequeño (" + saldoMin + " min.)");
			return false;
		}
		
		if (saldo > saldoMax) {
			mensajeError.setText("Saldo demasiado grande (" + saldoMax + " max.)");
			return false;
		}
		
		mensajeError.setText("");
		return true;
	}
	
	
	/**
	 * Método para validar el dinero de un juego.
	 * @param texto Dinero del juego
	 * @param mensajeError Texo visual donde se mostrará el error (si hubiese)
	 * @return True el dinero es válido, false si no lo es
	 * @since 3.0
	 */
	public boolean validarDineroJuego(String texto, JLabel mensajeError) {
		
		double saldo;
		
		if (texto.isBlank()) {
			mensajeError.setText("");
			return false;
		}
		
		try {
			saldo = Double.parseDouble(texto);
		
		} catch (NumberFormatException ee) {
			mensajeError.setText("Ingresa únicamente números");
			return false;
		}
		
		if (saldo < dineroMin) {
			mensajeError.setText("Cantidad muy pequeña (" + dineroMin + " min.)");
			return false;
		}
		
		if (saldo > dineroMax) {
			mensajeError.setText("Cantidad demasiado grande (" + dineroMax + " max.)");
			return false;
		}
		
		mensajeError.setText("");
		return true;
	}
	
	
	/**
	 * Método para validar que una apuesta sea válida. Lanza una excepción si no lo es.
	 * @param apuestaTxt Cantidad de la apuesta como texto
	 * @param saldoCliente Saldo del cliente
	 * @param dineroJuego Dinero del juego
	 * @throws ApuestaExcepcion Si la apuesta no es válida
	 */
	public void validarApuesta(String apuestaTxt, double saldoCliente, double dineroJuego) throws ApuestaExcepcion {
	    double apuesta;
	    
	    try {
	        apuesta = Double.parseDouble(apuestaTxt);
	    } catch (NumberFormatException e) {
	        throw new ApuestaExcepcion("Ingresa una apuesta válida");
	    }
	    
	    if (apuesta > saldoCliente) {
	        throw new ApuestaExcepcion("La apuesta es mayor que el saldo del cliente");
	    }
	    if (apuesta > dineroJuego) {
	        throw new ApuestaExcepcion("La apuesta es mayor que el dinero en el juego");
	    }
	    if (apuesta < apuestaMin) {
	        throw new ApuestaExcepcion("La apuesta mínima son " + apuestaMin);
	    }
	    if (apuesta > apuestaMax) {
	        throw new ApuestaExcepcion("La apuesta máxima son " + apuestaMax);
	    }
	}
	
	
	/**
	 * El jugador pide una carta en el juego Blackjack.
	 * @param blackjack Objeto blackjack.
	 * @return Verdadero si se ha pasado de 21, falso si no.
	 */
	public boolean blackjackPedirCarta(Blackjack blackjack) {
	    blackjack.repartirCartas(1, "cliente");
	    return blackjack.jugadorPierde("cliente");
	}
	
	
	/**
	 * Actualizar el saldo y el dinero de juego y cliente al terminar una partida.
	 * @param cliente Cliente a actualizar.
	 * @param juego Juego a actualizar.
	 * @param resultadoApuesta Resultado de la apuesta.
	 * @param cerrarJuego Verdadero si se ha cerraro el juego antes de jugar, falso si la apuesta se terminó.
	 */
	public void actualizarSaldos(Cliente cliente, Juego juego, double resultadoApuesta, boolean cerrarJuego) {
	    if (cerrarJuego) {
	    	cliente.setSaldo(cliente.getSaldo() - resultadoApuesta);
	    	juego.setDinero(juego.getDinero() + resultadoApuesta);
	    	
	    } else {
	    	cliente.setSaldo(cliente.getSaldo() + resultadoApuesta);
	    	juego.setDinero(juego.getDinero() - resultadoApuesta);
	    }		
	    modelo.modificarSaldoCliente(cliente);
	    modelo.modificarDineroJuego(juego);
	    modelo.agregarPartida(cliente, juego, resultadoApuesta);
	}
	
	
	/**
	 * Iniciar partida de blackjack, barajando las cartas y repartiendo las cartas a los jugadores.
	 * @param blackjack Objeto de la partida de blackjack.
	 */
	public void blackjackIniciar(Blackjack blackjack) {
	    blackjack.barajarCartas();
	    blackjack.repartirCartas(2, "crupier");
	    blackjack.repartirCartas(2, "cliente");
	}
	
	
	/**
	 * Maneja la acción de plantarse en el Blackjack
	 * @param blackjack Juego de Blackjack.
	 * @param ventana Ventana de Blackjack.
	 * @return true si el cliente gana, false si pierde
	 */
	public boolean blackjackPlantarse(Blackjack blackjack, BlackjackVentana ventana) {
	    while (blackjack.crupierDebePedir()) {
	        blackjack.repartirCartas(1, "crupier");
	        if (blackjack.jugadorPierde("crupier")) {
	        	// El jugador ha ganado
	            ventana.actualizarCartasCrupier(blackjack.mostrarCartas(false, "crupier"), 
	                                        blackjack.sumarCartas(blackjack.getCartasCrupier()));
	            return true;
	        }
	    }
	    // El jugador ha perdido
	    ventana.actualizarCartasCrupier(blackjack.mostrarCartas(false, "crupier"), 
	                                blackjack.sumarCartas(blackjack.getCartasCrupier()));
	    return false;
	}


	/** 
	 * Muestra un el resultado final de una partida de blackjack.
	 * @param clienteGana True si el jugador ganó, false si el jugador perdió.
	 * @param cliente Objeto de Cliente que jugó.
	 * @param blackjack Objeto de Blackjack que jugó.
	 * @param apuestaResultado 
	 * @return Mensaje con el estado del fin de partida.
	 */
	public String blackjackEstadoFin(boolean clienteGana, Cliente cliente, Blackjack blackjack, double apuestaResultado) {
		
		int cartasCliente = blackjack.sumarCartas(blackjack.getCartasCliente());
		int cartasCrupier = blackjack.sumarCartas(blackjack.getCartasCrupier());
		
		// Cliente pierde
		if (!clienteGana) {
		    // El cliente se pasa de 21
		    if (cartasCliente > 21) {
		        return "¡Te has pasado de 21, has perdido " + String.format("%.2f", apuestaResultado) + "$!";
		    
		    // Cliente pierde normal
		    } else {
		        return "¡Has perdido " + String.format("%.2f", apuestaResultado) + "$!";
		    }

		// Cliente gana
		} else {
		    // Empate
		    if (cartasCliente == cartasCrupier) {
		        if (cartasCliente == 21) {
		        	return "¡Empate con Push, has ganado " + String.format("%.2f", apuestaResultado) + "$!";
		        }
		        return "¡Empate, has ganado " + String.format("%.2f", apuestaResultado) + "$!";
		    
		    // Victoria
		    } else {
		        if (cartasCliente == 21 && blackjack.getCartasCliente().size() == 2) {
		        	return "¡Victoria con un Blackjack, has ganado " + String.format("%.2f", apuestaResultado) + "$!";
		        }
		        return "¡Has ganado " + String.format("%.2f", apuestaResultado) + "$!";
		    }
		}
	}

	
	
	/**
	 * Lanza una excepción si no se puede apostar. Solicita al usuario una apuesta válida.
	 * @param cliente Objeto cliente que jugará.
	 * @param juego Objeto juego que jugará.
	 * @return La apuesta ingresada, devuelve 0 si no es válida o se cancela.
	 * @throws ApuestaExcepcion si el cliente o el juego no tienen suficiente dinero.
	 * @since 3.0
	 */
	public double alertaApuesta(Cliente cliente, Juego juego) throws ApuestaExcepcion {

		    if (cliente.getSaldo() < apuestaMin) {
		        throw new ApuestaExcepcion("Este cliente no puede jugar, tiene menos saldo que la apuesta mínima (" + apuestaMin + "$).");
		    }

		    if (juego.getDinero() < apuestaMin) {
		        throw new ApuestaExcepcion("Este juego no puede jugar, tiene menos dinero que la apuesta mínima (" + apuestaMin + "$).");
		    }

		    // Mostrar ventana
		    while (true) {
		        JTextField campoApuesta = new JTextField();
		        Object[] mensaje = {
		            "Ingresa una apuesta:", campoApuesta
		        };

		        String[] opciones = {"Cancelar", "Repetir apuesta", "Aceptar"};
		        int opcion = JOptionPane.showOptionDialog(null, mensaje, "Apuesta",
		                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
		                null, opciones, opciones[0]);

		        // Usuario canceló
		        if (opcion == 0 || opcion == JOptionPane.CLOSED_OPTION) {
		            return 0; 
		         
	            // Repetir apuesta   
		        } else if (opcion == 1) {
		            if (ultimaApuesta == 0) {
		                JOptionPane.showMessageDialog(null, "No hay una apuesta previa.");
		                continue;
		            }

		            try {
		                validarApuesta(String.valueOf(ultimaApuesta), cliente.getSaldo(), juego.getDinero());
		                return ultimaApuesta;
		            } catch (ApuestaExcepcion ex) {
		                throw ex;
		            }
		            
	            // Apuesta normal
		        } else {
		            String apuestaTexto = campoApuesta.getText();
		            try {
		                validarApuesta(apuestaTexto, cliente.getSaldo(), juego.getDinero());
		                ultimaApuesta = Double.parseDouble(apuestaTexto);
		                return ultimaApuesta;
		                
		            } catch (ApuestaExcepcion ex) {
		                throw ex;
		            }
		        }
		    }
		}


	/**
	 * Método para abrir la ventana de un juego. Dependiendo de la instancia del juego recibido abrirá de un tipo o de otra.
	 * @param juego Clase del juego a jugar.
	 * @param jugarVentana Ventana "Jugar" desde donde se debería llamar a este método.
	 * @param cliente Cliente a jugar.
	 * @param apuesta Cantidad de la apuesta.
	 * @param ventanaJuego Ventana del juego.
	 * @since 3.0
	 */
	public void abrirJuegoVentana(Juego juego, Jugar jugarVentana, Cliente cliente, double apuesta) {
		if (juego instanceof Blackjack) {						
			BlackjackVentana blackjackVentana = new BlackjackVentana(this, modelo, jugarVentana, cliente, juego, apuesta);
			cambiarVentana(jugarVentana, blackjackVentana);
			blackjackVentana.iniciarJuego();
		}
		
		if (juego instanceof Tragaperras ) {
			TragaperrasVentana tragaperrasVentana = new TragaperrasVentana(this, modelo, jugarVentana, cliente, juego, apuesta);
			cambiarVentana(jugarVentana, tragaperrasVentana);
			tragaperrasVentana.iniciarJuego();
		}
	}


	/**
	 * Muestra un el resultado final de una partida de tragaperras.
	 * @param cliente Cliente jugando.
	 * @param tragaperras Objeto de clase tragaperras jugando.
	 * @param apuestaResultado Resultado de la apuesta.
	 * @return Mensaje del resultado de la partida.
	 */
	public String tragaperrasEstadoFin(Cliente cliente, Tragaperras tragaperras, double apuestaResultado) {
		int [] numeros = tragaperras.getNumeros();
		if (numeros[0] == 7 && numeros[1] == 7 && numeros[2] == 7) {
		    return String.format("¡¡Jackpot, has ganado %.2f$!", apuestaResultado);
		}

		if (numeros[0] == numeros[1] && numeros[0] == numeros[2]) {
		    return String.format("¡Triple combinación, has ganado %.2f$!", apuestaResultado);
		}

		if (numeros[0] == numeros[1] || numeros[0] == numeros[2] || numeros[1] == numeros[2]) {
		    return String.format("¡Doble combinación, has ganado %.2f$!", apuestaResultado);
		}

		return String.format("Ninguna combinación... Has perdido %.2f$.", apuestaResultado);
	}


	/**
	 * Método que mostrará al usuario un aviso si intenta cerrar una partida sin haberla terminado.
	 * @param cliente Cliente jugando.
	 * @param juego Juego jugando.
	 * @param apuesta Apuesta de la partida.
	 * @return True si ha confirmado salir, false si no.
	 */
	public boolean avisoCerrarJuego(Cliente cliente, Juego juego, double apuesta) {
		int respuesta = JOptionPane.showConfirmDialog(
			    null,
			    "¿Estás seguro que quieres salir? Contará como que has perdido la partida",
			    "Confirmación",
			    JOptionPane.YES_NO_OPTION,
			    JOptionPane.QUESTION_MESSAGE
			);

		if (respuesta == JOptionPane.YES_OPTION) {
			actualizarSaldos(cliente, juego, apuesta, true);
			return true;
		}
			
		return false;	
	}


	/**
	 * Método que rellena la tabla de clientes en la ventana Juegos
	 * @param rset Resultado de la consulta a la BD.
	 * @param modeloClientes Tabla de clientes.
	 */
	public void rellenarTablaClientes(ResultSet rset, DefaultTableModel modeloClientes) {
		try {
			do {
				// "ID", "Nombre", "Activo", "Saldo"
		        Object[] clienteLista = new Object[4];
		        clienteLista[0] = rset.getInt(1);
		        clienteLista[1] = rset.getString(2);
		        clienteLista[2] = (rset.getBoolean(5) == true ? "SI" : "NO");
		        clienteLista[3] = rset.getDouble(6);
		        
		        modeloClientes.addRow(clienteLista);
			} while (rset.next());
			
		} catch (SQLException e) {	
			e.printStackTrace();
		}			
	}
	
	/**
	 * Método que rellena la tabla de clientes en la ventana Juegos
	 * @param rset Resultado de la consulta a la BD.
	 * @param modeloJuegos Tabla de juegos.
	 */
	public void rellenarTablaJuegos(ResultSet rset, DefaultTableModel modeloJuegos) {
		try {
			do {
				// "ID", "Tipo", "Activo", "Dinero"
		        Object[] juegoLista = new Object[4];
		        juegoLista[0] = rset.getInt(1);
		        juegoLista[1] = rset.getString(2);
		        juegoLista[2] = (rset.getBoolean(3) == true ? "SI" : "NO");
		        juegoLista[3] = rset.getDouble(4);
		        
		        modeloJuegos.addRow(juegoLista);
			} while (rset.next());
			
		} catch (SQLException e) {	
			e.printStackTrace();
		}			
	}
	
	
	/**
	 * Método que mostrará una ventana de fin génerica para los juegos.
	 * @param mensajeEstado Mensaje personalizado dependiendo del contexto del fin de partida.
	 * @return Elección del jugador (Volver / Apostar de nuevo).
	 */
	public int juegosEstadoFin(String mensajeEstado) {
		String[] opciones = {"Volver", "Apostar de nuevo"};
		int eleccion = JOptionPane.showOptionDialog(
			    null,
			    mensajeEstado +  " ¿Qué deseas hacer?",
			    "Fin de partida",
			    JOptionPane.DEFAULT_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    opciones,
			    opciones[0]
			);
		
		return eleccion;
	}
	
	
	/**
	 * Método que rellena la tabla de clientes en la ventana Gestion, con todos los campos
	 * @param rset Resultado de la consulta a la BD.
	 * @param modeloJuegos Tabla de juegos.
	 */
	public void rellenarTablaClientesCompleto(ResultSet rset, DefaultTableModel modeloClientes) {
		try {
			do {
				// "ID", "Nombre", "Activo", "Saldo"
		        Object[] clienteLista = new Object[6];
		        clienteLista[0] = rset.getInt(1);
		        clienteLista[1] = rset.getString(2);
		        clienteLista[2] = rset.getInt(3);
		        clienteLista[3] = rset.getString(4);
		        clienteLista[4] = (rset.getBoolean(5) == true ? "SI" : "NO");
		        clienteLista[5] = rset.getDouble(6);
		        
		        modeloClientes.addRow(clienteLista);
			} while (rset.next());
			
		} catch (SQLException e) {	
			e.printStackTrace();
		}			
	}
}