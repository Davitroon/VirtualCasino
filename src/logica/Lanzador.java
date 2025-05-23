package logica;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import Excepciones.MensajeExcepcion;
import ventanas.MenuPrincipal;

/**
 * Clase encargada de instanciar las clases más importantes y ejecutar el programa.
 * @author David
 * @since 3.0
 */
public class Lanzador {
	
	private static Modelo modelo;
	private static Controlador controlador;
	private static MenuPrincipal menu;
	private static MensajeExcepcion mensajeExcepcion;
	
	
    public static void main(String[] args) {
    	mensajeExcepcion = new MensajeExcepcion();
    	
        try {      	
            modelo = new Modelo(mensajeExcepcion);          
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
        
        controlador = new Controlador(modelo);
        menu = new MenuPrincipal(modelo, controlador);
        menu.setVisible(true);
	}
	

}