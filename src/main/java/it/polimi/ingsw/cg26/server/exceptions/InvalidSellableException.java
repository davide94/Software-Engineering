package it.polimi.ingsw.cg26.server.exceptions;

public class InvalidSellableException extends Exception{

	private static final long serialVersionUID = -1166003202602249755L;

	public InvalidSellableException() {
		//Create simple exception
    }

    public InvalidSellableException(String message) {
        super(message);
    }

    public InvalidSellableException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSellableException(Throwable cause) {
        super(cause);
    }
}
