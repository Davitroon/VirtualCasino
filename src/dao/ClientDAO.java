package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptions.MessageException;
import model.Client;
import model.User;

public class ClientDAO {

	private MessageException exceptionMessage;

	public ClientDAO(MessageException exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	/**
	 * Method to insert a client into the database.
	 * 
	 * @param client Client to add.
	 * @since 3.0
	 */
	public void addClient(Client client, User user, Connection connection) {
		String query = "INSERT INTO customers (customer_name, age, gender, balance, user_profile) VALUES (?, ?, ?, ?, ?);";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, client.getName());
			stmt.setInt(2, client.getAge());
			stmt.setString(3, String.valueOf(client.getGender()));
			stmt.setDouble(4, client.getBalance());
			stmt.setInt(5, user.getId());

			stmt.executeUpdate();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while inserting a client.\nCheck the console for more information.");
		}
	}

	/**
	 * Method to modify a client in the database.
	 * 
	 * @param client Client to modify.
	 * @since 3.0
	 */
	public void modifyClient(Client client, Connection connection) {
		String query = "UPDATE customers SET customer_name = ?, age = ?, gender = ?, active_status = ?, balance = ? WHERE id = ?;";

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
					"An error occurred in the DB connection while modifying a client.\nCheck the console for more information.");
		}
	}

	public void deleteClient(int clientId, Connection connection) {
		String query = "DELETE FROM customers WHERE id = ?;";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, clientId);
			stmt.executeUpdate();

		} catch (SQLException e) {
			exceptionMessage.showError(e,
					"An error occurred in the DB connection while modifying a client.\nCheck the console for more information.");
		}
	}

	public ResultSet queryClients(boolean onlyActive, Connection connection, int currentUser) {
		String query = "SELECT * FROM customers WHERE user_profile = ?";
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
					"An error occurred in the DB connection while querying the table customers .\nCheck the console for more information.");
		}

		return rset;
	}
}
