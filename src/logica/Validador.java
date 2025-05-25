package logica;

import javax.swing.JLabel;

import excepciones.ApuestaExcepcion;

/**
 * Clase ocupada de validar entrada y saldia de datos
 */
public class Validador {
	
	// Definición constantes
	private static final double saldoMin = 100;
	private static final double saldoMax = 999999;
	private static final int edadMax = 95;
	private static final int longitudNombreMax = 30;
	private static final double dineroMin = 1000;
	private static final double dineroMax = 999999;
	private static final double apuestaMin = 2;
	private static final double apuestaMax = 50000;

	/**
	 * Método para validar que una apuesta sea válida. Lanza una excepción si no lo es.
	 * @param apuestaTxt Cantidad de la apuesta como texto
	 * @param saldoCliente Saldo del cliente
	 * @param dineroJuego Dinero del juego
	 * @throws ApuestaExcepcion Excepción que se lanza si la apuesta no es válida
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
	 * Método que comprueba que tanto el cliente como el juego tengan más dinero que la apuesta mínima.
	 * @param saldoCliente Saldo del cliente.
	 * @param dineroJuego Dinero del juego.
	 * @throws ApuestaExcepcion Excepción en caso de que no tengan suficiente dinero.
	 */
	public void validarSaldosMinimos(double saldoCliente, double dineroJuego) throws ApuestaExcepcion {
	    if (saldoCliente < apuestaMin) {
	        throw new ApuestaExcepcion("Este cliente no puede jugar, tiene menos saldo que la apuesta mínima (" + apuestaMin + "$).");
	    }

	    if (dineroJuego < apuestaMin) {
	        throw new ApuestaExcepcion("Este juego no puede jugar, tiene menos dinero que la apuesta mínima (" + apuestaMin + "$).");
	    }
	}
}
