package it.polimi.ingsw.cg26.server.exceptions;

public class InvalidSellableException extends Exception{

    public InvalidSellableException() {

    }

    public InvalidSellableException(String message) {
        super(message);
    }

    public InvalidSellableException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSellableException(Throwable cause) {
        super(cause);
    }
}
