package it.polimi.ingsw.cg26.server.exceptions;

public class NotYourTurnException extends Exception {

	private static final long serialVersionUID = -182848219293139060L;

	public NotYourTurnException() {
		//Create simple exception
	}
	
	public NotYourTurnException(String message) {
        super(message);
    }

    public NotYourTurnException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotYourTurnException(Throwable cause) {
        super(cause);
    }
}
