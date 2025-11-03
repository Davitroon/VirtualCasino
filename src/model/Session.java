package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import controller.DataBaseController;

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
	 * Checks if there is any user with the "remember login" option enabled.
	 * <p>
	 * At application startup, this method queries the database to find a user whose
	 * "remember login" flag is set. If such a user exists, their information is
	 * loaded into the current session, allowing automatic login. If no user is found,
	 * the application should proceed to the standard login/connection window.
	 * </p>
	 *
	 * @param dbController The database controller used to query users with
	 *                     "remember login" enabled.
	 * @return {@code true} if a user with "remember login" was found and loaded;
	 *         {@code false} otherwise.
	 * @since 3.0
	 */
	public boolean isRememberLogin(DataBaseController dbController) {
		ResultSet rset = dbController.checkRememberLogin();

		try {
			if (rset.next()) {
				// Load user data from the ResultSet
				currentUser = new User(rset.getInt("id"), rset.getString("username"), rset.getString("password"),
						rset.getString("email"), rset.getString("last_access"), true);

				rset.close();
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		
		return false;
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
