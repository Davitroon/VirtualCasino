package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptions.ExceptionMessage;
import model.Client;
import model.Game;

/**
 * @author Davitroon
 * @since 3.3
 */
public class GameSessionDAO {

	private ExceptionMessage exceptionMessage;

	public GameSessionDAO(ExceptionMessage exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	// --------------------- CREATE ---------------------

	/**
	 * Inserts a new game session into the database.
	 * 
	 * @param client    The client participating in the game session. Must have a
	 *                  valid ID.
	 * @param game      The game in which the session is taking place. Must have an
	 *                  ID and a type ("Blackjack" or "Slotmachine").
	 * @param betResult Resulting amount of the bet (positive if the client wins,
	 *                  negative if they lose).
	 * @since 3.0
	 */
	public void addGameSession(Client client, Game game, double betResult, int userId ,Connection connection) {

		String query = "INSERT INTO game_sessions (customer_id, game_id, game_type, bet_result, user_profile) "
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
	 * 
	 * @since 3.3
	 */
	public ResultSet queryGameSessions(int userId, Connection connection) {
		String query = "SELECT * FROM game_sessions WHERE user_profile = ?";
		ResultSet rset = null;

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, userId);
			rset = stmt.executeQuery();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while querying the game_sessions table .\nCheck the console for more information.");
		}

		return rset;
	}

	// --------------------- DELETE ---------------------

	/**
	 * @since 3.3
	 */
	public void deleteGameSession(int userId, Connection connection) {
		String query = "DELETE FROM game_sessions WHERE user_profile = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, userId);
			stmt.executeUpdate();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while deleting entries from the table game_sessions table.\nCheck the console for more information.");
		}
	}
}