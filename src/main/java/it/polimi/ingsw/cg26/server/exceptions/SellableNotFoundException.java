package it.polimi.ingsw.cg26.server.exceptions;

public class SellableNotFoundException extends Exception {

    public SellableNotFoundException() {

    }

    public SellableNotFoundException(String message) {
        super(message);
    }

    public SellableNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SellableNotFoundException(Throwable cause) {
        super(cause);
    }
}
