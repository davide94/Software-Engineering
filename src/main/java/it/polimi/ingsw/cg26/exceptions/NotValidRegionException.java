package it.polimi.ingsw.cg26.exceptions;

public class NotValidRegionException extends RuntimeException {
	
	/**
     * Default constructor
     */
    public NotValidRegionException() {
        super();
    }

    public NotValidRegionException(String message) {
        super(message);
    }

}
