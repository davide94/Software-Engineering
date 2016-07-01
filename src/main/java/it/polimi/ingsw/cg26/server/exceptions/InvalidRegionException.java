package it.polimi.ingsw.cg26.server.exceptions;

public class InvalidRegionException extends Exception {

	private static final long serialVersionUID = -6249159769785209429L;

	public InvalidRegionException() {
		//Create simple exception
    }

    public InvalidRegionException(String message) {
        super(message);
    }

    public InvalidRegionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRegionException(Throwable cause) {
        super(cause);
    }
}
