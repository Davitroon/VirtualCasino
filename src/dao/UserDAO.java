package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import exceptions.MailException;
import exceptions.ExceptionMessage;
import model.User;

/**
 * DAO class for managing users in the database.
 * <p>
 * Provides CRUD operations and methods for handling login session preferences.
 * </p>
 * 
 * @author Davitroon
 * @since 3.3
 */
public class UserDAO {
	private ExceptionMessage exceptionMessage;

	/**
	 * Constructs a {@code UserDAO} with a reference to the exception handling
	 * system.
	 * 
	 * @param exceptionMessage class responsible for displaying or logging
	 *                         database-related errors.
	 * @since 3.3
	 */
	public UserDAO(ExceptionMessage exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	// --------------------- CREATE ---------------------

	/**
	 * Adds a new user to the database.
	 * 
	 * @param user            {@link User} object to add.
	 * @param rememberSession {@code true} if the login session should be
	 *                        remembered.
	 * @param connection      active database {@link Connection}.
	 * @return The {@link User} object with the generated ID.
	 * @throws MailException                            if the email is invalid.
	 * @throws SQLIntegrityConstraintViolationException if the username already
	 *                                                  exists.
	 * @since 3.3
	 */
	public User addUser(User user, boolean rememberSession, Connection connection)
			throws MailException, SQLIntegrityConstraintViolationException {
		String query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?);";

		try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, user.getName());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getEmail());

			stmt.executeUpdate();

			// Get the generated ID
			int generatedId = -1;
			try (ResultSet rs = stmt.getGeneratedKeys()) {
				if (rs.next()) {
					generatedId = rs.getInt(1);
				}
			}

			ResultSet rset = queryUser(user, connection);

			if (rememberSession) {
				enableRememberLogin(user.getName(), connection);
			}

			user = new User(generatedId, rset.getString("username"), rset.getString("password"),
					rset.getString("email"), rset.getString("last_access"), rememberSession);

			return user;

		} catch (SQLIntegrityConstraintViolationException e) {
			throw new SQLIntegrityConstraintViolationException(
					"The user " + user.getName() + " is already registered.");

		} catch (SQLException e) {
			if ("45000".equals(e.getSQLState())) {
				throw new MailException("Invalid email domain");

			} else {
				exceptionMessage.showError(e,
						"An error occurred in the DB connection while adding a user.\nCheck the console for more information.");
			}
		}
		return null;
	}

	// --------------------- READ ---------------------

	/**
	 * Retrieves users who have the "remember_login" flag set.
	 * <p>
	 * Used to automatically log in users who opted for remembering their session.
	 * </p>
	 * 
	 * @param connection active database {@link Connection}.
	 * @return {@link ResultSet} containing users with {@code remember_login = 1}.
	 * @since 3.3
	 */
	public ResultSet checkRememberLogin(Connection connection) {

		ResultSet rset = null;

		try {
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE remember_login = 1");
			rset = stmt.executeQuery();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while performing a read operation.\nCheck the console for more information.");
		}

		return rset;
	}

	/**
	 * Queries a user from the database using exact username and password.
	 * 
	 * @param user       {@link User} object containing the credentials to search.
	 * @param connection active database {@link Connection}.
	 * @return {@link ResultSet} containing the user if found, or {@code null}.
	 * @since 3.3
	 */
	public ResultSet queryUser(User user, Connection connection) {
		String query = "SELECT * FROM users WHERE username = ? AND password = ?";
		ResultSet rset;

		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, user.getName());
			stmt.setString(2, user.getPassword());

			rset = stmt.executeQuery();

			if (rset.next()) {
				return rset;
			}

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while querying the users table.\nCheck the console for more information.");
		}

		return null;
	}

	// --------------------- UPDATE ---------------------

	/**
	 * Enables automatic login for a user, and disables it for all other users.
	 * 
	 * @param name       Username to enable remembering session.
	 * @param connection active database {@link Connection}.
	 * @since 3.3
	 */
	public void enableRememberLogin(String name, Connection connection) {
		try {
			disableRememberLogin(connection);

			try (PreparedStatement stmt3 = connection
					.prepareStatement("UPDATE users SET remember_login = TRUE WHERE username = ?")) {
				stmt3.setString(1, name);
				stmt3.executeUpdate();
			}

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while modifying a user.\nCheck the console for more information.");
		}
	}

	/**
	 * Disables automatic login for all users.
	 * 
	 * @param connection active database {@link Connection}.
	 * @since 3.3
	 */
	public void disableRememberLogin(Connection connection) {
		try {
			try (PreparedStatement stmt2 = connection
					.prepareStatement("UPDATE users SET remember_login = FALSE WHERE remember_login = TRUE")) {
				stmt2.executeUpdate();
			}

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while modifying a user.\nCheck the console for more information.");
		}
	}

	/**
	 * Updates the last access timestamp for a user.
	 * 
	 * @param name       Username to update.
	 * @param connection active database {@link Connection}.
	 * @since 3.3
	 */
	public void updateLastAccess(String name, Connection connection) {
		String query = "UPDATE users SET last_access = NOW() WHERE username = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, name);
			stmt.executeUpdate();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while modifying a user.\nCheck the console for more information.");
		}
	}

	// --------------------- DELETE ---------------------

	/**
	 * Deletes a user from the database.
	 * 
	 * @param userId     ID of the user to delete.
	 * @param connection active database {@link Connection}.
	 * @since 3.3
	 */
	public void deleteUser(int userId, Connection connection) {
		String query = "DELETE FROM users WHERE id = ?;";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, userId);
			stmt.executeUpdate();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while deleting an entry from the users table.\nCheck the console for more information.");
		}
	}
}
