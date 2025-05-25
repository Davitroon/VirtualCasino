package logica;



import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;

import ventanas.Conectarse;
import ventanas.MenuPrincipal;

/**
 * Clase que maneja el inicio de sesión de los usuarios.
 */
public class Sesion {
	
	private MenuPrincipal menu;
	private Conectarse conectarse;
	private Controlador controlador;
	private Modelo modelo;

	public Sesion(MenuPrincipal menu, Conectarse conectarse, Controlador controlador, Modelo modelo) {
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
				iniciarSesion(new Usuario(rset.getInt("id") ,rset.getString("username"), rset.getString("user_password"), rset.getString("email"), 
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
	public void iniciarSesion (Usuario usuario, JFrame ventanaActual) {
		modelo.actualizarUltimoAcceso(usuario.getNombre());
		menu.setUsuario(usuario);
		menu.getPerfil().actualizarDatos(usuario);
		modelo.setUsuarioActual(usuario.getId());
		controlador.cambiarVentana(ventanaActual, menu);
	}


	public void setConectarse(Conectarse conectarse) {
		this.conectarse = conectarse;
	}
}
