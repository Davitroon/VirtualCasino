package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import dao.DatabaseManager;
import exceptions.ExceptionMessage;
import exceptions.MailException;
import model.Blackjack;
import model.Client;
import model.Game;
import model.Slotmachine;
import model.User;

/**
 * Controller responsible for managing database operations across different DAOs
 * (UserDAO, ClientDAO, GameDAO, GameSessionDAO). Acts as a bridge between the
 * {@link MainController} and the {@link DatabaseManager}, delegating queries,
 * inserts, updates and deletions through an established SQL connection.
 *
 * @author Davitroon
 * @since 3.3
 */
public class DataBaseController {

	private MainController mainController;
	private DatabaseManager dbManager;
	private Connection connection;
	private ExceptionMessage exceptionMsg;

	/**
	 * Creates a new {@code DataBaseController} and initializes the database
	 * connection.
	 *
	 * @param mainController reference to the main application controller
	 * @param dbManager      database manager providing DAO access and connections
	 */
	public DataBaseController(MainController mainController, DatabaseManager dbManager) {
		this.mainController = mainController;
		this.dbManager = dbManager;
		this.connection = dbManager.getDbConnection().getConnection();
	}

	// --------------------------- USERDAO METHODS ---------------------------

	/**
	 * Adds a new user to the database.
	 *
	 * @param user            the user object containing registration data
	 * @param rememberSession true if the user session should be remembered
	 * @return the registered user object
	 * @throws SQLIntegrityConstraintViolationException if the username or email
	 *                                                  already exists
	 * @throws MailException                            if there is an issue sending
	 *                                                  confirmation mail
	 * @since 3.3
	 */
	public User addUser(User user, boolean rememberSession)
			throws SQLIntegrityConstraintViolationException, MailException {
		return dbManager.getUserDAO().addUser(user, rememberSession, connection);
	}

	/**
	 * Checks if any user has a remembered login session.
	 *
	 * @return a {@link ResultSet} with remembered login data
	 * @since 3.3
	 */
	public ResultSet checkRememberLogin() {
		return dbManager.getUserDAO().checkRememberLogin(connection);
	}

	/**
	 * Queries user information from the database.
	 *
	 * @param user user object containing the query parameters
	 * @return a {@link ResultSet} with the queried user data
	 * @since 3.3
	 */
	public ResultSet queryUser(User user) {
		return dbManager.getUserDAO().queryUser(user, connection);
	}

	/**
	 * Enables the "remember login" flag for the given user.
	 *
	 * @param name the username whose session will be remembered
	 * @since 3.3
	 */
	public void enableRememberLogin(String name) {
		dbManager.getUserDAO().enableRememberLogin(name, connection);
	}

	/**
	 * Disables the "remember login" flag for all users.
	 *
	 * @since 3.3
	 */
	public void disableRememberLogin() {
		dbManager.getUserDAO().disableRememberLogin(connection);
	}

	/**
	 * Updates the last access date of a given user.
	 *
	 * @param name the username whose last access date will be updated
	 * @since 3.3
	 */
	public void updateLastAccess(String name) {
		dbManager.getUserDAO().updateLastAccess(name, connection);
	}

	/**
	 * Deletes a user from the database.
	 *
	 * @param userId the unique identifier of the user
	 * @since 3.3
	 */
	public void deleteUser(int userId) {
		dbManager.getUserDAO().deleteUser(userId, connection);
	}

	// -------------------------- CLIENTDAO METHODS --------------------------

	/**
	 * Adds a new client linked to the current user.
	 *
	 * @param client the client to add
	 * @since 3.3
	 */
	public void addClient(Client client) {
		dbManager.getClientDAO().addClient(client, currentUserId(), connection);
	}

	/**
	 * Queries a specific client by ID.
	 *
	 * @param clientId the client's unique identifier
	 * @return a {@link ResultSet} containing the client data
	 * @since 3.3
	 */
	public ResultSet queryClient(int clientId) {
		return dbManager.getClientDAO().queryClient(clientId, connection);
	}

	/**
	 * Queries all clients linked to the current user.
	 *
	 * @param onlyActive true to query only active clients
	 * @return a {@link ResultSet} with the client list
	 * @since 3.3
	 */
	public ResultSet queryClients(boolean onlyActive) {
		return dbManager.getClientDAO().queryClients(onlyActive, currentUserId(), connection);
	}

	/**
	 * Modifies an existing client's information.
	 *
	 * @param client the client object containing updated data
	 * @since 3.3
	 */
	public void modifyClient(Client client) {
		dbManager.getClientDAO().modifyClient(client, connection);
	}

	/**
	 * Updates the balance of a client.
	 *
	 * @param client the client whose balance will be updated
	 * @since 3.3
	 */
	public void updateClientBalance(Client client) {
		dbManager.getClientDAO().updateClientBalance(client, connection);
	}

	/**
	 * Deletes a specific client.
	 *
	 * @param clientId the client’s unique identifier
	 * @since 3.3
	 */
	public void deleteClient(int clientId) {
		dbManager.getClientDAO().deleteClient(clientId, connection);
	}

	/**
	 * Deletes all clients linked to the current user.
	 * 
	 * @since 3.3
	 */
	public void deleteClients() {
		dbManager.getClientDAO().deleteClients(currentUserId(), connection);
	}

	// --------------------------- GAMEDAO METHODS ---------------------------

	/**
	 * Adds a new game associated with the current user.
	 *
	 * @param game the game to add
	 * 
	 * @since 3.3
	 */
	public void addGame(Game game) {
		dbManager.getGameDAO().addGame(game, currentUserId(), connection);
	}

	/**
	 * Queries a specific game by ID.
	 *
	 * @param gameId the game's unique identifier
	 * @return a {@link ResultSet} containing the game data
	 * @since 3.3
	 */
	public ResultSet queryGame(int gameId) {
		return dbManager.getGameDAO().queryGame(gameId, connection);
	}

	/**
	 * Queries all games belonging to the current user.
	 *
	 * @param onlyActive true to query only active games
	 * @return a {@link ResultSet} with the game list
	 * @since 3.3
	 */
	public ResultSet queryGames(boolean onlyActive) {
		return dbManager.getGameDAO().queryGames(onlyActive, currentUserId(), connection);
	}

	/**
	 * Updates an existing game’s information.
	 *
	 * @param game the game with updated data
	 * @since 3.3
	 */
	public void updateGame(Game game) {
		dbManager.getGameDAO().updateGame(game, connection);
	}

	/**
	 * Updates the balance (house money) of a game.
	 *
	 * @param game the game whose balance will be updated
	 * @since 3.3
	 */
	public void updateGameBalance(Game game) {
		dbManager.getGameDAO().updateGameBalance(game, connection);
	}

	/**
	 * Deletes a game from the database.
	 *
	 * @param gameId the game's unique identifier
	 * @since 3.3
	 */
	public void deleteGame(int gameId) {
		dbManager.getGameDAO().deleteGame(gameId, connection);
	}

	/**
	 * Deletes all games linked to the current user.
	 * 
	 * @since 3.3
	 */
	public void deleteGames() {
		dbManager.getGameDAO().deleteGames(currentUserId(), connection);
	}

	// ----------------------- GAMESESSIONDAO METHODS ------------------------

	/**
	 * Adds a new game session record to the database.
	 *
	 * @param client    the client who played
	 * @param game      the game that was played
	 * @param betResult the result of the bet (positive or negative)
	 * @since 3.3
	 */
	public void addGameSession(Client client, Game game, double betResult) {
		dbManager.getGameSessionDAO().addGameSession(client, game, betResult, currentUserId(), connection);
	}

	/**
	 * Queries all game sessions associated with the current user.
	 *
	 * @return a {@link ResultSet} containing the session data
	 * @since 3.3
	 */
	public ResultSet queryGameSessions() {
		return dbManager.getGameSessionDAO().queryGameSessions(currentUserId(), connection);
	}

	/**
	 * Deletes all game sessions linked to the current user.
	 * 
	 * @since 3.3
	 */
	public void deleteGameSessions() {
		dbManager.getGameSessionDAO().deleteGameSession(currentUserId(), connection);
	}

	// ---------------------------- OTHER METHODS ----------------------------

	/**
	 * Adds default clients and games when creating a new user. Includes one default
	 * client and the default games (Blackjack and Slotmachine).
	 *
	 * @since 3.3
	 */
	public void addDefaultUser() {
		Connection connection = dbManager.getDbConnection().getConnection();
		int userId = currentUserId();

		dbManager.getClientDAO().addClient(new Client("user", 32, 'O', 2000), userId, connection);
		dbManager.getGameDAO().addGame(new Blackjack(10000), userId, connection);
		dbManager.getGameDAO().addGame(new Slotmachine(10000), userId, connection);
	}

	/**
	 * Executes a custom SQL query.
	 *
	 * @param specificQuery raw SQL query to execute
	 * @return a {@link ResultSet} with the result of the query
	 * @since 3.3
	 */
	public ResultSet specificQuery(String specificQuery) {
		return dbManager.specificQuery(specificQuery, connection);
	}

	/**
	 * Gets the ID of the currently logged-in user.
	 *
	 * @return the current user's ID
	 * @since 3.3
	 */
	public int currentUserId() {
		return mainController.getCurrentUser().getId();
	}

	/**
	 * Closes the current database connection safely.
	 * 
	 * @since 3.3
	 */
	public void closeConnection() {
		try {
			dbManager.getDbConnection().getConnection().close();
		} catch (SQLException e) {
			exceptionMsg.showError(e,
					"An error occurred in the DB connection.\nCheck the console for more information.");
		}
	}
}