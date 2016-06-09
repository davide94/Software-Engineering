package it.polimi.ingsw.cg26.common.update.event;

import it.polimi.ingsw.cg26.common.ClientModel;

/**
 *
 */
public class GameStarted implements Event {

    @Override
    public void apply(ClientModel model) {
        model.getState().gameStarted();
    }
}
