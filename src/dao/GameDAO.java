package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptions.ExceptionMessage;
import model.Game;

public class GameDAO {

	private ExceptionMessage exceptionMessage;

	public GameDAO(ExceptionMessage exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	// --------------------- CREATE ---------------------

	/**
	 * Method to insert a game into the database.
	 * 
	 * @param game Game to add.
	 * @throws SQLException
	 * @since 3.0
	 */
	public void addGame(Game game, int userId, Connection connection) {
		String query = "INSERT INTO games (game_type, money_pool, user_profile) VALUES (?, ?, ?);";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, game.getType());
			stmt.setDouble(2, game.getMoney());
			stmt.setInt(3, userId);

			stmt.executeUpdate();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while inserting a game.\nCheck the console for more information.");

		}
	}

	// --------------------- READ ---------------------

	/**
	 * 
	 * @param gameId
	 * @param connection
	 * @return
	 * @since 3.3
	 */
	public ResultSet queryGame(int gameId, Connection connection) {
		ResultSet rset = null;
		
		try {	
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM games WHERE game_id = ?");
			stmt.setInt(1, gameId);
			rset = stmt.executeQuery();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while querying the game table. \nCheck the console for more information.");
		}

		return rset;
	}

	/**
	 * 
	 * @param onlyActive
	 * @param userId
	 * @param connection
	 * @return
	 * @since 3.3
	 */
	public ResultSet queryGames(boolean onlyActive, int userId, Connection connection) {
		String query = "SELECT * FROM games WHERE user_profile = ?";
		ResultSet rset = null;

		if (onlyActive) {
			query += " AND active_status = 1";
		}

		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setInt(1, userId);
			rset = stmt.executeQuery();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while querying the games table .\nCheck the console for more information.");
		}

		return rset;
	}
	
	// --------------------- UPDATE ---------------------

	/**
	 * Method to modify a game in the database.
	 * 
	 * @param game Game to modify.
	 * @throws SQLException
	 * @since 3.0
	 */
	public void updateGame(Game game, Connection connection) {
		String query = "UPDATE games SET game_type = ?, active_status = ?, money_pool = ? WHERE id = ?;";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, game.getType());
			stmt.setBoolean(2, game.isActive());
			stmt.setDouble(3, game.getMoney());
			stmt.setInt(4, game.getId());

			stmt.executeUpdate();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while updating a game.\nCheck the console for more information.");

		}
	}

	/**
	 * Method to modify a game's money pool in the database.
	 * 
	 * @param game Game to modify.
	 * @throws SQLException
	 * @since 3.0
	 */
	public void updateGameBalance(Game game, Connection connection) {
		String query = "UPDATE games SET money_pool = ? WHERE id = ?;";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setDouble(1, game.getMoney());
			stmt.setInt(2, game.getId());

			stmt.executeUpdate();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while modifying a game's balance.\nCheck the console for more information.");

		}
	}

	// --------------------- DELETE ---------------------

	/**
	 * 
	 * @param gameId
	 * @param connection
	 * @since 3.3
	 */
	public void deleteGame(int gameId, Connection connection) {
		String query = "DELETE FROM games WHERE id = ?;";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, gameId);
			stmt.executeUpdate();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while deleting a game.\nCheck the console for more information.");
		}
	}
	
	/**
	 * 
	 * @param userId
	 * @param connection
	 * @since 3.3
	 */
	public void deleteGames(int userId, Connection connection) {
		String query = "DELETE FROM games WHERE user_profile = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, userId);
			stmt.executeUpdate();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while deleting entries from the games table.\nCheck the console for more information.");
		}
	}
}