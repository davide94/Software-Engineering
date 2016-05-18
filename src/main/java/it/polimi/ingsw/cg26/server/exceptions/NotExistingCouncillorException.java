package it.polimi.ingsw.cg26.server.exceptions;

public class NotExistingCouncillorException extends RuntimeException {
	
	/**
     * Default constructor
     */
    public NotExistingCouncillorException() {
        super();
    }

    public NotExistingCouncillorException(String message) {
        super(message);
    }

}
