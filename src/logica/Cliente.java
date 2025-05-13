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
	 * Imprime los datos del cliente.
	 * @param id ID del cliente
	 * @return Información acerca del cliente
	 * @since 2.0
	 */
	public String imprimirCliente(int id) {
		return String.format("%-5s|%-24s|%-6d|%-7c|%-6b|%10.2f $", id + 1, nombre, edad, genero, activo, saldo);
		
	}


	public String getNombre() {
		return nombre;
	}


	public int getEdad() {
		return edad;
	}


	public char getGenero() {
		return genero;
	}


	public boolean isActivo() {
		return activo;
	}


	public double getSaldo() {
		return saldo;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public void setEdad(int edad) {
		this.edad = edad;
	}


	public void setGenero(char genero) {
		this.genero = genero;
	}


	public void setActivo(boolean baja) {
		this.activo = baja;
	}


	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}


	public int getId() {
		return id;
	}
	
	
	
	
	
	
	
	
}

