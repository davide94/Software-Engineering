package it.polimi.ingsw.cg26.common.update.event;

import it.polimi.ingsw.cg26.common.ClientModel;

/**
 *
 */
public class ActionSuccessFul implements Event {

	private static final long serialVersionUID = -2320085384038432074L;

	@Override
    public void apply(ClientModel model) {
        model.getState().actionSuccessful();
    }
}
