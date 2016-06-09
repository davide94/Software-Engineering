package it.polimi.ingsw.cg26.server.exceptions;

public class CityNotFoundException extends Exception {

    public CityNotFoundException() {

    }

    public CityNotFoundException(String message) {
        super(message);
    }

    public CityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CityNotFoundException(Throwable cause) {
        super(cause);
    }
}
