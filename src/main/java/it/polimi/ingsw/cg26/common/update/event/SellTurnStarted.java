package it.polimi.ingsw.cg26.common.update.event;

import it.polimi.ingsw.cg26.common.ClientModel;

/**
 *
 */
public class SellTurnStarted implements Event {

	private static final long serialVersionUID = 2382195842442679095L;

	@Override
    public void apply(ClientModel model) {
        model.getState().sellTurnStarted();
    }
}
