package logica;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Controlador {

	// Definición constantes
	private static final double saldoMin = 100;
	private static final double saldoMax = 999999;
	private static final int edadMax = 95;
	private static final int longitudNombreMax = 30;
	private static final double dineroMin = 1000;
	private static final double dineroMax = 999999;
	
	
	public void cerrarVentana(JFrame ventanaActual, JFrame ventanaAnterior, boolean cerrar) {
		
		if (cerrar) {
			ventanaActual.dispose();
			ventanaAnterior.setVisible(true);
			
		} else {
			ventanaActual.setVisible(false);
			ventanaAnterior.setVisible(true);
		}
	}
	
	
	public boolean validarNombreCliente(String texto, JLabel mensajeError) {
		
		if (texto.isBlank()) {
			mensajeError.setText("");
			return false;
		}
		
		if (!texto.matches("[a-zA-Z ]+")) {
			mensajeError.setText("No se permiten caracteres especiales ni números");
			return false;
		}
		
		if (texto.length() > longitudNombreMax) {
			mensajeError.setText("Nombre demasiado largo (" + longitudNombreMax + " máx.)");
			return false;
		}		
		
		mensajeError.setText("");
		return true;
	}
	
	
	public boolean validarEdadCliente(String texto, JLabel mensajeError) {
		
		if (texto.isBlank()) {
			mensajeError.setText("");
			return false;
		}
		
		if (!texto.matches("[0-9]+")) {
			mensajeError.setText("Ingresa únicamente números");
			return false;
		}
		
		int edad = Integer.parseInt(texto);
		
		if (edad < 18) {
			mensajeError.setText("Cliente menor de edad.");
			return false;
		}
		
		if (edad > edadMax) {
			mensajeError.setText("Cliente demasiado mayor (" + edadMax + " máx.)");
			return false;
		}
		
		mensajeError.setText("");
		return true;
	}
	
	
	public boolean validarSaldoCliente(String texto, JLabel mensajeError) {
		
		double saldo;
		
		if (texto.isBlank()) {
			mensajeError.setText("");
			return false;
		}
		
		try {
			saldo = Integer.parseInt(texto);
		
		} catch (NumberFormatException ee) {
			mensajeError.setText("Ingresa únicamente números");
			return false;
		}
		
		if (saldo < saldoMin) {
			mensajeError.setText("Saldo muy pequeño (" + saldoMin + " min.)");
			return false;
		}
		
		if (saldo > saldoMax) {
			mensajeError.setText("Saldo demasiado grande (" + saldoMax + " max.)");
			return false;
		}
		
		mensajeError.setText("");
		return true;
	}
	
	
	public boolean validarDineroJuego(String texto, JLabel mensajeError) {
		
		double saldo;
		
		if (texto.isBlank()) {
			mensajeError.setText("");
			return false;
		}
		
		try {
			saldo = Double.parseDouble(texto);
		
		} catch (NumberFormatException ee) {
			mensajeError.setText("Ingresa únicamente números");
			return false;
		}
		
		if (saldo < dineroMin) {
			mensajeError.setText("Cantidad muy pequeña (" + dineroMin + " min.)");
			return false;
		}
		
		if (saldo > dineroMax) {
			mensajeError.setText("Cantidad demasiado grande (" + dineroMax + " max.)");
			return false;
		}
		
		mensajeError.setText("");
		return true;
	}
}






























