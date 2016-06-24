package it.polimi.ingsw.cg26.common.update.event;

import it.polimi.ingsw.cg26.common.ClientModel;

/**
 *
 */
public class SellTurnEnded implements Event {

	private static final long serialVersionUID = 1952402333075849773L;

	@Override
    public void apply(ClientModel model) {
        model.getState().sellTurnEnded();
    }
}
