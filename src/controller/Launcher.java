package controller;

import java.sql.SQLException;

import javax.swing.UIManager;

import dao.DatabaseManager;
import exceptions.ExceptionMessage;
import model.Session;

/**
 * The {@code Launcher} class is the main entry point of the application. It
 * initializes the essential components, establishes the database connection,
 * sets the Look and Feel (L&F), and launches the main controller.
 * <p>
 * This class is responsible for handling startup exceptions related to the
 * database connection and application configuration.
 * </p>
 *
 * <p>
 * <b>Main responsibilities:</b>
 * </p>
 * <ul>
 * <li>Initialize {@link DatabaseManager}, {@link Session}, and
 * {@link ExceptionMessage}.</li>
 * <li>Connect to the database using
 * {@link DatabaseManager#initializeClasses()}.</li>
 * <li>Configure Swing's Look and Feel.</li>
 * <li>Start the application via {@link MainController}.</li>
 * </ul>
 *
 * @author Davitroon
 * @since 3.0
 */
public class Launcher {

	/**
	 * Main method and application entry point.
	 * <p>
	 * It initializes the required controllers and managers, sets the Nimbus Look
	 * and Feel, and handles possible SQL and communication exceptions gracefully.
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
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

		} catch (SQLException e) {
			exceptionMessage.showError(e, "An SQL error occurred while connecting or initializing the database.\n"
					+ "Please check the console for details.");

		} catch (Exception e) {
			exceptionMessage.showError(e, "An unexpected error occurred.\nCheck the console for more information.");
		}

		// Initialize and start main controller
		MainController controller = new MainController(session);
		controller.initializeClasses(dbManager);
		ViewController viewController = controller.getViewController();

		// Check if a remembered login exists and start the session
		if (session.isRememberLogin(controller.getDataBaseController())) {
			viewController.switchPanel(viewController.getHomeUI());

		} else {
			viewController.switchPanel(viewController.getConnectUI());
		}

		viewController.getMainFrame().setVisible(true);
	}
}