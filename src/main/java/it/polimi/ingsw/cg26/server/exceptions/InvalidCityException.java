package it.polimi.ingsw.cg26.server.exceptions;

public class InvalidCityException extends RuntimeException {
	
	/**
     * Default constructor
     */
    public InvalidCityException() {
        super();
    }

    public InvalidCityException(String message) {
        super(message);
    }

}
