package controller;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

import javax.swing.UIManager;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import dao.DatabaseManager;
import exceptions.ExceptionMessage;
import model.Session;

/**
 * The {@code Launcher} class is the main entry point of the application.
 * It initializes the essential components, establishes the database connection,
 * sets the Look and Feel (L&F), and launches the main controller.
 * <p>
 * This class is responsible for handling startup exceptions related to the
 * database connection and application configuration.
 * </p>
 *
 * <p><b>Main responsibilities:</b></p>
 * <ul>
 *   <li>Initialize {@link DatabaseManager}, {@link Session}, and {@link ExceptionMessage}.</li>
 *   <li>Connect to the database using {@link DatabaseManager#initializeClasses()}.</li>
 *   <li>Configure Swing's Look and Feel.</li>
 *   <li>Start the application via {@link MainController}.</li>
 * </ul>
 *
 * @author
 *     Davitroon
 * @since 3.0
 */
public class Launcher {

	/**
	 * Main method and application entry point.
	 * <p>
	 * It initializes the required controllers and managers, sets the Nimbus Look and Feel,
	 * and handles possible SQL and communication exceptions gracefully.
	 * </p>
	 *
	 * @param args command-line arguments (unused)
	 */
	public static void main(String[] args) {
		// Core components initialization
		ExceptionMessage exceptionMessage = new ExceptionMessage();
		Session session = new Session();
		DatabaseManager dbManager = new DatabaseManager(exceptionMessage);

		try {
			// Initialize DAOs and establish database connection
			dbManager.initializeClasses();

			// Set Nimbus Look & Feel
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

		} catch (SQLSyntaxErrorException e) {
			// Database not found or incorrect schema name
			exceptionMessage.showError(e,
					"Database not found. Please follow the installation guide and verify the database name.\nCheck the console for more details.");

		} catch (CommunicationsException e) {
			// MySQL service not reachable or host misconfigured
			exceptionMessage.showError(e,
					"Cannot communicate with the database. Make sure the MySQL80 service is running and the host configuration is correct.\nCheck the console for more details.");

		} catch (SQLException e) {
			// Handle authentication or general SQL errors
			if (e.getErrorCode() == 1045) {
				exceptionMessage.showError(e,
						"Incorrect database username or password.\nCheck the console for more information.");
			} else {
				exceptionMessage.showError(e,
						"An error occurred while connecting to the database.\nCheck the console for more information.");
			}

		} catch (Exception e) {
			// Catch any other unexpected errors during startup
			exceptionMessage.showError(e, "An unexpected error occurred.\nCheck the console for more information.");
		}

		// Initialize and start main controller
		MainController controller = new MainController(session);
		controller.initializeClasses(dbManager);

		// Check if a remembered login exists and start the session
		session.checkStartup(controller.getDataBaseController(), controller);
	}
}