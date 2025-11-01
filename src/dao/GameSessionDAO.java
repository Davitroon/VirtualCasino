package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptions.ExceptionMessage;
import model.Client;
import model.Game;

/**
 * DAO class for managing game sessions in the database.
 * <p>
 * Provides CRUD operations for storing and retrieving sessions of games played
 * by clients, including the results of bets.
 * </p>
 * 
 * @author Davitroon
 * @since 3.3
 */
public class GameSessionDAO {

	private ExceptionMessage exceptionMessage;

	/**
	 * Constructs a {@code GameSessionDAO} with a reference to the exception
	 * handling system.
	 * 
	 * @param exceptionMessage class responsible for displaying or logging
	 *                         database-related errors.
	 * @since 3.3
	 */
	public GameSessionDAO(ExceptionMessage exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	// --------------------- CREATE ---------------------

	/**
	 * Inserts a new game session into the database.
	 * 
	 * @param client     the {@link Client} participating in the game session. Must
	 *                   have a valid ID.
	 * @param game       the {@link Game} in which the session is taking place. Must
	 *                   have an ID and a type ("Blackjack" or "Slotmachine").
	 * @param betResult  resulting amount of the bet (positive if the client wins,
	 *                   negative if they lose).
	 * @param userId     the ID of the user associated with this session.
	 * @param connection active database {@link Connection}.
	 * @since 3.3
	 */
	public void addGameSession(Client client, Game game, double betResult, int userId, Connection connection) {

		String query = "INSERT INTO game_sessions (client_id, game_id, game_type, bet_result, user_profile) "
				+ "VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setInt(1, client.getId());
			stmt.setInt(2, game.getId());
			stmt.setString(3, game.getType());
			stmt.setDouble(4, betResult);
			stmt.setInt(5, userId);

			stmt.executeUpdate();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while inserting a game session.\nCheck the console for more information.");
		}
	}

	// --------------------- READ ---------------------

	/**
	 * Retrieves all game sessions for a specific user.
	 * 
	 * @param userId     the ID of the user whose game sessions are queried.
	 * @param connection active database {@link Connection}.
	 * @return {@link ResultSet} containing the game session records, or
	 *         {@code null} if an exception occurs.
	 * @since 3.3
	 */
	public ResultSet queryGameSessions(int userId, Connection connection) {
		String query = "SELECT * FROM game_sessions WHERE user_profile = ?";
		ResultSet rset = null;

		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setInt(1, userId);
			rset = stmt.executeQuery();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while querying the game_sessions table.\nCheck the console for more information.");
		}

		return rset;
	}

	// --------------------- DELETE ---------------------

	/**
	 * Deletes all game sessions associated with a specific user.
	 * 
	 * @param userId     the ID of the user whose sessions should be deleted.
	 * @param connection active database {@link Connection}.
	 * @since 3.3
	 */
	public void deleteGameSession(int userId, Connection connection) {
		String query = "DELETE FROM game_sessions WHERE user_profile = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, userId);
			stmt.executeUpdate();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while deleting entries from the game_sessions table.\nCheck the console for more information.");
		}
	}
}
