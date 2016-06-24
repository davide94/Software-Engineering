package it.polimi.ingsw.cg26.common.update.event;

import it.polimi.ingsw.cg26.common.ClientModel;

/**
 *
 */
public class BuyTurnStarted implements Event {

	private static final long serialVersionUID = -6202453713926911026L;

	@Override
    public void apply(ClientModel model) {
        model.getState().buyTurnStarted();
    }
}
