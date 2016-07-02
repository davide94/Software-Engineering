package it.polimi.ingsw.cg26.client.model.state;

import java.util.Map;

/**
 *The public interface of the states
 */
public interface State {

    Map<String, String> commands();

    default boolean yourTurn() {
        return false;
    }

    default boolean yourTurntoBuy() {
        return false;
    }

    default boolean yourTurnToSell() {
        return false;
    }

}
