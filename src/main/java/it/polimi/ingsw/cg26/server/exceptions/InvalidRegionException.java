package it.polimi.ingsw.cg26.server.exceptions;

public class InvalidRegionException extends RuntimeException {
	
	/**
     * Default constructor
     */
    public InvalidRegionException() {
        super();
    }

    public InvalidRegionException(String message) {
        super(message);
    }

}
