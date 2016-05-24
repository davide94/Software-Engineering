package it.polimi.ingsw.cg26.server.exceptions;

public class NotValidSellableException extends RuntimeException{
	
	/**
     * Default constructor
     */
    public NotValidSellableException() {
        super();
    }

    public NotValidSellableException(String message) {
        super(message);
    }

}
