package it.polimi.ingsw.cg26.common.update.event;

import it.polimi.ingsw.cg26.common.ClientModel;

/**
 *
 */
public class GameEnded implements Event {
    @Override
    public void apply(ClientModel model) {
        model.getState().gameEnded();
    }
}
