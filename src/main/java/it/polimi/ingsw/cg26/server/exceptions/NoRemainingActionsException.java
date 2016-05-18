package it.polimi.ingsw.cg26.server.exceptions;

/**
 *
 */
public class NoRemainingActionsException extends RuntimeException {

    /**
     * Default constructor
     */
    public NoRemainingActionsException() {
        super();
    }

    public NoRemainingActionsException(String message) {
        super(message);
    }

}