package controller;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

import javax.swing.UIManager;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import dao.DBConnection;
import dao.DBManagement;
import exceptions.MessageException;
import ui.ConnectUI;
import ui.HomeUI;
import ui.ProfileUI;

/**
 * Class responsible for instantiating the most important classes and executing
 * the program.
 * 
 * @author David
 * @since 3.0
 */
public class Launcher {

	public static void main(String[] args) {
		MessageException exceptionMessage = new MessageException();
		DBConnection dbConnection;
		DBManagement dbManagement = null;
		Session session;

		try {
			dbConnection = new DBConnection(exceptionMessage);
			dbManagement = new DBManagement(exceptionMessage, dbConnection, session);
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

		Validator validator = new Validator();
		Controller controller = new Controller(dbManagement, validator);
		HomeUI mainMenu = new HomeUI(dbManagement, controller, validator);
		Session session = new Session(mainMenu, null, controller, dbManagement);
		ConnectUI connect = new ConnectUI(dbManagement, controller, session, validator, mainMenu);
		ProfileUI profile = new ProfileUI(mainMenu, controller, dbManagement);

		mainMenu.setProfile(profile);
		mainMenu.setConnect(connect);
		session.setConnect(connect);
		session.checkStartup();
	}
}