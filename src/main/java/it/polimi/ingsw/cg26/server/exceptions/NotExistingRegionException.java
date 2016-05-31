package it.polimi.ingsw.cg26.server.exceptions;

public class NotExistingRegionException extends RuntimeException {

	/**
     * Default constructor
     */
    public NotExistingRegionException() {
        super();
    }

    public NotExistingRegionException(String message) {
        super(message);
    }
}
