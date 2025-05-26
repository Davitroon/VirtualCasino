package excepciones;

/**
 * Excepción que se lanzará cuando un correo ingresado no sea válido.
 */
public class CorreoExcepcion extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6026756649166474590L;
	
	public CorreoExcepcion(String mensaje) {
        super(mensaje);
    }

}
