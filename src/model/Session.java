package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import controller.DataBaseController;
import controller.MainController;
import controller.ViewController;

/**
 * Class that handles user login sessions.
 */
public class Session {

	private User currentUser;

	/**
	 * Checks if any user has been flagged to remember the login session. If so, it
	 * will take them to the menu with their session activated.
	 * 
	 * @since 3.0
	 */
	public void checkStartup(DataBaseController dbController, MainController controller) {
		ResultSet rset = dbController.checkRememberLogin();
		ViewController viewManager = controller.getViewController();

		try {
			if (rset.next()) {
				currentUser = new User(rset.getInt("id"), rset.getString("username"), rset.getString("user_password"),
						rset.getString("email"), rset.getString("last_access"), true);

				rset.close();
				viewManager.openWindow(viewManager.getHomeUI());

			} else {
				viewManager.openWindow(viewManager.getConnectUI());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

}