package it.polimi.ingsw.cg26.server.exceptions;

public class InvalidTileException extends RuntimeException {
	
	/**
     * Default constructor
     */
    public InvalidTileException() {
        super();
    }

    public InvalidTileException(String message) {
        super(message);
    }

}
