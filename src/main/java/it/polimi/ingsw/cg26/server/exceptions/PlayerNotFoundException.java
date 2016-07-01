package it.polimi.ingsw.cg26.server.exceptions;

public class PlayerNotFoundException extends Exception {

	private static final long serialVersionUID = 6781931108908802498L;

	public PlayerNotFoundException() {
		//Create simple exception
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
