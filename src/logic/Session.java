package logic;



import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;

import logic.Controller;
import logic.Model;
import logic.User;
import ui.ConnectUI;
import ui.HomeUI;

/**
 * Clase que maneja el inicio de sesión de los usuarios.
 */
public class Session {
	
	private HomeUI menu;
	private ConnectUI conectarse;
	private Controller controlador;
	private Model modelo;

	public Session(HomeUI menu, ConnectUI conectarse, Controller controlador, Model modelo) {
		this.menu = menu;
		this.conectarse = conectarse;
		this.controlador = controlador;
		this.modelo = modelo;
	}


	/**
	 * Comprobar si se ha indicado que se recuerde el inicio de sesión de algún usuario. Si es así, llevara al menú con su sesión activada.
	 */
	public void comprobarInicio () {
		ResultSet rset = modelo.consultaEspecifica("SELECT * FROM users WHERE remember_login = 1");
		
		try {
			if (rset.next()) {
				iniciarSesion(new User(rset.getInt("id") ,rset.getString("username"), rset.getString("user_password"), rset.getString("email"), 
						rset.getString("last_access"), true), menu);
				
			} else {
				conectarse.setVisible(true);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	
	
	/**
	 * Método que inicia sesión con un núevo usuario y lleva al usuario al menú principal.
	 * @param usuario Usuario nuevo.
	 * @param ventanaActual Ventana en la que se está iniciando sesión.
	 */
	public void iniciarSesion (User usuario, JFrame ventanaActual) {
		menu.setUsuario(usuario);
		modelo.setUsuarioActual(usuario.getId());
		controlador.cambiarVentana(ventanaActual, menu);
	}


	public void setConectarse(ConnectUI conectarse) {
		this.conectarse = conectarse;
	}
}
