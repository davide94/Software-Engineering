package it.polimi.ingsw.cg26.server.exceptions;

public class InvalidCityException extends Exception {

	private static final long serialVersionUID = 205525068577357148L;

	public InvalidCityException() {
		//Create simple exception
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
