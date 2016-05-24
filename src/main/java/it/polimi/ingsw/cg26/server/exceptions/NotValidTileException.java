package it.polimi.ingsw.cg26.server.exceptions;

public class NotValidTileException extends RuntimeException {
	
	/**
     * Default constructor
     */
    public NotValidTileException() {
        super();
    }

    public NotValidTileException(String message) {
        super(message);
    }

}
