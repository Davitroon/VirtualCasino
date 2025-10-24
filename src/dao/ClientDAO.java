package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptions.ExceptionMessage;
import model.Client;

public class ClientDAO {

	private ExceptionMessage exceptionMessage;

	public ClientDAO(ExceptionMessage exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	// --------------------- CREATE ---------------------

	/**
	 * Method to insert a client into the database.
	 * * @param client Client to add.
	 * @since 3.0
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
	 * * @param clientId
	 * @param connection
	 * @return
	 * @since 3.3
	 */
	public ResultSet queryClient(int clientId, Connection connection) {
		ResultSet rset = null;

		try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM clients WHERE client_id = ?")) {		
			stmt.setInt(1, clientId);
			rset = stmt.executeQuery();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while querying the clients table. \nCheck the console for more information.");
		}

		return rset;
	}

	/**
	 * * @param onlyActive
	 * @param currentUser
	 * @param connection
	 * @return
	 * @since 3.3
	 */
	public ResultSet queryClients(boolean onlyActive, int currentUser, Connection connection) {
		String query = "SELECT * FROM clients WHERE user_profile = ?";
		ResultSet rset = null;

		if (onlyActive) {
			query += " AND active_status = 1";
		}

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, currentUser);
			rset = stmt.executeQuery();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while querying the clients table .\nCheck the console for more information.");
		}

		return rset;
	}

	// --------------------- UPDATE ---------------------

	/**
	 * Method to modify a client in the database.
	 * * @param client Client to modify.
	 * @since 3.0
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
					"An error occurred in the DB connection while udpating a client.\nCheck the console for more information.");
		}
	}

	/**
	 * Method to modify only a client's balance in the database.
	 * * @param client Client to modify.
	 * @since 3.0
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
	 * * @param clientId
	 * @param connection
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
	 * * @param userId
	 * @param connection
	 * @since 3.3
	 */
	public void deleteClients(int userId, Connection connection) {
		String query = "DELETE FROM clients WHERE user_profile = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query);) {		
			stmt.setInt(1, userId);
			stmt.executeUpdate();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while deleting entries from the clients table.\nCheck the console for more information.");
		}
	}
}