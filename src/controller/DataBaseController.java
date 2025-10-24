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
 * @author Davitroon
 * @since 3.3
 */
public class DataBaseController {

	private MainController mainController;
	private DatabaseManager dbManager;
	private Connection connection;
	private ExceptionMessage exceptionMsg;

	public DataBaseController(MainController mainController, DatabaseManager dbManager) {
		this.mainController = mainController;
		this.dbManager = dbManager;
		this.connection = dbManager.getDbConnection().getConnection();
	}

	public void updateLastAccess(String name) {
		dbManager.getUserDAO().updateLastAccess(name, connection);
	}

	public void deleteClient(int clientId) {
		dbManager.getClientDAO().deleteClient(clientId, connection);
	}

	public void toggleRememberLogin(String name, Boolean active) {
		dbManager.getUserDAO().toggleRememberLogin(name, active, connection);
	}

	public void deleteUser(int userId) {
		dbManager.getUserDAO().deleteUser(userId, connection);
	}

	public ResultSet queryClients(boolean onlyActive) {
		return dbManager.getClientDAO().queryClients(onlyActive, currentUserId(), connection);
	}

	public ResultSet queryClient(int clientId) {
		return dbManager.getClientDAO().queryClient(clientId, connection);
	}

	public void modifyClient(Client client) {
		dbManager.getClientDAO().modifyClient(client, connection);
	}

	public void updateGameBalance(Game game) {
		dbManager.getGameDAO().updateGameBalance(game, connection);
	}

	public void updateClientBalance(Client client) {
		dbManager.getClientDAO().updateClientBalance(client, connection);
	}

	public void closeConnection() {
		try {
			dbManager.getDbConnection().getConnection().close();
		} catch (SQLException e) {
			exceptionMsg.showError(e,
					"An error occurred in the DB connection while .\nCheck the console for more information.");
		}
	}

	public ResultSet checkRememberLogin() {
		return dbManager.getUserDAO().checkRememberLogin(connection);
	}

	public void addGameSession(Client client, Game game, double betResult) {
		dbManager.getGameSessionDAO().addGameSession(client, game, betResult, currentUserId(), connection);
	}

	public void addClient(Client client) {
		dbManager.getClientDAO().addClient(client, currentUserId(), connection);
	}

	public void addGame(Game game) {
		dbManager.getGameDAO().addGame(game, currentUserId(), connection);
	}

	public void updateGame(Game game) {
		dbManager.getGameDAO().updateGame(game, connection);
	}

	public ResultSet queryGame(int gameId) {
		return dbManager.getGameDAO().queryGame(gameId, connection);
	}

	public User addUser(User user, boolean rememberSession) throws SQLIntegrityConstraintViolationException, MailException {
		return dbManager.getUserDAO().addUser(user, rememberSession, connection);
	}
	
	/**
	 * * Method that adds default users and games when creating a user.
	 * @since 3.0
	 */
	public void addDefaultUser() {
		
		Connection connection = dbManager.getDbConnection().getConnection();	
		int userId = currentUserId();

		dbManager.getClientDAO().addClient(new Client("user", 32, 'O', 2000), userId, connection);
		dbManager.getGameDAO().addGame(new Blackjack(10000), userId, connection);
		dbManager.getGameDAO().addGame(new Slotmachine(10000), userId, connection);
	}

	public ResultSet queryUser(User user) {
		return dbManager.getUserDAO().queryUser(user, connection);
	}

	public void deleteClients() {
		dbManager.getClientDAO().deleteClients(currentUserId(), connection);
	}

	public void deleteGame(int gameId) {
		dbManager.getGameDAO().deleteGame(gameId, connection);
		
	}

	public void deleteGames() {
		dbManager.getGameDAO().deleteGames(currentUserId(), connection);
		
	}

	public ResultSet queryGames(boolean onlyActive) {
		return dbManager.getGameDAO().queryGames(onlyActive, currentUserId(), connection);
	}

	public void deleteGameSessions() {
		dbManager.getGameSessionDAO().deleteGameSession(currentUserId(), connection);
	}

	public ResultSet queryGameSession() {
		return null;
	}

	public ResultSet queryGameSessions() {
		return dbManager.getGameSessionDAO().queryGameSessions(currentUserId(), connection);
	}
	
	public ResultSet specificQuery(String specificQuery) {
		return dbManager.specificQuery(specificQuery, connection);
		
	}
	
	/**
	 * 
	 * @return
	 * @since 3.0
	 */
	public int currentUserId() {
		return mainController.getCurrentUser().getId();
	}

}
