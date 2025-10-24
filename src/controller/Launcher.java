package controller;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

import javax.swing.UIManager;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import dao.DatabaseManager;
import exceptions.ExceptionMessage;
import model.Session;

/**
 * Class responsible for instantiating the most important classes and executing
 * the program's main entry point. It handles initial setup, including database
 * connection and setting the application's Look and Feel (L&F).
 *
 * @author David
 * @since 3.0
 */
public class Launcher {

	public static void main(String[] args) {
		ExceptionMessage exceptionMessage = new ExceptionMessage();
		Session session = new Session();
		DatabaseManager dbManager = null;

		try {
			dbManager = new DatabaseManager(exceptionMessage);
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

		} catch (SQLSyntaxErrorException e) {
			exceptionMessage.showError(e,
					"Database not found, use the installation guide and check that the database name is correct.\nCheck the console for more details.");

		} catch (CommunicationsException e) {
			exceptionMessage.showError(e,
					"Make sure the MySQL80 service is running and that you haven't modified the host in the program code.\nCheck the console for more details.");

		} catch (SQLException e) {
			if (e.getErrorCode() == 1045) {
				exceptionMessage.showError(e,
						"Incorrect username or password.\nCheck the console for more information.");

			} else {
				exceptionMessage.showError(e,
						"An error occurred while connecting to the DB.\nCheck the console for more information.");
			}

		} catch (Exception e) {
			exceptionMessage.showError(e, "An unexpected error occurred.\nCheck the console for more information.");
		}

		MainController controller = new MainController(dbManager);

		session.checkStartup(controller.getDataBaseController(), controller);
	}
}