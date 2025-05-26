package logica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;

import excepciones.CorreoExcepcion;
import excepciones.MensajeExcepcion;


/**
 * Clase modelo del MVC. Conecta con la BD y gestiona la información.
 * @author David
 * @since 3.0
 */
public class Modelo {
	
	private static Connection conexion;
	private String database = "casino25"; 
	private String login = "root";
	private String pwd = "Coco2006";
	private String url = "jdbc:mysql://localhost/" + database;
	
	private Cliente clienteDefault;
	private Juego juegoDefault;
	
	private MensajeExcepcion mensajeExcepcion;
	private int usuarioActual;
	
	/**
	 * Constructor del modelo, dodne hace conexión a la base de datos.
	 * @param mensajeExcepcion Clase que maneja el aviso de las excepciones.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @since 3.0
	 */
	public Modelo(MensajeExcepcion mensajeExcepcion) throws SQLException, ClassNotFoundException {
		this.mensajeExcepcion = mensajeExcepcion;
		if (database == "") throw new SQLSyntaxErrorException();
		Class.forName("com.mysql.cj.jdbc.Driver");
		conexion=DriverManager.getConnection(url,login,pwd);
		System.out.println (" - Conexión con BD establecida -");
	}
	
	
	/**
	 * Actualizar el último acceso de un usuario al programa.
	 * @param nombre Nombre del usuario actualizar.
	 */
	public void actualizarUltimoAcceso (String nombre) {
		String consulta = "UPDATE users SET last_access = NOW() WHERE username = ?";
		
		try (PreparedStatement stmt = conexion.prepareStatement(consulta)) {
			stmt.setString(1, nombre);
			stmt.executeUpdate();
			
		} catch (SQLException e) {
	    	mensajeExcepcion.mostrarError(e, "Ha ocurrido un error en la conexión con la BD al modificar un usuario.\nConsultar la consola para más información.");
		}
	}
	
	
	
	/**
	 * Método para insertar un cliente en la base de datos.
	 * @param cliente Cliente a agregar.
	 * @since 3.0
	 */
	public void agregarCliente(Cliente cliente) {
	    String consulta = "INSERT INTO customers (customer_name, age, gender, balance, user_profile) VALUES (?, ?, ?, ?, ?);";

	    try (PreparedStatement stmt = conexion.prepareStatement(consulta)) {
	        stmt.setString(1, cliente.getNombre());
	        stmt.setInt(2, cliente.getEdad());
	        stmt.setString(3, String.valueOf(cliente.getGenero()));
	        stmt.setDouble(4, cliente.getSaldo());
	        stmt.setInt(5, usuarioActual);

	        stmt.executeUpdate();
	        

	    } catch (SQLException e) {
	    	mensajeExcepcion.mostrarError(e, "Ha ocurrido un error en la conexión con la BD al insertar un cliente.\nConsultar la consola para más información.");
	    }
	}
	
	
	/**
	 * Método para insertar un juego en la base de datos.
	 * @param juego Juego a agregar.
	 * @since 3.0
	 */
	public void agregarJuego(Juego juego) {
	    String consulta = "INSERT INTO games (game_type, money_pool, user_profile) VALUES (?, ?, ?);";

	    try (PreparedStatement stmt = conexion.prepareStatement(consulta)) {
	        stmt.setString(1, juego.getTipo());
	        stmt.setDouble(2, juego.getDinero());
	        stmt.setInt(3, usuarioActual);

	        stmt.executeUpdate();
	        

	    } catch (SQLException e) {
	    	mensajeExcepcion.mostrarError(e, "Ha ocurrido un error en la conexión con la BD al insertar un juego.\nConsultar la consola para más información.");
	    }
	}
	
	
	/**
	 * Inserta una nueva partida en la base de datos.
	 * @param cliente El cliente que participa en la partida. Debe tener un ID válido.
	 * @param juego El juego en el que se realiza la partida. Debe tener un ID y un tipo ("Blackjack" o "Tragaperras").
	 * @param resultadoApuesta Monto resultante de la apuesta (positivo si el cliente gana, negativo si pierde).
	 */
	public void agregarPartida(Cliente cliente, Juego juego, double resultadoApuesta) {
	    
	    String consulta = "INSERT INTO game_sessions (customer_id, game_id, game_type, bet_result, user_profile) "
	                    + "VALUES (?, ?, ?, ?, ?)";
	    
	    try {
	        PreparedStatement stmt = conexion.prepareStatement(consulta);
	        stmt.setInt(1, cliente.getId());
	        stmt.setInt(2, juego.getId());
	        stmt.setString(3, juego.getTipo());
	        stmt.setDouble(4, resultadoApuesta);
	        stmt.setInt(5, usuarioActual);
	        
	        stmt.executeUpdate();
	        stmt.close();
	        
	    } catch (SQLException e) {
	        mensajeExcepcion.mostrarError(e, "Ha ocurrido un error en la conexión con la BD al insertar una partida.\nConsultar la consola para más información.");
	    }        
	}
	
	
	/**
	 * Método que añade un usuario a la BD.
	 * @param usuario Objeto usuario a agregar.
	 * @param recordarSesion True si se ha indicado que se guarde el inicio de sesion.
	 * @return 
	 * @throws CorreoExcepcion Excepción que se lanza en caso de que el correo no sea válido.
	 * @throws SQLIntegrityConstraintViolationException 
	 */
		public Usuario agregarUsuario (Usuario usuario, boolean recordarSesion) throws CorreoExcepcion, SQLIntegrityConstraintViolationException {
			String consulta = "INSERT INTO users (username, user_password, email) VALUES (?, ?, ?);";
			
		    try (PreparedStatement stmt = conexion.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS)) {
		        stmt.setString(1, usuario.getNombre());
		        stmt.setString(2, usuario.getContrasena());
		        stmt.setString(3, usuario.getCorreo());
	
		        stmt.executeUpdate();
		        
		        // Obtener el ID generado
		        int idGenerado = -1;
		        try (ResultSet rs = stmt.getGeneratedKeys()) {
		            if (rs.next()) {
		                idGenerado = rs.getInt(1);
		            }
		        }

		        ResultSet rset = consultarUsuario(usuario.getNombre(), usuario.getContrasena());
	            if (!rset.getBoolean("verified_email")) {
	                borrarDato(rset.getInt("id"), "users");
	                throw new CorreoExcepcion("El correo ingresado no es válido.");
	            }

	            if (recordarSesion) {
	                alternarRecordarSesion(usuario.getNombre(), recordarSesion);
	            }

	            usuario = new Usuario(idGenerado, rset.getString("username"), rset.getString("user_password"),
	                rset.getString("email"), rset.getString("last_access"), recordarSesion);

	            return usuario;

	             
		    } catch (SQLIntegrityConstraintViolationException e) {
		    	throw new SQLIntegrityConstraintViolationException("El usuario " + usuario.getNombre() + " ya está registrado.");
		    	
		    } catch (SQLException e) {
		    	mensajeExcepcion.mostrarError(e, "Ha ocurrido un error en la conexión con la BD al agregar un usuario.\nConsultar la consola para más información.");
		    }
			return null;
		}
	
	
	/**
	 * Método que alternará el inicio de sesion automático de un usuario.
	 * @param nombre Nombre del usuario a alternar.
	 * @param activar True si se ha marcado que se recuerde la sesión.
	 */
	public void alternarRecordarSesion(String nombre, boolean activar) {
		try {
			// Quitar el resto de usuarios que tengan marcado recordar sesion
			try (PreparedStatement stmt2 = conexion.prepareStatement("UPDATE users SET remember_login = FALSE WHERE remember_login = TRUE")) {
			    stmt2.executeUpdate();
			}
			
			if (activar) {
				// Poner a este usuario como recordar sesion
				try (PreparedStatement stmt3 = conexion.prepareStatement("UPDATE users SET remember_login = TRUE WHERE username = ?")) {
				    stmt3.setString(1, nombre);
				    stmt3.executeUpdate();
				}	
			}

		} catch (SQLException e) {
	    	mensajeExcepcion.mostrarError(e, "Ha ocurrido un error en la conexión con la BD al modificar un usuario.\nConsultar la consola para más información.");
	    } 
	}
	
	
	/**
	 * Método para borrar un dato de la base de datos.
	 * @param id Id del dato a borrar.
	 * @param tabla Tabla de donde se borrará el dato.
	 * @since 3.0
	 */
	public void borrarDato(int id, String tabla) {
		
		String consulta = "DELETE FROM " + tabla + " WHERE id = ?;";
		
		try {		
			PreparedStatement stmt = conexion.prepareStatement(consulta);
			stmt.setInt(1, id);	
			
			stmt.executeUpdate();
			stmt.close();
			
			
		} catch (SQLException e) {
			mensajeExcepcion.mostrarError(e, "Ha ocurrido un error en la conexión con la BD al borrar un dato de la tabla " + tabla + ".\nConsultar la consola para más información.");
		}
	}
	
	/**
	 * Método para borrar todos los datos de una tabla.
	 * @param tabla Tabla a borrar su datos.
	 * @since 3.0
	 */
	public void borrarDatosTabla(String tabla) {
		String consulta = "DELETE FROM " + tabla + " WHERE user_profile = ?";
		
		try {
			PreparedStatement stmt = conexion.prepareStatement(consulta);
			stmt.setInt(1, usuarioActual);
			stmt.executeUpdate();
			stmt.close();
			
		} catch (SQLException e) {
			mensajeExcepcion.mostrarError(e, "Ha ocurrido un error en la conexión con la BD al borrar los datos de la tabla " + tabla + ".\nConsultar la consola para más información.");
		}
		
		
	}
	
	/**
	 * Realiza una consulta a la BD que se reciba por parametro. Recomendado para consultas muy específicas.
	 * @param consulta Consulta a ejecutar
	 * @return Resultado de la consulta SQL.
	 */
	public ResultSet consultaEspecifica(String consulta) {
		
		ResultSet rset = null;
		PreparedStatement stmt;
		try {
			stmt = conexion.prepareStatement(consulta);
			rset = stmt.executeQuery();
			
		} catch (SQLException e) {
			mensajeExcepcion.mostrarError(e, "Ha ocurrido un error en la conexión con la BD al hacer una lectura.\nConsultar la consola para más información.");
		}	
		
		return rset;
	}
	
	
	/**
	 * Método para realizar una consulta de todos los datos de una tabla a la base de datos.
	 * @param tabla Tabla a consultar.
	 * @param soloActivos Mostrar datos que únicamente esten activos.
	 * @return ResultSet (Consulta SQL)
	 * @since 3.0
	 */
	public ResultSet consultarDatos(String tabla, boolean soloActivos) {
		
		String consulta = "SELECT * FROM " + tabla + " WHERE user_profile = ?";	
		ResultSet rset = null;
		 
		if (soloActivos) consulta += " AND active_status = 1";
		
		try {
			PreparedStatement stmt = conexion.prepareStatement(consulta);
			stmt.setInt(1, usuarioActual);
			rset = stmt.executeQuery();
			
			
		} catch (SQLException e) {
	        mensajeExcepcion.mostrarError(e, "Ha ocurrido un error en la conexión con la BD al consultar la tabla " + tabla + ".\nConsultar la consola para más información.");

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
		
		String consulta = "SELECT * FROM " + tabla + " WHERE id = ?";
		ResultSet rset = null;
		
		try {
			PreparedStatement stmt = conexion.prepareStatement(consulta);
			stmt.setInt(1, id);
			rset = stmt.executeQuery();
			rset.next();
			
			
		} catch (SQLException e) {
			mensajeExcepcion.mostrarError(e, "Ha ocurrido un error en la conexión con la BD al consultar la tabla " + tabla + ".\nConsultar la consola para más información.");
		}
	
		return rset;
	}
	
	
	/**
	 * Consulta un usuario de la base de datos. El nombre y contraseña debe ser exactamente el mismo.
	 * @param nombre Nombre del usuario.
	 * @param contraseña Contraseña del usuario.
	 * @return Resultado de la consulta SQL.
	 */
	public ResultSet consultarUsuario (String nombre, String contraseña) {
		String consulta = "SELECT * FROM users WHERE username = ? AND user_password = ?";
		ResultSet rset;
		
	    try {
	    	PreparedStatement stmt = conexion.prepareStatement(consulta);
	        stmt.setString(1, nombre);
	        stmt.setString(2, contraseña);

	        rset = stmt.executeQuery();
	        
	        // Si se encontró un usuario con esa contraseña
	        if (rset.next()) {   
	        	return rset;        	
	        }
             
	    } catch (SQLException e) {
	    	mensajeExcepcion.mostrarError(e, "Ha ocurrido un error en la conexión con la BD al consultar la tabla usuarios.\nConsultar la consola para más información.");
	    }
	    
	    return null;
	}
	
	
	/**
	 * Método para modificar un cliente en la base de datos.
	 * @param cliente Cliente a modificar.
	 * @since 3.0
	 */
	public void modificarCliente(Cliente cliente) {
	    String consulta = "UPDATE customers SET customer_name = ?, age = ?, gender = ?, active_status = ?, balance = ? WHERE id = ?;";
	    
	    try (PreparedStatement stmt = conexion.prepareStatement(consulta)) {
	        stmt.setString(1, cliente.getNombre());
	        stmt.setInt(2, cliente.getEdad());
	        stmt.setString(3, String.valueOf(cliente.getGenero()));
	        stmt.setBoolean(4, cliente.isActivo());
	        stmt.setDouble(5, cliente.getSaldo());
	        stmt.setInt(6, cliente.getId());

	        stmt.executeUpdate();	
	        
	    } catch (SQLException e) {
	    	mensajeExcepcion.mostrarError(e, "Ha ocurrido un error en la conexión con la BD al modificar un cliente.\nConsultar la consola para más información.");
	    }
	}	
	
	
/**
 * Método para modificar el dinero de un juego en la base de datos.
 * @param juego Juego a modificar.
 * @since 3.0
 */
public void modificarDineroJuego(Juego juego) {
    String consulta = "UPDATE games SET money_pool = ? WHERE id = ?;";
    
    try (PreparedStatement stmt = conexion.prepareStatement(consulta)) {
        stmt.setDouble(1, juego.getDinero());
        stmt.setInt(2, juego.getId());

        stmt.executeUpdate();
        
        
    } catch (SQLException e) {
    	mensajeExcepcion.mostrarError(e, "Ha ocurrido un error en la conexión con la BD al modificar el dinero del juego.\nConsultar la consola para más información.");
    }
}
	
	
	/**
	 * Método para modificar un juego en la base de datos.
	 * @param juego Juego a modificar.
	 * @since 3.0
	 */
	public void modificarJuego(Juego juego) {
	    String consulta = "UPDATE games SET game_type = ?, active_status = ?, money_pool = ? WHERE id = ?;";
	    
	    try (PreparedStatement stmt = conexion.prepareStatement(consulta)) {
	        stmt.setString(1, juego.getTipo());
	        stmt.setBoolean(2, juego.isActivo());
	        stmt.setDouble(3, juego.getDinero());
	        stmt.setInt(4, juego.getId());

	        stmt.executeUpdate();
	        
	        
	    } catch (SQLException e) {
	    	mensajeExcepcion.mostrarError(e, "Ha ocurrido un error en la conexión con la BD al modificar un juego.\nConsultar la consola para más información.");
	    }
	}
	
	
	/**
	 * Método para modificar únicamente el saldo de un cliente en la base de datos.
	 * @param cliente Cliente a modificar.
	 * @since 3.0
	 */
	public void modificarSaldoCliente(Cliente cliente) {
	    String consulta = "UPDATE customers SET balance = ? WHERE id = ?;";
	    
	    try (PreparedStatement stmt = conexion.prepareStatement(consulta)) {
	        stmt.setDouble(1, cliente.getSaldo());
	        stmt.setInt(2, cliente.getId());

	        stmt.executeUpdate();
	        
	        
	    } catch (SQLException e) {
	    	mensajeExcepcion.mostrarError(e, "Ha ocurrido un error en la conexión con la BD al modificar el saldo del cliente.\nConsultar la consola para más información.");
	    }
	}


	public void setUsuarioActual(int usuarioActual) {
		this.usuarioActual = usuarioActual;
	}


	/** 
	 * Método que agrega usuarios y juegos pro defecto al crear un usuario.
	 */
	public void agregarUsuarioDefault() {
		clienteDefault = new Cliente("usuario", 32, 'O', 2000);
		agregarCliente(clienteDefault);
		juegoDefault = new Blackjack(10000);
		agregarJuego(juegoDefault);
		juegoDefault = new Tragaperras(10000);
		agregarJuego(juegoDefault);
	}
	
	
}
