package it.polimi.ingsw.cg26.server.exceptions;

/**
 *
 */
public class BadInputFileException extends Exception {

	private static final long serialVersionUID = 1387498201091393659L;

	public BadInputFileException() {
		//Create simple exception
    }

    public BadInputFileException(String message) {
        super(message);
    }

    public BadInputFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadInputFileException(Throwable cause) {
        super(cause);
    }
}
