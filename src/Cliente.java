
/**
 * Clase para los clientes del casino.
 * @author David Forero
 */
public class Cliente {
	
	private String nombre;
	private int edad;
	private char genero;
	private boolean baja;
	private double saldo;
	
	
	/**
	 * Constructor de los clientes, recibe datos validados.
	 * @param nombre Nombre completo del cliente (nombre y apellido).
	 * @param edad Edad del cliente (con rango de edad válido establecido).
	 * @param genero Género del cliente.
	 * @param baja El cliente esta de baja o no.
	 * @param saldo Saldo del cliente.
	 */
	public Cliente(String nombre, int edad, char genero, boolean baja, double saldo) {
		this.nombre = nombre;
		this.edad = edad;
		this.genero = genero;
		this.baja = baja;
		this.saldo = saldo;
	}

	
	/**
	 * Imprime los datos del cliente.
	 * @param id ID del cliente.
	 * @return Información acerca del cliente.
	 */
	public String imprimirCliente(int id) {
		return String.format("%-5s|%-24s|%-6d|%-7c|%-6b|%10.2f $", id + 1, nombre, edad, genero, baja, saldo);
		
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


	public boolean isBaja() {
		return baja;
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


	public void setBaja(boolean baja) {
		this.baja = baja;
	}


	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	
	
	
	
	
}

