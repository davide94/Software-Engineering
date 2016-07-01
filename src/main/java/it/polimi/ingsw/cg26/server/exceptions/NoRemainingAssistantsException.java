package it.polimi.ingsw.cg26.server.exceptions;

/**
 *
 */
public class NoRemainingAssistantsException extends Exception {

	private static final long serialVersionUID = 4665403442038317155L;

	public NoRemainingAssistantsException() {
		//Create simple exception
    }

    public NoRemainingAssistantsException(String message) {
        super(message);
    }

    public NoRemainingAssistantsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRemainingAssistantsException(Throwable cause) {
        super(cause);
    }
}
