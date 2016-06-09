package it.polimi.ingsw.cg26.server.exceptions;

public class InvalidCardsException extends Exception {

    public InvalidCardsException() {

    }

    public InvalidCardsException(String message) {
        super(message);
    }

    public InvalidCardsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCardsException(Throwable cause) {
        super(cause);
    }
}
