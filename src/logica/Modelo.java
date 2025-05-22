package logica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Clase modelo del MVC. Conecta con la BD y gestiona la información.
 * @author David
 * @since 3.0
 */
public class Modelo {
	
	private String database = "casino25";
	private String login = "root"; 
	private String pwd = "Coco2006";
	private String url = "jdbc:mysql://localhost/" + database;
	private static Connection conexion;
	
	/**
	 * Constructor del modelo, dodne hace conexión a la base de datos.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @since 3.0
	 */
	public Modelo() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		conexion=DriverManager.getConnection(url,login,pwd);
		System.out.println (" - Conexión con BD establecida -");
	}
	
	
	/**
	 * Método para realizar una consulta de todos los datos de una tabla a la base de datos.
	 * @param tabla Tabla a consultar.
	 * @param soloActivos Mostrar datos que únicamente esten activos.
	 * @return ResultSet (Consulta SQL)
	 * @since 3.0
	 */
	public ResultSet consultarDatos(String tabla, boolean soloActivos) {
		
		String consulta = null;
		ResultSet rset = null;
		
		if (tabla.equals("juegos")) consulta = "SELECT * FROM juegos";
		if (tabla.equals("clientes")) consulta = "SELECT * FROM clientes";
		if (tabla.equals("partidas")) consulta = "SELECT * FROM partidas";
		if (soloActivos) consulta += " WHERE activo = 1";
		
		try {
			PreparedStatement stmt = conexion.prepareStatement(consulta);
			rset = stmt.executeQuery();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return rset;
	}
	
	
	
	/**
	 * Método para realizar una consulta de un único dato en una tabla a la base de datos.
	 * @param tabla Tabla a consultar
	 * @param id Id del elemento a consultar
	 * @return ResultSet (Consulta SQL)
	 * @since 3.0
	 */
	public ResultSet consultarDatoUnico(String tabla, int id) {
		
		String consulta = null;
		ResultSet rset = null;
		
		if (tabla.equals("juegos")) consulta = "SELECT * FROM juegos WHERE id = ?;";
		if (tabla.equals("clientes")) consulta = "SELECT * FROM clientes WHERE id = ?;";
		
		try {
			PreparedStatement stmt = conexion.prepareStatement(consulta);
			stmt.setInt(1, id);
			rset = stmt.executeQuery();
			rset.next();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return rset;
	}
	
	
	public ResultSet consultaEspecifica(String consulta) {
		
		ResultSet rset = null;
		PreparedStatement stmt;
		try {
			stmt = conexion.prepareStatement(consulta);
			rset = stmt.executeQuery();
			rset.next();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return rset;
	}
	
	
	/**
	 * Método para insertar un cliente en la base de datos.
	 * @param cliente Cliente a agregar.
	 * @since 3.0
	 */
	public void agregarCliente(Cliente cliente) {
	    String consulta = "INSERT INTO clientes (nombre, edad, genero, activo, saldo) VALUES (?, ?, ?, true, ?);";

	    try (PreparedStatement stmt = conexion.prepareStatement(consulta)) {
	        stmt.setString(1, cliente.getNombre());
	        stmt.setInt(2, cliente.getEdad());
	        stmt.setString(3, String.valueOf(cliente.getGenero()));
	        stmt.setDouble(4, cliente.getSaldo());

	        stmt.executeUpdate();
	        

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	
	/**
	 * Método para insertar un juego en la base de datos.
	 * @param juego Juego a agregar.
	 * @since 3.0
	 */
	public void agregarJuego(Juego juego) {
	    String consulta = "INSERT INTO juegos (tipo, activo, dinero) VALUES (?, true, ?);";

	    try (PreparedStatement stmt = conexion.prepareStatement(consulta)) {
	        stmt.setString(1, juego.getTipo());
	        stmt.setDouble(2, juego.getDinero());

	        stmt.executeUpdate();
	        

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Método para modificar un cliente en la base de datos.
	 * @param cliente Cliente a modificar.
	 * @since 3.0
	 */
	public void modificarCliente(Cliente cliente) {
	    String consulta = "UPDATE clientes SET nombre = ?, edad = ?, genero = ?, activo = ?, saldo = ? WHERE id = ?;";
	    
	    try (PreparedStatement stmt = conexion.prepareStatement(consulta)) {
	        stmt.setString(1, cliente.getNombre());
	        stmt.setInt(2, cliente.getEdad());
	        stmt.setString(3, String.valueOf(cliente.getGenero()));
	        stmt.setBoolean(4, cliente.isActivo());
	        stmt.setDouble(5, cliente.getSaldo());
	        stmt.setInt(6, cliente.getId());

	        stmt.executeUpdate();
	        
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Método para modificar únicamente el saldo de un cliente en la base de datos.
	 * @param cliente Cliente a modificar.
	 * @since 3.0
	 */
	public void modificarSaldoCliente(Cliente cliente) {
	    String consulta = "UPDATE clientes SET saldo = ? WHERE id = ?;";
	    
	    try (PreparedStatement stmt = conexion.prepareStatement(consulta)) {
	        stmt.setDouble(1, cliente.getSaldo());
	        stmt.setInt(2, cliente.getId());

	        stmt.executeUpdate();
	        
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Método para modificar un juego en la base de datos.
	 * @param juego Juego a modificar.
	 * @since 3.0
	 */
	public void modificarJuego(Juego juego) {
	    String consulta = "UPDATE juegos SET tipo = ?, activo = ?, dinero = ? WHERE id = ?;";
	    
	    try (PreparedStatement stmt = conexion.prepareStatement(consulta)) {
	        stmt.setString(1, juego.getTipo());
	        stmt.setBoolean(2, juego.isActivo());
	        stmt.setDouble(3, juego.getDinero());
	        stmt.setInt(4, juego.getId());

	        stmt.executeUpdate();
	        
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	
	/**
	 * Método para modificar el dinero de un juego en la base de datos.
	 * @param juego Juego a modificar.
	 * @since 3.0
	 */
	public void modificarDineroJuego(Juego juego) {
	    String consulta = "UPDATE juegos SET dinero = ? WHERE id = ?;";
	    
	    try (PreparedStatement stmt = conexion.prepareStatement(consulta)) {
	        stmt.setDouble(1, juego.getDinero());
	        stmt.setInt(2, juego.getId());

	        stmt.executeUpdate();
	        
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	
	/**
	 * Método para borrar un dato de la base de datos.
	 * @param id Id del dato a borrar.
	 * @param tabla Tabla de donde se borrará el dato.
	 * @since 3.0
	 */
	public void borrarDato(String id, String tabla) {
		
		String consulta = null;
		
		if (tabla.equals("juegos")) consulta = "DELETE FROM juegos WHERE id = ?;";
		if (tabla.equals("clientes")) consulta = "DELETE FROM clientes WHERE id = ?;";
		
		try {		
			PreparedStatement stmt = conexion.prepareStatement(consulta);
			stmt.setString(1, id);	
			
			stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public void agregarPartida(Cliente cliente, Juego juego, double resultadoApusta) {
		
		String consulta = "INSERT INTO partidas(id_cliente, id_juego, resultado_apuesta, cliente_gana, fecha) "
				+ "VALUES (?, ?, ?, ?, NOW())";
		
		try {
			PreparedStatement stmt = conexion.prepareStatement(consulta);
			stmt.setInt(1, cliente.getId());
			stmt.setInt(2, juego.getId());
			stmt.setDouble(3, resultadoApusta);
			stmt.setBoolean(4, ( resultadoApusta > 0 ? true : false) );
			
			stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
