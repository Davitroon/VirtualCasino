package logic;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

import javax.swing.UIManager;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import exceptions.MessageException;
import logic.Controller;
import logic.Model;
import logic.Session;
import logic.Validator;
import ui.ConnectUI;
import ui.HomeUI;
import ui.ProfileUI;

/**
 * Clase encargada de instanciar las clases más importantes y ejecutar el programa.
 * @author David
 * @since 3.0
 */
public class Launcher {
		
    public static void main(String[] args) {
    	MessageException mensajeExcepcion = new MessageException();
    	Model modelo = null;
    	
        try {      	
            modelo = new Model(mensajeExcepcion);          
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            
        } catch (SQLSyntaxErrorException e) {
        	mensajeExcepcion.mostrarError(e, "Base de datos no encontrada, utiliza la guía de instalación y revisa que el nombre de la base de datos sea correcto.\nConsultar la consola para más detalles.");
        
        } catch (CommunicationsException e) {
        	mensajeExcepcion.mostrarError(e, "Asegúrate de que el servicio MySQL80 esté en ejecución y de no haber modificado el host en el código del programa.\nConsultar la consola para más detalles.");
        
        } catch (SQLException e) {
            if (e.getErrorCode() == 1045) {
            	mensajeExcepcion.mostrarError(e, "Usuario o contraseña incorrectos.\nConsultar la consola para más información.");
            
            } else {
            	mensajeExcepcion.mostrarError(e, "Ha ocurrido un error al conectar con la BD.\nConsultar la consola para más información.");
            }
        
        } catch (Exception e) {
        	mensajeExcepcion.mostrarError(e, "Ha ocurrido un error inesperado.\nConsultar la consola para más información.");
        }
        
        Validator validador = new Validator();
        Controller controlador = new Controller(modelo, validador);
        HomeUI menu = new HomeUI(modelo, controlador, validador);
        Session sesion = new Session(menu, null, controlador, modelo);
        ConnectUI conectarse = new ConnectUI(modelo, controlador, sesion, validador, menu);
        ProfileUI perfil = new ProfileUI(menu, controlador, modelo);
        
        menu.setPerfil(perfil);
        menu.setConectarse(conectarse);
        sesion.setConectarse(conectarse);
        sesion.comprobarInicio();
	}
	

}