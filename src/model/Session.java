package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;

import controller.MainController;
import controller.ViewController;
import dao.DatabaseManager;
import ui.ConnectUI;
import ui.HomeUI;

/**
 * Class that handles user login sessions.
 */
public class Session {

private User currentUser;

	/**
	 * Checks if any user has been flagged to remember the login session. If so, it
	 * will take them to the menu with their session activated.
	 */
	public void checkStartup(DatabaseManager dbManager, MainController controller) {
		ResultSet rset = dbManager.checkRememberLogin();
		ViewController viewManager = controller.getViewController();

		try {
			if (rset.next()) {
				currentUser = new User(rset.getInt("id"), rset.getString("username"), rset.getString("user_password"),
						rset.getString("email"), rset.getString("last_access"), true);

				viewManager.openWindow(viewManager.getMainMenuUI());

			} else {
				viewManager.openWindow(viewManager.getConnectUI());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public User getCurrentUser() {
		return currentUser;
	}

}