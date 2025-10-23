package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import exceptions.MessageException;
import model.Game;
import model.User;

public class GameDAO {

	private MessageException exceptionMessage;

	public GameDAO(MessageException exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	/**
	 * Method to insert a game into the database.
	 * 
	 * @param game Game to add.
	 * @throws SQLException
	 * @since 3.0
	 */
	public void addGame(Game game, User user, Connection connection) {
		String query = "INSERT INTO games (game_type, money_pool, user_profile) VALUES (?, ?, ?);";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, game.getType());
			stmt.setDouble(2, game.getMoney());
			stmt.setInt(3, user.getId());

			stmt.executeUpdate();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while inserting a game.\nCheck the console for more information.");

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
					"An error occurred in the DB connection while modifying the game's money pool.\nCheck the console for more information.");

		}
	}

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
					"An error occurred in the DB connection while modifying a game.\nCheck the console for more information.");

		}
	}
}
