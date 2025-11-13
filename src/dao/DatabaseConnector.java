package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

/**
 * Handles the connection between the application and the MySQL database.
 * <p>
 * This class is responsible for establishing, providing, and closing the
 * connection to the database. It ensures that the connection is created only
 * once and shared across the application through a static {@link Connection}
 * instance.
 * </p>
 *
 * <p><b>Usage example:</b></p>
 * <pre>
 * DataBaseConnector connector = new DataBaseConnector();
 * Connection conn = connector.getConnection();
 * </pre>
 *
 * @author Davitroon
 * @since 3.3
 */
public class DatabaseConnector {

    private static Connection connection;
    private static final String DB_NAME = "casino25.db";
    private static final String URL = "jdbc:sqlite:" + DB_NAME;

	/**
	 * Constructs a new {@code DataBaseConnector} and establishes a connection
	 * to the database using the defined credentials.
	 * <p>
	 * This constructor loads the MySQL JDBC driver and attempts to connect
	 * to the specified database. If the database name is empty or invalid, it
	 * throws an {@link SQLSyntaxErrorException}.
	 * </p>
	 *
	 * @throws SQLException             if an error occurs while connecting to
	 *                                  the database.
	 * @throws ClassNotFoundException   if the JDBC driver class cannot be found.
	 * @since 3.3
	 */
    public DatabaseConnector() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection(URL);
    }

	/**
	 * Closes the active connection to the database, if it exists.
	 * <p>
	 * This method checks whether a connection is currently open before attempting
	 * to close it. It is safe to call multiple times.
	 * </p>
	 *
	 * @since 3.3
	 */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	/**
	 * Retrieves the current database connection.
	 *
	 * @return the active {@link Connection} object, or {@code null} if no
	 *         connection has been established.
	 * @since 3.3
	 */
	public Connection getConnection() {
		return connection;
	}
}
