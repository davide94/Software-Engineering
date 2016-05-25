package it.polimi.ingsw.cg26.server.exceptions;

public class NotValidPlayerException extends RuntimeException {

	/**
     * Default constructor
     */
    public NotValidPlayerException() {
        super();
    }

    public NotValidPlayerException(String message) {
        super(message);
    }
}
