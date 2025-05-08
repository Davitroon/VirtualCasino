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
	
	
	public Modelo() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion=DriverManager.getConnection(url,login,pwd);
			System.out.println (" - Conexión con BD establecida -");
			
		} catch (SQLException e) {
			System.out.println (" Error de Conexión con BD: "+e.getMessage()+" -- "+e.getSQLState()+ " cod.:"+e.getErrorCode());
			e.printStackTrace();
			
		} catch (Exception e) {
			System.out.println (" – Error de Conexión con BD -");
			e.printStackTrace();
		}
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
	
	
	public void agregarDato(String tabla, Object dato) {
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
	            consulta = "INSERT INTO clientes (nombre, edad, genero, baja, saldo) VALUES (?, ?, ?, false, ?);";
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
	
	
	public void modificarDato() {
		
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
