package it.polimi.ingsw.cg26.server.exceptions;

public class ExistingEmporiumException extends Exception {

    public ExistingEmporiumException() {

    }

    public ExistingEmporiumException(String message) {
        super(message);
    }

    public ExistingEmporiumException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistingEmporiumException(Throwable cause) {
        super(cause);
    }
}
