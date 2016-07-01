package it.polimi.ingsw.cg26.server.exceptions;

public class InvalidTileException extends Exception {

	private static final long serialVersionUID = -5432135724829724910L;

	public InvalidTileException() {
		//Create simple exception
    }

    public InvalidTileException(String message) {
        super(message);
    }

    public InvalidTileException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTileException(Throwable cause) {
        super(cause);
    }
}
