package logic;

/**
 * Clase para los diferentes perfiles que podrá tener el jugador.
 */
public class User {

	private int id;
	private String nombre;
	private String contrasena;
	private String correo;
	private String ultimoAcceso;
	private boolean recordarSesion;
	
	
	
	public User(int id, String nombre, String contraseña, String correo, String ultimoAcceso, boolean recordarSesion) {
		this.id = id;
		this.nombre = nombre;
		this.contrasena = contraseña;
		this.correo = correo;
		this.ultimoAcceso = ultimoAcceso;
		this.recordarSesion = recordarSesion;
	}
	
	
	public User(String nombre, String contraseña) {
		this.nombre = nombre;
		this.contrasena = contraseña;
	}


	public User(String nombre, String contraseña, String correo, boolean recordarSesion) {
		this.nombre = nombre;
		this.contrasena = contraseña;
		this.correo = correo;
		this.recordarSesion = recordarSesion;
	}


	public String getNombre() {
		return nombre;
	}


	public String getContrasena() {
		return contrasena;
	}


	public String getCorreo() {
		return correo;
	}


	public String getUltimoAcceso() {
		return ultimoAcceso;
	}


	public boolean isRecordarSesion() {
		return recordarSesion;
	}


	public int getId() {
		return id;
	}	
	
	
	
}
