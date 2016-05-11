package it.polimi.ingsw.cg26.exceptions;

/**
 *
 */
public class BadInputFileException extends RuntimeException {

    public BadInputFileException() {
        // Nothing to do here
    }

    public BadInputFileException(String message) {
        super(message);
    }

    public BadInputFileException(Throwable cause) {
        super(cause);
    }

}
