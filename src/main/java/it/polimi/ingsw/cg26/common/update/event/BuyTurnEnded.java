package it.polimi.ingsw.cg26.common.update.event;

import it.polimi.ingsw.cg26.common.ClientModel;

/**
 *
 */
public class BuyTurnEnded implements Event {
    @Override
    public void apply(ClientModel model) {
        model.getState().buyTurnEnded();
    }
}
