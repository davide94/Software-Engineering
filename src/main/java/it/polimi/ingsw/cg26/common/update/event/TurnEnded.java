package it.polimi.ingsw.cg26.common.update.event;

import it.polimi.ingsw.cg26.common.ClientModel;

/**
 *
 */
public class TurnEnded implements Event {
    @Override
    public void apply(ClientModel model) {
        model.getState().turnEnded();
    }
}