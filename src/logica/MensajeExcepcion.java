package logica;

import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 * Clase que mostrará un mensaje de error en caso de excepciones. Es la primera clase en ser instanciada en el programa, para pueda llamarse antes de haber creado el modelo.
 */
public class MensajeExcepcion {

	/**
	 * Método para mostrar un mensaje de error cuando ocurra una excepción.
	 * @param e Excepción
	 * @param mensajeUsuario Mensaje de error para mostrar al usuario
	 * @since 3.0
	 */
    public void mostrarError(Exception e, String mensajeUsuario) {
        JOptionPane.showMessageDialog(null, mensajeUsuario, "Error de Conexión con BD", JOptionPane.ERROR_MESSAGE);
        System.out.println("(" + e.getClass().getSimpleName() + ") " + e.getMessage());
        e.printStackTrace();

        if (e instanceof SQLException sqlEx) {
            System.out.println("SQLState: " + sqlEx.getSQLState() + " - Código de error: " + sqlEx.getErrorCode());
        }
        
        // Ante un error, prefiero cerrar el programa y que el usuario no pueda usar la aplicación hasta que se haya solucionado.
        System.exit(1);
    }
}
