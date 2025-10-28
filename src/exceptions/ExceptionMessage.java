package exceptions;

import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Utility class to handle exceptions by displaying a user-friendly error
 * message and logging technical details to the console.
 * <p>
 * This class is intended to be instantiated first in the program so that it
 * can handle exceptions even before the model or other components are fully
 * initialized.
 * </p>
 * 
 * @author Davitroon
 * @since 3.0
 */
public class ExceptionMessage {

    /**
     * Displays an error message dialog to the user and logs detailed exception
     * information to the console.
     * <p>
     * If the exception is an instance of {@link SQLException}, the SQL state and
     * error code are also printed.
     * <p>
     * After showing the error, the program will terminate to prevent continued
     * execution with an unstable state.
     * 
     * @param e           The exception that occurred.
     * @param userMessage The message to display to the user explaining the error.
     * @since 3.0
     */
    public void showError(Exception e, String userMessage) {
        JOptionPane.showMessageDialog(null, userMessage, "DB Connection Error", JOptionPane.ERROR_MESSAGE);
        System.out.println("(" + e.getClass().getSimpleName() + ") " + e.getMessage());
        e.printStackTrace();

        if (e instanceof SQLException sqlEx) {
            System.out.println("SQLState: " + sqlEx.getSQLState() + " - Error Code: " + sqlEx.getErrorCode());
        }

        // Terminate the application after a critical error
        System.exit(1);
    }
}
