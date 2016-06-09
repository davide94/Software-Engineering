package it.polimi.ingsw.cg26.common.update.event;

import it.polimi.ingsw.cg26.common.ClientModel;

/**
 *
 */
public class ActionFailed implements Event {

    private Exception exception;

    public ActionFailed() {

    }

    public ActionFailed(Exception exception) {
        this.exception = exception;
    }

    public void why() throws Exception {
        throw exception;
    }

    @Override
    public void apply(ClientModel model) {
        model.getState().actionFailed();
    }
}
