package it.polimi.ingsw.cg26.common.update.event;

import it.polimi.ingsw.cg26.common.ClientModel;

/**
 *
 */
public class RegularTurnStarted implements Event {

	private static final long serialVersionUID = -3567237381792863259L;

	@Override
    public void apply(ClientModel model) {
        model.getState().turnStarted();
    }
}
