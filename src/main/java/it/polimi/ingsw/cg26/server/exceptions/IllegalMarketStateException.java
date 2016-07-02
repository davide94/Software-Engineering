package it.polimi.ingsw.cg26.server.exceptions;

public class IllegalMarketStateException extends Exception {

	private static final long serialVersionUID = 2120518521239823231L;

	public IllegalMarketStateException() {
		//Create simple exception
    }

    public IllegalMarketStateException(String message) {
        super(message);
    }

    public IllegalMarketStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalMarketStateException(Throwable cause) {
        super(cause);
    }
}
