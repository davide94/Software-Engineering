package it.polimi.ingsw.cg26.server.exceptions;

/**
 *
 */
public class NoRemainingCardsException extends Exception {

	private static final long serialVersionUID = 6731561978036980582L;

	public NoRemainingCardsException() {
		//Create simple exception
    }

    public NoRemainingCardsException(String message) {
        super(message);
    }

    public NoRemainingCardsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRemainingCardsException(Throwable cause) {
        super(cause);
    }
}
