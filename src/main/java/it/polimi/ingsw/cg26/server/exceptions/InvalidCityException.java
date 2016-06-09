package it.polimi.ingsw.cg26.server.exceptions;

public class InvalidCityException extends Exception {

    public InvalidCityException() {

    }

    public InvalidCityException(String message) {
        super(message);
    }

    public InvalidCityException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCityException(Throwable cause) {
        super(cause);
    }
}
