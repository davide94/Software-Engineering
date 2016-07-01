package it.polimi.ingsw.cg26.server.exceptions;

public class ExistingEmporiumException extends Exception {

	private static final long serialVersionUID = 3873680545650187384L;

	public ExistingEmporiumException() {
		//Create simple exception
    }

    public ExistingEmporiumException(String message) {
        super(message);
    }

    public ExistingEmporiumException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistingEmporiumException(Throwable cause) {
        super(cause);
    }
}
