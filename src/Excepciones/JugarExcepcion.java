package excepciones;

/**
 * Excepción para cuando el usuario intente abrir la ventana de juegos sin que se haya creado ningun usuario o juego antes.
 * @param mensaje Mensaje del error.
 */
public class JugarExcepcion extends Exception {
	
    public JugarExcepcion(String mensaje) {
        super(mensaje);
    }
}