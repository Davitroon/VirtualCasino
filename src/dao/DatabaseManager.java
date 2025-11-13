package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptions.ExceptionMessage;

/**
 * Central manager class for database operations within the MVC architecture.
 * <p>
 * This class acts as an intermediary between the controllers and the specific
 * DAO classes ({@link GameDAO}, {@link ClientDAO}, {@link UserDAO},
 * {@link GameSessionDAO}). It initializes the database connection through
 * {@link DatabaseConnector} and provides access to all DAO instances.
 * </p>
 *
 * <p>
 * Additionally, it allows executing custom SQL queries directly using the
 * {@link #specificQuery(String, Connection)} method.
 * </p>
 *
 * @author Davitroon
 * @since 3.3
 */
public class DatabaseManager {

	private DatabaseConnector dbConnection;
	private DatabaseInitializer dbInitializer;
	private ExceptionMessage exceptionMessage;
	private GameDAO gameDAO;
	private ClientDAO clientDAO;
	private UserDAO userDAO;
	private GameSessionDAO gameSessionDAO;

	/**
	 * Constructs a {@code DatabaseManager} with a reference to the exception
	 * handling system.
	 *
	 * @param exceptionMessage class responsible for displaying or logging
	 *                         database-related errors.
	 * @since 3.3
	 */
	public DatabaseManager(ExceptionMessage exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	/**
	 * Initializes the database connection and all DAO classes used to interact with
	 * different entities in the database.
	 * <p>
	 * This method should be called once during the application's startup to prepare
	 * all database-related modules.
	 * </p>
	 *
	 * @throws SQLException           if an error occurs while establishing the
	 *                                database connection.
	 * @throws ClassNotFoundException if the JDBC driver is not found.
	 * @since 3.3
	 */
	public void initializeClasses() throws SQLException, ClassNotFoundException {
  
		dbConnection = new DatabaseConnector();
		dbInitializer = new DatabaseInitializer(dbConnection.getConnection());
		dbInitializer.ensureDatabaseExists();
		gameDAO = new GameDAO(exceptionMessage);
		clientDAO = new ClientDAO(exceptionMessage);
		gameSessionDAO = new GameSessionDAO(exceptionMessage);
		userDAO = new UserDAO(exceptionMessage);
	}

	// --------------------------- GETTERS ---------------------------

	/**
	 * Retrieves the current database connector instance.
	 *
	 * @return the {@link DatabaseConnector} responsible for managing the
	 *         connection.
	 * @since 3.3
	 */
	public DatabaseConnector getDbConnection() {
		return dbConnection;
	}

	/**
	 * Retrieves the {@link GameDAO} instance.
	 *
	 * @return the DAO used for managing game data.
	 * @since 3.3
	 */
	public GameDAO getGameDAO() {
		return gameDAO;
	}

	/**
	 * Retrieves the {@link ClientDAO} instance.
	 *
	 * @return the DAO used for managing client data.
	 * @since 3.3
	 */
	public ClientDAO getClientDAO() {
		return clientDAO;
	}

	/**
	 * Retrieves the {@link UserDAO} instance.
	 *
	 * @return the DAO used for managing user data.
	 * @since 3.3
	 */
	public UserDAO getUserDAO() {
		return userDAO;
	}

	/**
	 * Retrieves the {@link GameSessionDAO} instance.
	 *
	 * @return the DAO used for managing game session data.
	 * @since 3.3
	 */
	public GameSessionDAO getGameSessionDAO() {
		return gameSessionDAO;
	}
	
	/**
	 * Retrieves the {@link GameSessionDAO} instance.
	 *
	 * @return the DAO used for managing game session data.
	 * @since 3.3
	 */
	public DatabaseInitializer getDatabaseInitializer() {
		return dbInitializer;
	}

	// --------------------------- QUERY METHODS ---------------------------

	/**
	 * Executes a custom SQL query and returns its {@link ResultSet}.
	 * <p>
	 * This method allows running dynamic SQL queries that are not covered by the
	 * predefined DAO methods.
	 * </p>
	 *
	 * @param specificQuery the SQL query to execute.
	 * @param connection    the active {@link Connection} to use.
	 * @return a {@link ResultSet} containing the results of the query, or
	 *         {@code null} if an exception occurs.
	 * @since 3.3
	 */
	public ResultSet specificQuery(String specificQuery, Connection connection) {

		ResultSet rset = null;

		try {
			PreparedStatement stmt = connection.prepareStatement(specificQuery);
			rset = stmt.executeQuery();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection. \nCheck the console for more information.");
		}

		return rset;
	}
}
