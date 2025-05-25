package excepciones;

/**
 * Excepción que se lanzará cuando una apuesta no sea posible por diferentes motivos.
 * @param mensaje Mensaje del error.
 */
public class UsuarioExcepcion extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6672040684515815144L;

	public UsuarioExcepcion(String mensaje) {
        super(mensaje);
    }
}