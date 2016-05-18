package it.polimi.ingsw.cg26.server.exceptions;

public class NotValidCityException extends RuntimeException {
	
	/**
     * Default constructor
     */
    public NotValidCityException() {
        super();
    }

    public NotValidCityException(String message) {
        super(message);
    }

}
