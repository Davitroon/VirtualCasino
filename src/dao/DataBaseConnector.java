package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

public class DataBaseConnector {

	private static Connection connection;
	private String database = "casino25";
	private String login = "root";
	private String pwd = "Coco2006";
	private String url = "jdbc:mysql://localhost/" + database;

	/**
	 * DBConnection constructor, where it makes the connection to the database.
	 * 
	 * @param exceptionMessage Class that handles the exception warnings.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @since 3.3
	 */
	public DataBaseConnector() throws SQLException, ClassNotFoundException {
		if (database == "") {
			throw new SQLSyntaxErrorException();
		}
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection(url, login, pwd);
		System.out.println(" - DB Connection established -");
	}
	
	/**
	 * Method to close the connection to the DB.
	 * @since 3.3
	 */
	public void closeConnection() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
				System.out.println(" - DB Connection closed -");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return connection;
	}

}