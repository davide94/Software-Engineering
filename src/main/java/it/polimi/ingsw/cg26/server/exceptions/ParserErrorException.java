package it.polimi.ingsw.cg26.server.exceptions;

/**
 *
 */
public class ParserErrorException extends RuntimeException {

    public ParserErrorException() {
        // Nothing to do here
    }

    public ParserErrorException(String message) {
        super(message);
    }

    public ParserErrorException(Throwable cause) {
        super(cause);
    }

}
