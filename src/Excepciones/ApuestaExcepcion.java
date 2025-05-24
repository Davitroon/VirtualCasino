package excepciones;

/**
 * Excepción que se lanzará cuando una apuesta no sea posible por diferentes motivos.
 * @param mensaje Mensaje del error.
 */
public class ApuestaExcepcion extends Exception {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -4757589471438310285L;

	public ApuestaExcepcion(String mensaje) {
        super(mensaje);
    }
}