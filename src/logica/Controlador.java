package logica;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import ventanas.BlackjackVentana;

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
	 * Método para validar que una apuesta sea válida, devuelve un mensaje de error si no lo es.
	 * @param apuestaTxt Cantidad de la apuesta
	 * @param saldoCliente Saldo del cliente
	 * @param dineroJuego Dinero del juego
	 * @return Mensaje de error si la apuesta no sirve, null si la apuesta es válida
	 */
	public String validarApuesta (String apuestaTxt, double saldoCliente, double dineroJuego) {
		
		double apuesta = 0;
		try {
			apuesta = Double.parseDouble(apuestaTxt);
			
		} catch (NumberFormatException e) {
			return "Ingresa una apuesta válida";
		}	
		
		if (apuesta > saldoCliente) return "La apuesta es mayor que el saldo del cliente";
		if (apuesta > dineroJuego) return "La apuesta es mayor que el dinero en el juego";
		if (apuesta < apuestaMin) return "La apuesta mínima son " + apuestaMin;
		if (apuesta > apuestaMax) return "La apuesta máxima son " + apuestaMax;
		
		return null;		
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
	 * @param resultado Resultado de la apuesta.
	 */
	public void actualizarSaldos(Cliente cliente, Juego juego, double resultado) {
	    cliente.setSaldo(cliente.getSaldo() + resultado);
	    modelo.modificarSaldoCliente(cliente);
	    juego.setDinero(juego.getDinero() - resultado);
	    modelo.modificarDineroJuego(juego);
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
	 * @return Mensaje con el estado del fin de partida.
	 */
	public String blackjackEstadoFin(boolean clienteGana, Cliente cliente, Blackjack blackjack) {
		
		int cartasCliente = blackjack.sumarCartas(blackjack.getCartasCliente());
		int cartasCrupier = blackjack.sumarCartas(blackjack.getCartasCrupier());
		
		// Cliente pierde
		if (!clienteGana) {
			// El cliente se pasa de 21
			if (cartasCliente > 21) {
				return "¡Te has pasado de 21, has perdido ";
				
			// Cliente pierde normal
			} else {
				return "¡Has perdido ";
			}
			
		// Cliente gana
		} else {
			// Empate
			if (cartasCliente == cartasCrupier) {
				if (cartasCliente == 21) return "¡Empate con Push, has ganado ";
				return "¡Empate, has ganado ";
			
			// Victoria
			} else {
				if (cartasCliente == 21 && blackjack.getCartasCliente().size() == 2) 
					return "¡Victoria con un Blackjack, has ganado ";
				return "¡Has ganado ";
			}
		}
	}
	
	
	/**
	 * Lanzar una ventana para ingresar una apuesta. Si el cliente o el juego tiene menos dinero que la apuesta mínima, no mostrará la ventana.
	 * @param cliente Objeto cliente que jugará.
	 * @param juego Objeto juego que jugará.
	 * @return La apuesta ingresada, devuelve 0 si no es válida.
	 */
	public double alertaApuesta(Cliente cliente, Juego juego) {
		
		// Cliente sin saldo suficiente
		if (cliente.getSaldo() < apuestaMin ) {
			JOptionPane.showMessageDialog(null, "Este cliente no puede jugar, tiene menos saldo que la apuesta mínima (" + apuestaMin + "$).", "Error", JOptionPane.ERROR_MESSAGE);
			return 0;
		}
		
		// Juego sin dinero suficiente
		if (juego.getDinero() < apuestaMin ) {
			JOptionPane.showMessageDialog(null, "Este juego no puede jugar, tiene menos dinero que la apuesta mínima (" + apuestaMin + "$).", "Error", JOptionPane.ERROR_MESSAGE);
			return 0;
		}
		
		while (true) {
			String apuestaNueva = JOptionPane.showInputDialog("Ingresa una apuesta");
			// El usuario ha cerrado la ventana
			if (apuestaNueva == null) {
				return 0;
			}
			
			String mensajeError = null;			
			mensajeError = validarApuesta(apuestaNueva, cliente.getSaldo(), juego.getDinero());			
			// El usuario ha ingresado un dato no válido
			if (mensajeError != null) {
				 JOptionPane.showMessageDialog(null, mensajeError, "Error", JOptionPane.ERROR_MESSAGE);
				 
			} else {				
				return 	Double.parseDouble(apuestaNueva);		
			}
		}
	}
}






























