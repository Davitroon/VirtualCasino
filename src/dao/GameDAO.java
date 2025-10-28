package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptions.ExceptionMessage;
import model.Game;

/**
 * DAO class for managing {@link Game} entities in the database.
 * <p>
 * Provides CRUD operations (Create, Read, Update, Delete) for games associated
 * with specific users. Uses {@link ExceptionMessage} to handle database-related
 * errors.
 * </p>
 * 
 * @author Davitroon
 * @since 3.3
 */
public class GameDAO {

	private ExceptionMessage exceptionMessage;

	/**
	 * Constructs a {@code GameDAO} with a reference to the exception handling
	 * system.
	 * 
	 * @param exceptionMessage class responsible for displaying or logging
	 *                         database-related errors.
	 * @since 3.3
	 */
	public GameDAO(ExceptionMessage exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	// --------------------- CREATE ---------------------

	/**
	 * Inserts a new game into the database.
	 * 
	 * @param game       the {@link Game} object to add.
	 * @param userId     the ID of the user creating the game.
	 * @param connection active database {@link Connection}.
	 * @since 3.3
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
	 * Retrieves a specific game by its ID.
	 * 
	 * @param gameId     the ID of the game to query.
	 * @param connection active database {@link Connection}.
	 * @return {@link ResultSet} containing the game's data, or {@code null} if an
	 *         exception occurs.
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
					"An error occurred in the DB connection while querying the game table.\nCheck the console for more information.");
		}

		return rset;
	}

	/**
	 * Retrieves all games associated with a specific user.
	 * 
	 * @param onlyActive if {@code true}, only active games are retrieved.
	 * @param userId     the ID of the user whose games are queried.
	 * @param connection active database {@link Connection}.
	 * @return {@link ResultSet} containing the games data, or {@code null} if an
	 *         exception occurs.
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
					"An error occurred in the DB connection while querying the games table.\nCheck the console for more information.");
		}

		return rset;
	}

	// --------------------- UPDATE ---------------------

	/**
	 * Updates all data of a specific game in the database.
	 * 
	 * @param game       the {@link Game} object containing updated data.
	 * @param connection active database {@link Connection}.
	 * @since 3.3
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
	 * Updates only the money pool of a specific game in the database.
	 * 
	 * @param game       the {@link Game} object containing the new money value.
	 * @param connection active database {@link Connection}.
	 * @since 3.3
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
	 * Deletes a specific game by its ID from the database.
	 * 
	 * @param gameId     the ID of the game to delete.
	 * @param connection active database {@link Connection}.
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
	 * Deletes all games associated with a specific user.
	 * 
	 * @param userId     the ID of the user whose games should be deleted.
	 * @param connection active database {@link Connection}.
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
