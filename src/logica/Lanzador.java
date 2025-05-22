package logica;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

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
	
	
    public static void main(String[] args) {
        try {
            modelo = new Modelo();
            controlador = new Controlador(modelo);
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            menu = new MenuPrincipal(modelo, controlador);
            menu.comprobarPartidas();
            menu.setVisible(true);
            
        } catch (SQLSyntaxErrorException e) {
            mostrarError(e, "Base de datos no encontrada, utiliza la guía de instalación y revisa que el nombre de la base de datos sea correcto.\nConsultar la consola para más detalles.");
        
        } catch (CommunicationsException e) {
            mostrarError(e, "Asegúrate de que el servicio MySQL80 esté en ejecución y de no haber modificado el puerto y host en el código del programa.\nConsultar la consola para más detalles.");
        
        } catch (SQLException e) {
            if (e.getErrorCode() == 1045) {
                mostrarError(e, "Usuario o contraseña incorrectos.");
            
            } else {
                mostrarError(e, "Ha ocurrido un error al conectar con la BD.\nConsultar consola para más información.");
            }
        
        } catch (Exception e) {
            mostrarError(e, "Ha ocurrido un error inesperado.\nConsultar consola para más información.");
        }
	}
	
	/**
	 * Método para mostrar un mensaje de error cuando ocurra una excepción.
	 * @param e Excepción
	 * @param mensajeUsuario Mensaje de error para mostrar al usuario
	 * @since 3.0
	 */
    private static void mostrarError(Exception e, String mensajeUsuario) {
        JOptionPane.showMessageDialog(null, mensajeUsuario, "Error de Conexión con BD", JOptionPane.ERROR_MESSAGE);
        System.out.println("(" + e.getClass().getSimpleName() + ") " + e.getMessage());
        e.printStackTrace();

        if (e instanceof SQLException sqlEx) {
            System.out.println("SQLState: " + sqlEx.getSQLState() + " - Código de error: " + sqlEx.getErrorCode());
        }
    }
}