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
	private Exception exception;

	/**
	 * Construct a simple action failed event
	 */
    public ActionFailed() {

    }

    /**
     * Construct an action failed event with the exception that caused it
     * @param exception the exception that caused the fail
     */
    public ActionFailed(Exception exception) {
        this.exception = exception;
    }

    /**
     * Rethrows the exception that made the action fail
     * @throws Exception the exception that made the action fail
     */
    public void why() throws Exception {
        throw exception;
    }

    @Override
    public void apply(ClientModel model) {
        model.getState().actionFailed();
    }
}
