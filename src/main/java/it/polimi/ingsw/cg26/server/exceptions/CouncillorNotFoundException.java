package it.polimi.ingsw.cg26.server.exceptions;

public class CouncillorNotFoundException extends Exception {

	private static final long serialVersionUID = 8706651924756942686L;

	public CouncillorNotFoundException() {
		//Create simple exception
    }

    public CouncillorNotFoundException(String message) {
        super(message);
    }

    public CouncillorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouncillorNotFoundException(Throwable cause) {
        super(cause);
    }
}
