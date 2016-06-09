package it.polimi.ingsw.cg26.server.exceptions;

/**
 *
 */
public class NoRemainingCardsException extends Exception {

    public NoRemainingCardsException() {

    }

    public NoRemainingCardsException(String message) {
        super(message);
    }

    public NoRemainingCardsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRemainingCardsException(Throwable cause) {
        super(cause);
    }
}
