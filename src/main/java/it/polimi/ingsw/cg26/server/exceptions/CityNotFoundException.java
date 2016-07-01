package it.polimi.ingsw.cg26.server.exceptions;

public class CityNotFoundException extends Exception {

	private static final long serialVersionUID = -7938825727345696907L;

	public CityNotFoundException() {
		//Create simple exception
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
