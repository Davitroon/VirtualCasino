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
 * @author deivi Davitroon
 * @since 3.3
 */
public class UserDAO {
	private ExceptionMessage exceptionMessage;

	public UserDAO(ExceptionMessage exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	// --------------------- CREATE ---------------------

	/**
	 * Method that adds a user to the DB.
	 * 
	 * @param user            User object to add.
	 * @param rememberSession True if it has been indicated to save the login
	 *                        session.
	 * @return The User object with the generated ID.
	 * @throws MailException                            Exception thrown if the
	 *                                                  email is invalid.
	 * @throws SQLIntegrityConstraintViolationException
	 * @since 3.0
	 */
	public User addUser(User user, boolean rememberSession, Connection connection)
			throws MailException, SQLIntegrityConstraintViolationException {
		String query = "INSERT INTO users (username, user_password, email) VALUES (?, ?, ?);";

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
				toggleRememberLogin(user.getName(), rememberSession, connection);
			}

			user = new User(generatedId, rset.getString("username"), rset.getString("user_password"),
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
	 * Executes a query to retrieve all users who have the "remember_login" flag
	 * set. This method is intended for checking if any user should be automatically
	 * logged in.
	 *
	 * @param connection Database connection to use for the query
	 * @return ResultSet containing users with remember_login = 1
	 * @since 3.0
	 */
	public ResultSet checkRememberLogin(Connection connection) {

		ResultSet rset = null;
		
		try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE remember_login = 1");) {
			rset = stmt.executeQuery();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while performing a read operation.\nCheck the console for more information.");
		}

		return rset;
	}

	/**
	 * Queries a user from the database. The name and password must be exactly the
	 * same.
	 * 
	 * @param name Username.
	 * @return Result of the SQL query.
	 * @since 3.0
	 */
	public ResultSet queryUser(User user, Connection connection) {
		String query = "SELECT * FROM users WHERE username = ? AND password = ?";
		ResultSet rset;

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, user.getName());
			stmt.setString(2, user.getPassword());

			rset = stmt.executeQuery();

			// If a user with that password was found
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
	 * Method that will toggle a user's automatic login.
	 * 
	 * @param name     Username to toggle.
	 * @param activate True if the session has been marked to be remembered.
	 * @since 3.0
	 */
	public void toggleRememberLogin(String name, boolean activate, Connection connection) {
		try {
			// Remove the flag from all other users who have remember session checked
			try (PreparedStatement stmt2 = connection
					.prepareStatement("UPDATE users SET remember_login = FALSE WHERE remember_login = TRUE")) {
				stmt2.executeUpdate();
			}

			if (activate) {
				// Set this user to remember session
				try (PreparedStatement stmt3 = connection
						.prepareStatement("UPDATE users SET remember_login = TRUE WHERE username = ?")) {
					stmt3.setString(1, name);
					stmt3.executeUpdate();
				}
			}

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while modifying a user.\nCheck the console for more information.");
		}
	}
	
	/**
	 * Updates the last access time for a user in the program.
	 * 
	 * @param name Username to update.
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
	 * 
	 * @param connection
	 * @since 3.3
	 */
	public void deleteUser(int userId, Connection connection) {
		String query = "DELETE FROM customers WHERE id = ?;";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, userId);

			stmt.executeUpdate();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while deleting an entry from the table users.\nCheck the console for more information.");
		}
	}
}