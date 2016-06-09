package it.polimi.ingsw.cg26.server.exceptions;

public class InvalidTileException extends Exception {

    public InvalidTileException() {

    }

    public InvalidTileException(String message) {
        super(message);
    }

    public InvalidTileException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTileException(Throwable cause) {
        super(cause);
    }
}
