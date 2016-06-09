package it.polimi.ingsw.cg26.server.exceptions;

/**
 *
 */
public class BadInputFileException extends Exception {

    public BadInputFileException() {

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
