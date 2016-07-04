package it.polimi.ingsw.cg26.common.update.event;

import it.polimi.ingsw.cg26.common.ClientModel;

/**
 *
 */
public class ActionFailed implements Event {

	private static final long serialVersionUID = -4232169019233788105L;
	
	/**
	 * The exception that made the action fail 
	 */
	private String cause;

	/**
	 * Construct a simple action failed event
	 */
    public ActionFailed() {

    }

    /**
     * Construct an action failed event with the exception that caused it
     * @param cause the description of the cause of failure
     */
    public ActionFailed(String cause) {
        this.cause = cause;
    }

    @Override
    public void apply(ClientModel model) {
        model.getState().actionFailed(cause);
    }
}
