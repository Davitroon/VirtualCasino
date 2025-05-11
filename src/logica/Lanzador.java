package logica;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import ventanas.MenuPrincipal;

public class Lanzador {
	
	private static Modelo modelo;
	private static Controlador controlador;
	private static MenuPrincipal menu;
	
	public static void main(String[] args) {
		
		SQLException sqlEx = null;

		try {
			modelo = new Modelo();
			controlador = new Controlador();
			menu = new MenuPrincipal(modelo, controlador);		
			menu.setVisible(true);
			
		// BD no existe
		} catch (SQLSyntaxErrorException e) {
			sqlEx = e;
			System.out.println("Error de Conexión con BD: Base de datos no encontrada, utiliza la guía de instalación y revisa que el nombre de la base de datos sea correcto.");
			
		// Servicio MYSQL apagado
		} catch (CommunicationsException e) {
			sqlEx = e;
			System.out.println("Error de Conexión con BD: Asegurate de que el servicio MySQL80 este en ejecución y de no haber modificado el puerto y host en el código del programa.");
			
		} catch (SQLException e) {
			sqlEx = e;
			
			// Error en el login
			if (e.getErrorCode() == 1045) {
				System.out.println("Error de Conexión con BD: Usuario o contraseña incorrectos.");
				
			} else {
				System.out.println("- Error de Conexión con BD -");
			}
			
		} catch (Exception e) {
			System.out.println("– Error de Conexión con BD –");
		
		} finally {
			if (sqlEx != null) {
				System.out.println("(" + sqlEx.getClass().getSimpleName() + ") " + sqlEx.getMessage() + " -- " + sqlEx.getSQLState() + " cod.:" + sqlEx.getErrorCode());
				 sqlEx.printStackTrace();
			}
		}
	}
	
	
	
}
