package it.polimi.ingsw.cg26.server.exceptions;

/**
 *
 */
public class NotEnoughMoneyException extends Exception {

	private static final long serialVersionUID = -6162340842578012461L;

	public NotEnoughMoneyException() {
		//Create simple exception
    }

    public NotEnoughMoneyException(String message) {
        super(message);
    }

    public NotEnoughMoneyException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughMoneyException(Throwable cause) {
        super(cause);
    }
}