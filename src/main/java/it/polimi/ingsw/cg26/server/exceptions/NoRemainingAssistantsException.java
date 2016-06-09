package it.polimi.ingsw.cg26.server.exceptions;

/**
 *
 */
public class NoRemainingAssistantsException extends Exception {

    public NoRemainingAssistantsException() {

    }

    public NoRemainingAssistantsException(String message) {
        super(message);
    }

    public NoRemainingAssistantsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRemainingAssistantsException(Throwable cause) {
        super(cause);
    }
}
