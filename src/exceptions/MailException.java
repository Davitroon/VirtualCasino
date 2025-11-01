package exceptions;

/**
 * Exception thrown when an entered email is invalid or does not meet
 * the expected format.
 * <p>
 * This exception can be used to validate email inputs and ensure
 * that only correctly formatted emails are processed by the application.
 * </p>
 * 
 * @author Davitroon
 * @since 3.0
 */
public class MailException extends Exception {

    private static final long serialVersionUID = -6026756649166474590L;

    /**
     * Constructs a new {@code MailException} with the specified detail message.
     * 
     * @param message The error message describing the reason for the exception.
     */
    public MailException(String message) {
        super(message);
    }
}
