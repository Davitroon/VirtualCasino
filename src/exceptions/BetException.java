package exceptions;

/**
 * Exception that will be thrown when a bet is not possible for different reasons.
 * @param message The error message.
 */
public class BetException extends Exception {
	
	private static final long serialVersionUID = -4757589471438310285L;

	public BetException(String message) {
        super(message);
    }
}