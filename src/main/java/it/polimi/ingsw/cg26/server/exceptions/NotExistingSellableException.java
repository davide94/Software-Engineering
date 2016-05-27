package it.polimi.ingsw.cg26.server.exceptions;

public class NotExistingSellableException extends RuntimeException {

	/**
     * Default constructor
     */
    public NotExistingSellableException() {
        super();
    }

    public NotExistingSellableException(String message) {
        super(message);
    }
}
