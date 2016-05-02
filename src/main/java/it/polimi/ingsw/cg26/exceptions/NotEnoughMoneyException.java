package it.polimi.ingsw.cg26.exceptions;

/**
 *
 */
public class NotEnoughMoneyException extends Exception {

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