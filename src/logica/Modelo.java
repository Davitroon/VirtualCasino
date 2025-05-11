package logica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Modelo {
	
	private String database = "casino25";
	private String login = "root"; 
	private String pwd = "Coco2006";
	private String url = "jdbc:mysql://localhost/" + database;
	private static Connection conexion;
	
	
	public Modelo() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		conexion=DriverManager.getConnection(url,login,pwd);
		System.out.println (" - Conexión con BD establecida -");
	}
	
	
	public ResultSet consultarDatos(String tabla) {
		
		String consulta = null;
		ResultSet rset = null;
		
		if (tabla.equals("juegos")) consulta = "SELECT * FROM juegos";
		if (tabla.equals("clientes")) consulta = "SELECT * FROM clientes";
		
		try {
			PreparedStatement stmt = conexion.prepareStatement(consulta);
	        System.out.println("Consulta BBDD: " + stmt.toString());
			rset = stmt.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return rset;
	}
	
	
	public ResultSet consultarDatoUnico(String tabla, int id) {
		
		String consulta = null;
		ResultSet rset = null;
		
		if (tabla.equals("juegos")) consulta = "SELECT * FROM juegos WHERE id = ?;";
		if (tabla.equals("clientes")) consulta = "SELECT * FROM clientes WHERE id = ?;";
		
		try {
			PreparedStatement stmt = conexion.prepareStatement(consulta);
			stmt.setInt(1, id);
	        System.out.println("Consulta BBDD: " + stmt.toString());
			rset = stmt.executeQuery();
			rset.next();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return rset;
	}
	
	
	public void agregarDato(Object dato) {
		String consulta = null;
		
		try {
	        PreparedStatement stmt = null;

	        if (dato instanceof Juego juego) {
	            consulta = "INSERT INTO juegos (tipo, activo, dinero) VALUES (?, true, ?);";
	            stmt = conexion.prepareStatement(consulta);
	            stmt.setString(1, juego.getTipo());
	            stmt.setDouble(2, juego.getDinero()); 
	        }

	        if (dato instanceof Cliente cliente) {
	            consulta = "INSERT INTO clientes (nombre, edad, genero, baja, saldo) VALUES (?, ?, ?, true, ?);";
	            stmt = conexion.prepareStatement(consulta);
	            stmt.setString(1, cliente.getNombre());
	            stmt.setInt(2, cliente.getEdad());
	            stmt.setString(3, String.valueOf(cliente.getGenero()));
	            stmt.setDouble(4, cliente.getSaldo());
	        }

	        System.out.println("Consulta BBDD: " + stmt.toString());
            stmt.executeUpdate();
            stmt.close();
	        

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	
	public void modificarDato(Object dato) {
		String consulta = null;
		
		try {
	        PreparedStatement stmt = null;

	        if (dato instanceof Juego juego) {
	            consulta = "UPDATE juegos SET tipo = ?, activo = ?, dinero = ? WHERE id = ?;";
	            stmt = conexion.prepareStatement(consulta);
	            stmt.setString(1, juego.getTipo());
	            stmt.setBoolean(2, juego.isActivo()); 
	            stmt.setDouble(3, juego.getDinero());
	            stmt.setInt(4, juego.getId());
	        }

	        if (dato instanceof Cliente cliente) {
	            consulta = "UPDATE clientes SET nombre = ?, edad = ?, genero = ?, baja = ?, saldo = ? WHERE id = ?;";
	            stmt = conexion.prepareStatement(consulta);
	            stmt.setString(1, cliente.getNombre());
	            stmt.setInt(2, cliente.getEdad());
	            stmt.setString(3, String.valueOf(cliente.getGenero()));
	            stmt.setBoolean(4, cliente.isActivo());
	            stmt.setDouble(5, cliente.getSaldo());
	            stmt.setInt(6, cliente.getId());
	        }

	        System.out.println("Consulta BBDD: " + stmt.toString());
            stmt.executeUpdate();
            stmt.close();
	        

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	
	public void borrarDatos(String id, String tabla) {
		
		String consulta = null;
		
		if (tabla.equals("juegos")) consulta = "DELETE FROM juegos WHERE id = ?;";
		if (tabla.equals("clientes")) consulta = "DELETE FROM clientes WHERE id = ?;";
		
		try {		
			PreparedStatement stmt = conexion.prepareStatement(consulta);
			stmt.setString(1, id);	
			
	        System.out.println("Consulta BBDD: " + stmt.toString());
			stmt.executeUpdate();
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
