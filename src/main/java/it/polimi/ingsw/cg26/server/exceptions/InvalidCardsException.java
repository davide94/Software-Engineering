package it.polimi.ingsw.cg26.server.exceptions;

public class InvalidCardsException extends Exception {

	private static final long serialVersionUID = 818295382657070188L;

	public InvalidCardsException() {
		//Create simple exception
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
