package excepciones;

/**
 * Excepción que se lanzará cuando una apuesta no sea posible por diferentes motivos.
 * @param mensaje Mensaje del error.
 */
public class ApuestaExcepcion extends Exception {
	
    public ApuestaExcepcion(String mensaje) {
        super(mensaje);
    }
}