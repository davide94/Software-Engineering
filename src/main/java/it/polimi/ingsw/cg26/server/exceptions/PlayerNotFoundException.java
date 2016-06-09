package it.polimi.ingsw.cg26.server.exceptions;

public class PlayerNotFoundException extends Exception {

    public PlayerNotFoundException() {

    }

    public PlayerNotFoundException(String message) {
        super(message);
    }

    public PlayerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlayerNotFoundException(Throwable cause) {
        super(cause);
    }
}
