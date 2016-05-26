package it.polimi.ingsw.cg26.server.exceptions;

public class InvalidSellableException extends RuntimeException{
	
	/**
     * Default constructor
     */
    public InvalidSellableException() {
        super();
    }

    public InvalidSellableException(String message) {
        super(message);
    }

}
