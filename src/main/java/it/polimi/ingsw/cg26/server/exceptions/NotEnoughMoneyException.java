package it.polimi.ingsw.cg26.server.exceptions;

/**
 *
 */
public class NotEnoughMoneyException extends RuntimeException {

    /**
     * Default constructor
     */
    public NotEnoughMoneyException() {
        super();
    }

    public NotEnoughMoneyException(String message) {
        super(message);
    }

}