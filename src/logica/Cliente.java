package logica;

/**
 * Clase para los clientes del casino.
 * @author David Forero
 * @since 2.0
 */
public class Cliente {
	
	private int id;
	private String nombre;
	private int edad;
	private char genero;
	private boolean activo;
	private double saldo;
	
	
	/**
	 * Constructor mínimo para jugar, solo se necesitará saber el nombre, saldo y el id.
	 * @param id Id del cliente
	 * @param saldo Saldo del cliente
	 */
	public Cliente(int id, String nombre, double saldo) {
		this.id = id;
		this.nombre = nombre;
		this.saldo = saldo;
	}

	
	/**
	 * Constructor de los clientes.
	 * @param nombre Nombre completo del cliente (nombre y apellido)
	 * @param edad Edad del cliente (con rango de edad válido establecido)
	 * @param genero Género del cliente
	 * @param saldo Saldo del cliente
	 * @since 2.0
	 */
	public Cliente(String nombre, int edad, char genero, double saldo) {
		this.nombre = nombre;
		this.edad = edad;
		this.genero = genero;
		activo = true;
		this.saldo = saldo;
	}
	
	/**
	 * Constructor completo (para ser editado) de los clientes.
	 * @param nombre Nombre completo del cliente (nombre y apellido)
	 * @param edad Edad del cliente (con rango de edad válido establecido)
	 * @param genero Género del cliente
	 * @param saldo Saldo del cliente
	 * @since 2.0
	 */
	public Cliente(String nombre, int edad, char genero, double saldo, int id, boolean activo) {
		this.id = id;
		this.nombre = nombre;
		this.edad = edad;
		this.genero = genero;
		this.activo = activo;
		this.saldo = saldo;
	}


	public int getEdad() {
		return edad;
	}


	public char getGenero() {
		return genero;
	}


	public int getId() {
		return id;
	}


	public String getNombre() {
		return nombre;
	}


	public double getSaldo() {
		return saldo;
	}


	public boolean isActivo() {
		return activo;
	}


	public void setActivo(boolean baja) {
		this.activo = baja;
	}


	public void setEdad(int edad) {
		this.edad = edad;
	}


	public void setGenero(char genero) {
		this.genero = genero;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	
	
	
	
	
	
	
}

