package it.polimi.ingsw.cg26.server.exceptions;

public class CouncillorNotFoundException extends Exception {

    public CouncillorNotFoundException() {

    }

    public CouncillorNotFoundException(String message) {
        super(message);
    }

    public CouncillorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouncillorNotFoundException(Throwable cause) {
        super(cause);
    }
}
