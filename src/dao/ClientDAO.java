package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptions.ExceptionMessage;
import model.Client;

/**
 * Data Access Object (DAO) responsible for managing CRUD operations (Create,
 * Read, Update, Delete) for {@link Client} objects in the database.
 * <p>
 * All database errors are handled and reported through
 * {@link ExceptionMessage}.
 * </p>
 *
 * @author Davitroon
 * @since 3.3
 */
public class ClientDAO {

	private ExceptionMessage exceptionMessage;

	/**
	 * Constructs a new {@code ClientDAO} with the specified exception handler.
	 *
	 * @param exceptionMessage the {@link ExceptionMessage} instance used to display
	 *                         database errors.
	 * @since 3.3
	 */
	public ClientDAO(ExceptionMessage exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	// --------------------- CREATE ---------------------

	/**
	 * Inserts a new client into the database.
	 *
	 * @param client     the {@link Client} object to insert.
	 * @param userID     the ID of the user who owns the client profile.
	 * @param connection the active database {@link Connection}.
	 * @since 3.3
	 */
	public void addClient(Client client, int userID, Connection connection) {
		String query = "INSERT INTO clients (client_name, age, gender, balance, user_profile) VALUES (?, ?, ?, ?, ?);";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, client.getName());
			stmt.setInt(2, client.getAge());
			stmt.setString(3, String.valueOf(client.getGender()));
			stmt.setDouble(4, client.getBalance());
			stmt.setInt(5, userID);

			stmt.executeUpdate();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while inserting a client.\nCheck the console for more information.");
		}
	}

	// --------------------- READ ---------------------

	/**
	 * Queries the database for a specific client by its ID.
	 *
	 * @param clientId   the unique identifier of the client.
	 * @param connection the active database {@link Connection}.
	 * @return a {@link ResultSet} containing the client's information, or
	 *         {@code null} if an error occurs.
	 * @since 3.3
	 */
	public ResultSet queryClient(int clientId, Connection connection) {
		ResultSet rset = null;

		try {
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM clients WHERE client_id = ?");
			stmt.setInt(1, clientId);
			rset = stmt.executeQuery();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while querying the clients table.\nCheck the console for more information.");
		}

		return rset;
	}

	/**
	 * Retrieves a list of clients associated with a given user.
	 *
	 * @param onlyActive  if {@code true}, only active clients are retrieved.
	 * @param currentUser the ID of the current user whose clients are to be listed.
	 * @param connection  the active database {@link Connection}.
	 * @return a {@link ResultSet} containing all clients matching the criteria, or
	 *         {@code null} if an error occurs.
	 * @since 3.3
	 */
	public ResultSet queryClients(boolean onlyActive, int currentUser, Connection connection) {
		String query = "SELECT * FROM clients WHERE user_profile = ?";
		ResultSet rset = null;

		if (onlyActive) {
			query += " AND active_status = 1";
		}

		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setInt(1, currentUser);
			rset = stmt.executeQuery();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while querying the clients table.\nCheck the console for more information.");
		}

		return rset;
	}

	// --------------------- UPDATE ---------------------

	/**
	 * Updates a client's information in the database.
	 *
	 * @param client     the {@link Client} object containing updated data.
	 * @param connection the active database {@link Connection}.
	 * @since 3.3
	 */
	public void modifyClient(Client client, Connection connection) {
		String query = "UPDATE clients SET client_name = ?, age = ?, gender = ?, active_status = ?, balance = ? WHERE id = ?;";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, client.getName());
			stmt.setInt(2, client.getAge());
			stmt.setString(3, String.valueOf(client.getGender()));
			stmt.setBoolean(4, client.isActive());
			stmt.setDouble(5, client.getBalance());
			stmt.setInt(6, client.getId());

			stmt.executeUpdate();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while updating a client.\nCheck the console for more information.");
		}
	}

	/**
	 * Updates only the balance of a client in the database.
	 *
	 * @param client     the {@link Client} whose balance is to be updated.
	 * @param connection the active database {@link Connection}.
	 * @since 3.3
	 */
	public void updateClientBalance(Client client, Connection connection) {
		String query = "UPDATE clients SET balance = ? WHERE id = ?;";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setDouble(1, client.getBalance());
			stmt.setInt(2, client.getId());

			stmt.executeUpdate();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while modifying the client's balance.\nCheck the console for more information.");
		}
	}

	// --------------------- DELETE ---------------------

	/**
	 * Deletes a client from the database by its ID.
	 *
	 * @param clientId   the ID of the client to delete.
	 * @param connection the active database {@link Connection}.
	 * @since 3.3
	 */
	public void deleteClient(int clientId, Connection connection) {
		String query = "DELETE FROM clients WHERE id = ?;";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, clientId);
			stmt.executeUpdate();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while deleting a client.\nCheck the console for more information.");
		}
	}

	/**
	 * Deletes all clients linked to a specific user.
	 *
	 * @param userId     the ID of the user whose clients will be deleted.
	 * @param connection the active database {@link Connection}.
	 * @since 3.3
	 */
	public void deleteClients(int userId, Connection connection) {
		String query = "DELETE FROM clients WHERE user_profile = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, userId);
			stmt.executeUpdate();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while deleting entries from the clients table.\nCheck the console for more information.");
		}
	}
}