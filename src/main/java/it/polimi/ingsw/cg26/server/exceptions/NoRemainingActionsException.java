package it.polimi.ingsw.cg26.server.exceptions;

/**
 *
 */
public class NoRemainingActionsException extends Exception {

    public NoRemainingActionsException() {

    }

    public NoRemainingActionsException(String message) {
        super(message);
    }

    public NoRemainingActionsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRemainingActionsException(Throwable cause) {
        super(cause);
    }
}