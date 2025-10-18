package logic;

/**
 * Clase abstracta que da formato a los juegos.
 * Cada juego tendrá su propia lógica, metodos propios y atributos.
 * @author David Forero
 * @since 2.0
 */
public abstract class Game {
	
	private int id;
	private String tipo;
	private boolean activo = true;
	private double dinero;
	
	
	/**
	 * Constructor de los juegos.
	 * @param dinero Dinero que contará el juego para las apuestas
	 * @param tipo Tipo del juego
	 * @since 2.0
	 */
	public Game(double dinero, String tipo) {
		this.dinero = dinero;
		this.tipo = tipo;
	}
	
	
	/**
	 * Constructor minimo para jugar, solo se necesitará saber el dinero y el id.
	 * @param id
	 * @param dinero
	 */
	public Game(int id, double dinero, String tipo) {
		this.id = id;
		this.dinero = dinero;
		this.tipo = tipo;
	}

	
	/**
	 * Constructor completo (para ser editado) de los juegos.
	 * @param id Id del juego
	 * @param tipo Tipo del juego
	 * @param activo Estado del juego
	 * @param dinero Dinero que contará el juego para las apuestas
	 * @since 2.0
	 */
	public Game(int id, String tipo, boolean activo, double dinero) {
		this.id = id;
		this.dinero = dinero;
		this.activo = activo;
		this.tipo = tipo;
	}


	public double getDinero() {
		return dinero;
	}
	
	
	public int getId() {
		return id;
	}


	public String getTipo() {
		return tipo;
	}


	public boolean isActivo() {
		return activo;
	}



	/**
	 * Método para jugar al juego. Cada tipo de juego calcula mediante sus propias reglas la modificación de la apuesta, en base si el cliente pierde o gana.
	 * @param apuesta Apuesta del cliente.
	 * @return Apuesta modificada.
	 * @since 2.0
	 */
	public abstract double jugar(double apuesta);
	
	
	public void setActivo(boolean activo) {
		this.activo = activo;
	}


	public void setDinero(double dinero) {
		this.dinero = dinero;
	}
	
	
	
	
}