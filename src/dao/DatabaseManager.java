package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import exceptions.MailException;
import exceptions.MessageException;
import model.Blackjack;
import model.Client;
import model.Game;
import model.Session;
import model.Slotmachine;
import model.User;

/**
 * MVC Model class. Connects to the DB and manages information.
 * 
 * @author Davitroon
 * @since 3.0
 */
public class DatabaseManager {

	private DataBaseConnector dbConnection;
	private Session session;
	private MessageException exceptionMessage;
	private GameDAO gameDAO;
	private ClientDAO clientDAO;
	private UserDAO userDAO;

	/**
	 * Model constructor, where it makes the connection to the database.
	 * 
	 * @param exceptionMessage Class that handles the exception warnings.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @since 3.0
	 */
	public DatabaseManager(MessageException exceptionMessage, Session session)
			throws SQLException, ClassNotFoundException {
		this.exceptionMessage = exceptionMessage;
		this.session = session;
		dbConnection = new DataBaseConnector();
		gameDAO = new GameDAO(exceptionMessage);
		clientDAO = new ClientDAO(exceptionMessage);

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
			stmt.setInt(5, session.getCurrentUser().getId());

			stmt.executeUpdate();
			stmt.close();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while inserting a game session.\nCheck the console for more information.");
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
			stmt.setInt(1, session.getCurrentUser().getId());
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
			stmt.setInt(1, session.getCurrentUser().getId());
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

	public void modifyClient(Client client) {
		clientDAO.modifyClient(client, dbConnection.getConnection());
	}

	public void addClient(Client client) {
		clientDAO.addClient(client, session.getCurrentUser(), dbConnection.getConnection());
	}

	public void closeConnection() {
		dbConnection.closeConnection();
	}

	public void addGame(Game game) {
		gameDAO.addGame(game, session.getCurrentUser(), dbConnection.getConnection());
	}

	public void updateGame(Game game) {
		gameDAO.updateGame(game, dbConnection.getConnection());
	}

	public void updateGameBalance(Game game) {
		gameDAO.updateGameBalance(game, dbConnection.getConnection());
	}

	public ResultSet checkRememberLogin() {
		return userDAO.checkRememberLogin(dbConnection.getConnection());
	}

	public ResultSet queryUser(User user) {
		return userDAO.queryUser(user, dbConnection.getConnection());
	}

	public User addUser(User user, boolean rememberSession)
			throws SQLIntegrityConstraintViolationException, MailException {
		try {
			return userDAO.addUser(user, rememberSession, dbConnection.getConnection());

		} catch (SQLIntegrityConstraintViolationException e) {
			throw new SQLIntegrityConstraintViolationException(
					"The user " + user.getName() + " is already registered.");

		} catch (MailException e) {
			throw new MailException("Invalid email domain");
		}
	}

	public void toggleRememberLogin(String name, boolean activate) {
		userDAO.toggleRememberLogin(name, activate, dbConnection.getConnection());
	}

	public void updateLastAccess(String name) {
		userDAO.updateLastAccess(name, dbConnection.getConnection());
	}

	public void deleteClient(int clientId) {
		clientDAO.deleteClient(clientId, dbConnection.getConnection());
	}

	public void deleteUser(int userId) {
		userDAO.deleteUser(userId, dbConnection.getConnection());
	}

	public ResultSet queryClients(boolean onlyActive) {
		return clientDAO.queryClients(onlyActive, dbConnection.getConnection(), session.getCurrentUser().getId());
	}

}