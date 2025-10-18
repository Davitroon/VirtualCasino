package exceptions;

import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 * Class that will display an error message in case of exceptions and close the program. 
 * It is the first class to be instantiated in the program, so it can be called before the model has been created.
 */
public class MessageException {

	/**
	 * Method to display an error message when an exception occurs.
	 * @param e Exception
	 * @param userMessage Error message to display to the user
	 * @since 3.0
	 */
    public void mostrarError(Exception e, String userMessage) {
        JOptionPane.showMessageDialog(null, userMessage, "DB Connection Error", JOptionPane.ERROR_MESSAGE);
        System.out.println("(" + e.getClass().getSimpleName() + ") " + e.getMessage());
        e.printStackTrace();

        if (e instanceof SQLException sqlEx) {
            System.out.println("SQLState: " + sqlEx.getSQLState() + " - Error Code: " + sqlEx.getErrorCode());
        }
        
        // Faced with an error, I prefer to close the program so the user cannot use the application until it has been fixed.
        System.exit(1);
    }
}