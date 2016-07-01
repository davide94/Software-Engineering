package it.polimi.ingsw.cg26.server.exceptions;

/**
 *
 */
public class ParserErrorException extends Exception {

	private static final long serialVersionUID = -4297690856437637068L;

	public ParserErrorException() {
		//Create simple exception
    }

    public ParserErrorException(String message) {
        super(message);
    }

    public ParserErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParserErrorException(Throwable cause) {
        super(cause);
    }
}
