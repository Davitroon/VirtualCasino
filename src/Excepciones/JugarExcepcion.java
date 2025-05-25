package excepciones;

/**
 * Excepción que se lanzará cuando el usuario intente abrir la ventana de juegos sin que se haya creado ningun usuario o juego antes.
 * @param mensaje Mensaje del error.
 */
public class JugarExcepcion extends Exception {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -633752982384900116L;

	public JugarExcepcion(String mensaje) {
        super(mensaje);
    }
}