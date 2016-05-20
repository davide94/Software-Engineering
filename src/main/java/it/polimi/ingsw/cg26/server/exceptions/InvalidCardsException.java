package it.polimi.ingsw.cg26.server.exceptions;

public class InvalidCardsException extends RuntimeException {
	
	/**
     * Default constructor
     */
    public InvalidCardsException() {
        super();
    }

    public InvalidCardsException(String message) {
        super(message);
    }

}