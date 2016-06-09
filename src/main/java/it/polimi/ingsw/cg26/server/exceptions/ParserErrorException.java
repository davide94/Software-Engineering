package it.polimi.ingsw.cg26.server.exceptions;

/**
 *
 */
public class ParserErrorException extends Exception {

    public ParserErrorException() {

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
