package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import controller.DataBaseController;
import controller.MainController;
import controller.ViewController;

/**
 * Manages user login sessions in the application.
 * <p>
 * This class handles checking for users who have opted to be remembered for
 * automatic login and stores the currently logged-in user.
 * </p>
 * 
 * @author Davitroon
 * @since 3.0
 */
public class Session {

	private User currentUser;

	/**
	 * Checks at startup if any user has the "remember login" flag set.
	 * <p>
	 * If such a user exists, this method will automatically log them in and open
	 * the main menu window. Otherwise, it opens the login/connection window.
	 * </p>
	 * 
	 * @param dbController The database controller used to query the "remember
	 *                     login" flag.
	 * @param controller   The main controller of the application, used to access
	 *                     the view manager.
	 * @since 3.0
	 */
	public void checkStartup(DataBaseController dbController, MainController controller) {
		ResultSet rset = dbController.checkRememberLogin();
		ViewController viewManager = controller.getViewController();

		try {
			if (rset.next()) {
				// Load user data from the ResultSet
				currentUser = new User(rset.getInt("id"), rset.getString("username"), rset.getString("password"),
						rset.getString("email"), rset.getString("last_access"), true);

				rset.close();
				viewManager.openWindow(viewManager.getHomeUI());

			} else {
				// No remembered user; show the login window
				viewManager.openWindow(viewManager.getConnectUI());
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	/**
	 * Returns the currently logged-in user.
	 * 
	 * @return The current User object, or null if no user is logged in.
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 * Sets the currently logged-in user.
	 * 
	 * @param currentUser The User object to set as the current session user.
	 */
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

}
