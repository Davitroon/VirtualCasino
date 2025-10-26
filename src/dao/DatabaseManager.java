package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptions.ExceptionMessage;

/**
 * MVC Model class. Connects to the DB and manages information.
 * 
 * @author Davitroon
 * @since 3.0
 */
public class DatabaseManager {

	private DataBaseConnector dbConnection;
	private ExceptionMessage exceptionMessage;
	private GameDAO gameDAO;
	private ClientDAO clientDAO;
	private UserDAO userDAO;
	private GameSessionDAO gameSessionDAO;
	
	

	public DatabaseManager(ExceptionMessage exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	/**
	 * Model constructor, where it makes the connection to the database.
	 * 
	 * @param exceptionMessage Class that handles the exception warnings.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @since 3.0
	 */
	public void initializeClasses() throws SQLException, ClassNotFoundException {
		dbConnection = new DataBaseConnector();
		gameDAO = new GameDAO(exceptionMessage);
		clientDAO = new ClientDAO(exceptionMessage);
		gameSessionDAO = new GameSessionDAO(exceptionMessage);
		userDAO = new UserDAO(exceptionMessage);
	}

	public DataBaseConnector getDbConnection() {
		return dbConnection;
	}

	public GameDAO getGameDAO() {
		return gameDAO;
	}

	public ClientDAO getClientDAO() {
		return clientDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public GameSessionDAO getGameSessionDAO() {
		return gameSessionDAO;
	}

	/**
	 * 
	 * @param specificQuery
	 * @param connection
	 * @return
	 * @since 3.0
	 */
	public ResultSet specificQuery(String specificQuery, Connection connection) {

		ResultSet rset = null;

		try (PreparedStatement stmt = connection.prepareStatement(specificQuery)) {
			rset = stmt.executeQuery();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection. \nCheck the console for more information.");
		}

		return rset;
	}
}