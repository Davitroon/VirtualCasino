package logica;

import ventanas.MenuPrincipal;

public class Lanzador {
	
	private static Modelo modelo;
	private static Controlador controlador;
	private static MenuPrincipal menu;
	
	public static void main(String[] args) {
		modelo = new Modelo();
		controlador = new Controlador();
		menu = new MenuPrincipal(modelo, controlador);
		
		menu.setVisible(true);
	}
	
	
	
}
