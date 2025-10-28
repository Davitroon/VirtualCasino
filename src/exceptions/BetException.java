package exceptions;

/**
 * Custom exception thrown when a bet cannot be placed due to various reasons.
 * <p>
 * This exception is used to signal errors related to invalid betting actions
 * in the application, such as insufficient funds, invalid bet amount, or
 * other game-specific constraints.
 * </p>
 * 
 * @author Davitroon
 * @since 3.0
 */
public class BetException extends Exception {

	private static final long serialVersionUID = -4757589471438310285L;

	/**
	 * Constructs a new {@code BetException} with the specified detail message.
	 * 
	 * @param message the detail message explaining the reason for the exception
	 * @since 3.0
	 */
	public BetException(String message) {
		super(message);
	}
}
