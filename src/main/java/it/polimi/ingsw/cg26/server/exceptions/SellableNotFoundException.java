package it.polimi.ingsw.cg26.server.exceptions;

public class SellableNotFoundException extends Exception {

	private static final long serialVersionUID = -5779374003077616070L;

	public SellableNotFoundException() {
		//Create simple exception
    }

    public SellableNotFoundException(String message) {
        super(message);
    }

    public SellableNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SellableNotFoundException(Throwable cause) {
        super(cause);
    }
}
