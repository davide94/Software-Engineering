package it.polimi.ingsw.cg26.server.exceptions;

/**
 *
 */
public class NotEnoughMoneyException extends Exception {

    public NotEnoughMoneyException() {

    }

    public NotEnoughMoneyException(String message) {
        super(message);
    }

    public NotEnoughMoneyException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughMoneyException(Throwable cause) {
        super(cause);
    }
}