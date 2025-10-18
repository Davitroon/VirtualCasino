package exceptions;

/**
 * Exception that will be thrown when the user tries to open the games window without any user or game having been created before.
 * @param message The error message.
 */
public class GameException extends Exception {
	
	private static final long serialVersionUID = -633752982384900116L;

	public GameException(String message) {
        super(message);
    }
}