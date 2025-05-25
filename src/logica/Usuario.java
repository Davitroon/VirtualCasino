package logica;

public class Usuario {

	private String nombre;
	private String contrasena;
	private String correo;
	private String ultimoAcceso;
	private boolean recordarSesion;
	
	
	
	public Usuario(String nombre, String contraseña, String correo, String ultimoAcceso, boolean recordarSesion) {
		this.nombre = nombre;
		this.contrasena = contraseña;
		this.correo = correo;
		this.ultimoAcceso = ultimoAcceso;
		this.recordarSesion = recordarSesion;
	}
	
	
	public Usuario(String nombre, String contraseña) {
		this.nombre = nombre;
		this.contrasena = contraseña;
	}


	public Usuario(String nombre, String contraseña, String correo, boolean recordarSesion) {
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
	
	
	
}
