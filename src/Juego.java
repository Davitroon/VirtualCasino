
/**
 * Clase abstracta que da formato a los juegos.
 * Cada juego tendrá su propia lógica, metodos propios y atributos.
 * @author David Forero
 */
public abstract class Juego {
	
	private String tipo;
	private boolean activo = true;
	private double dinero;
	
	
	/**
	 * Constructor de los juegos.
	 * @param dinero Dinero que contará el juego para las apuestas.
	 * @param tipo Tipo de juego que será.
	 */
	public Juego(double dinero, String tipo) {
		this.dinero = dinero;
		this.tipo = tipo;
	}

	
	/**
	 * Cada tipo de juego calcula mediante sus propias reglas la modificación de la apuesta, en base si el cliente pierde o gana.
	 * @param apuesta Apuesta del cliente.
	 * @return Apuesta modificada.
	 */
	public abstract double jugar(double apuesta);
	
	
	/**
	 * Muestra los detalles de los juego.
	 * @param id ID del juego.
	 * @return Texto con información del juego.
	 */
	public String imprimirJuego(int id) {
		return String.format("%-5s|%-15s|%-7b|%10.2f $", id + 1, tipo, activo, dinero);
	}


	public void setActivo(boolean activo) {
		this.activo = activo;
	}


	public double getDinero() {
		return dinero;
	}


	public void setDinero(double dinero) {
		this.dinero = dinero;
	}



	public String getTipo() {
		return tipo;
	}
	
	
	public boolean isActivo() {
		return activo;
	}
	
	
}