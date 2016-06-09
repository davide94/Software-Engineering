package it.polimi.ingsw.cg26.server.exceptions;

public class InvalidRegionException extends Exception {

    public InvalidRegionException() {

    }

    public InvalidRegionException(String message) {
        super(message);
    }

    public InvalidRegionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRegionException(Throwable cause) {
        super(cause);
    }
}
