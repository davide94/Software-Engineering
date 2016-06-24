package it.polimi.ingsw.cg26.common.update.event;

import it.polimi.ingsw.cg26.common.ClientModel;

/**
 *
 */
public class BuyTurnEnded implements Event {

	private static final long serialVersionUID = 5591945164653040527L;

	@Override
    public void apply(ClientModel model) {
        model.getState().buyTurnEnded();
    }
}
