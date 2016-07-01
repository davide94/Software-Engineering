package it.polimi.ingsw.cg26.server.exceptions;

/**
 *
 */
public class NoRemainingActionsException extends Exception {

	private static final long serialVersionUID = -1597656001777432366L;

	public NoRemainingActionsException() {
		//Create simple exception
    }

    public NoRemainingActionsException(String message) {
        super(message);
    }

    public NoRemainingActionsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRemainingActionsException(Throwable cause) {
        super(cause);
    }
}