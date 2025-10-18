package exceptions;

/**
 * Exception that will be thrown when an entered email is not valid.
 * @param message The error message.
 */
public class MailException extends Exception {

	private static final long serialVersionUID = -6026756649166474590L;
	
	public MailException(String message) {
        super(message);
    }

}