package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import controller.Session;
import exceptions.MailException;
import exceptions.MessageException;
import model.Blackjack;
import model.Client;
import model.Game;
import model.Slotmachine;
import model.User;

/**
 * MVC Model class. Connects to the DB and manages information.
 * 
 * @author David
 * @since 3.0
 */
public class DBManagement {

	private DBConnection dbConnection;
	private Session session;
	private MessageException exceptionMessage;
	private GameDAO gameDAO;
	private ClientDAO clientDAO;

	/**
	 * Model constructor, where it makes the connection to the database.
	 * 
	 * @param exceptionMessage Class that handles the exception warnings.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @since 3.0
	 */
	public DBManagement(MessageException exceptionMessage, DBConnection dbConnection, Session session)
			throws SQLException, ClassNotFoundException {
		this.exceptionMessage = exceptionMessage;
		this.dbConnection = dbConnection;
		this.session = session;
		gameDAO = new GameDAO(exceptionMessage);
		clientDAO = new ClientDAO(exceptionMessage);
	}

	/**
	 * Updates the last access time for a user in the program.
	 * 
	 * @param name Username to update.
	 */
	public void updateLastAccess(String name) {
		String query = "UPDATE users SET last_access = NOW() WHERE username = ?";

		try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(query)) {
			stmt.setString(1, name);
			stmt.executeUpdate();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while modifying a user.\nCheck the console for more information.");
		}
	}


	/**
	 * Inserts a new game session into the database.
	 * 
	 * @param client    The client participating in the game session. Must have a
	 *                  valid ID.
	 * @param game      The game in which the session is taking place. Must have an
	 *                  ID and a type ("Blackjack" or "Slotmachine").
	 * @param betResult Resulting amount of the bet (positive if the client wins,
	 *                  negative if they lose).
	 */
	public void addGameSession(Client client, Game game, double betResult) {

		String query = "INSERT INTO game_sessions (customer_id, game_id, game_type, bet_result, user_profile) "
				+ "VALUES (?, ?, ?, ?, ?)";

		try {
			PreparedStatement stmt = dbConnection.getConnection().prepareStatement(query);
			stmt.setInt(1, client.getId());
			stmt.setInt(2, game.getId());
			stmt.setString(3, game.getType());
			stmt.setDouble(4, betResult);
			stmt.setInt(5, session.getCurrentUser());

			stmt.executeUpdate();
			stmt.close();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while inserting a game session.\nCheck the console for more information.");
		}
	}

	/**
	 * Method that adds a user to the DB.
	 * 
	 * @param user            User object to add.
	 * @param rememberSession True if it has been indicated to save the login
	 *                        session.
	 * @return The User object with the generated ID.
	 * @throws MailException                            Exception thrown if the
	 *                                                  email is invalid.
	 * @throws SQLIntegrityConstraintViolationException
	 */
	public User addUser(User user, boolean rememberSession)
			throws MailException, SQLIntegrityConstraintViolationException {
		String query = "INSERT INTO users (username, user_password, email) VALUES (?, ?, ?);";

		try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(query,
				Statement.RETURN_GENERATED_KEYS)) {
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

			ResultSet rset = queryUser(user.getName());

			if (rememberSession) {
				toggleRememberLogin(user.getName(), rememberSession);
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

	/**
	 * Method that will toggle a user's automatic login.
	 * 
	 * @param name     Username to toggle.
	 * @param activate True if the session has been marked to be remembered.
	 */
	public void toggleRememberLogin(String name, boolean activate) {
		try {
			// Remove the flag from all other users who have remember session checked
			try (PreparedStatement stmt2 = dbConnection.getConnection()
					.prepareStatement("UPDATE users SET remember_login = FALSE WHERE remember_login = TRUE")) {
				stmt2.executeUpdate();
			}

			if (activate) {
				// Set this user to remember session
				try (PreparedStatement stmt3 = dbConnection.getConnection()
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
	 * Method to delete an entry from the database.
	 * 
	 * @param id    ID of the entry to delete.
	 * @param table Table from which the entry will be deleted.
	 * @since 3.0
	 */
	public void deleteData(int id, String table) {

		String query = "DELETE FROM " + table + " WHERE id = ?;";

		try {
			PreparedStatement stmt = dbConnection.getConnection().prepareStatement(query);
			stmt.setInt(1, id);

			stmt.executeUpdate();
			stmt.close();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while deleting an entry from the table " + table
							+ ".\nCheck the console for more information.");
		}
	}

	/**
	 * Method to delete all entries from a table.
	 * 
	 * @param table Table to delete its entries from.
	 * @since 3.0
	 */
	public void deleteTableData(String table) {
		String query = "DELETE FROM " + table + " WHERE user_profile = ?";

		try {
			PreparedStatement stmt = dbConnection.getConnection().prepareStatement(query);
			stmt.setInt(1, session.getCurrentUser());
			stmt.executeUpdate();
			stmt.close();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while deleting entries from the table " + table
							+ ".\nCheck the console for more information.");
		}

	}

	/**
	 * Executes a query to the DB received by parameter. Recommended for very
	 * specific queries.
	 * 
	 * @param query Query to execute
	 * @return Result of the SQL query.
	 */
	public ResultSet specificQuery(String query) {

		ResultSet rset = null;
		PreparedStatement stmt;
		try {
			stmt = dbConnection.getConnection().prepareStatement(query);
			rset = stmt.executeQuery();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while performing a read operation.\nCheck the console for more information.");
		}

		return rset;
	}

	/**
	 * Method to query all entries in a table from the database.
	 * 
	 * @param table      Table to query.
	 * @param onlyActive Show entries that are only active.
	 * @return ResultSet (SQL Query Result)
	 * @since 3.0
	 */
	public ResultSet queryTableData(String table, boolean onlyActive) {

		String query = "SELECT * FROM " + table + " WHERE user_profile = ?";
		ResultSet rset = null;

		if (onlyActive) {
			query += " AND active_status = 1";
		}

		try {
			PreparedStatement stmt = dbConnection.getConnection().prepareStatement(query);
			stmt.setInt(1, session.getCurrentUser());
			rset = stmt.executeQuery();

		} catch (SQLException e) {
			exceptionMessage.showError(e, "An error occurred in the DB connection while querying the table " + table
					+ ".\nCheck the console for more information."); // Assumes 'mensajeExcepcion' is 'exceptionMessage'

		}

		return rset;
	}

	/**
	 * Method to query a single entry in a table from the database.
	 * 
	 * @param table Table to query
	 * @param id    ID of the element to query
	 * @return ResultSet (SQL Query Result)
	 * @since 3.0
	 */
	public ResultSet querySingleData(String table, int id) {

		String query = "SELECT * FROM " + table + " WHERE id = ?";
		ResultSet rset = null;

		try {
			PreparedStatement stmt = dbConnection.getConnection().prepareStatement(query);
			stmt.setInt(1, id);
			rset = stmt.executeQuery();
			rset.next();

		} catch (SQLException e) {
			exceptionMessage.showError(e, "An error occurred in the DB connection while querying the table " + table
					+ ".\nCheck the console for more information.");
		}

		return rset;
	}

	/**
	 * Queries a user from the database. The name and password must be exactly the
	 * same.
	 * 
	 * @param name Username.
	 * @return Result of the SQL query.
	 */
	public ResultSet queryUser(String name) {
		String query = "SELECT * FROM users WHERE username = ?";
		ResultSet rset;

		try {
			PreparedStatement stmt = dbConnection.getConnection().prepareStatement(query);
			stmt.setString(1, name);

			rset = stmt.executeQuery();

			// If a user with that password was found (Note: Original comment said
			// 'password' but logic uses 'username')
			if (rset.next()) {
				return rset;
			}

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while querying the users table.\nCheck the console for more information.");
		}

		return null;
	}

	

	/**
	 * Method to modify only a client's balance in the database.
	 * 
	 * @param client Client to modify.
	 * @since 3.0
	 */
	public void updateClientBalance(Client client) {
		String query = "UPDATE customers SET balance = ? WHERE id = ?;";

		try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(query)) {
			stmt.setDouble(1, client.getBalance());
			stmt.setInt(2, client.getId());

			stmt.executeUpdate();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while modifying the client's balance.\nCheck the console for more information.");
		}
	}

	/**
	 * * Method that adds default users and games when creating a user.
	 */
	public void addDefaultUser() {
			clientDAO.addClient(new Client("user", 32, 'O', 2000), session.getCurrentUser(), dbConnection.getConnection());	
			gameDAO.addGame(new Blackjack(10000), session.getCurrentUser(), dbConnection.getConnection());
			gameDAO.addGame(new Slotmachine(10000), session.getCurrentUser(), dbConnection.getConnection());
	}

	public GameDAO getGameDAO() {
		return gameDAO;
	}

	public ClientDAO getClientDAO() {
		return clientDAO;
	}
	
	
}