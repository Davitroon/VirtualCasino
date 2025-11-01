package exceptions;

/**
 * Exception thrown when an attempt is made to open the games window
 * without any existing user or game created beforehand.
 * <p>
 * This is used to enforce that the application cannot start a game
 * session unless the necessary prerequisites (user and game) exist.
 * </p>
 * 
 * @author Davitroon
 * @since 3.0
 */
public class GameException extends Exception {

    private static final long serialVersionUID = -633752982384900116L;

    /**
     * Constructs a new {@code GameException} with the specified detail message.
     * 
     * @param message The error message describing the reason for the exception.
     */
    public GameException(String message) {
        super(message);
    }
}
