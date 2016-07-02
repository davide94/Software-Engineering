package it.polimi.ingsw.cg26.client.model.state;

import java.util.Map;

/**
 *The public interface of the states
 */
public interface State {

    Map<String, String> commands();

    default boolean isYourTurn() {
        return false;
    }

    default boolean isYourTurntoBuy() {
        return false;
    }

    default boolean isYourTurnToSell() {
        return false;
    }

    default boolean isPendingBPTBonusRequest() {
        return false;
    }

    default boolean isPendingCityBonusRequest() {
        return false;
    }

    default boolean isPendingPlayerBonusRequest() {
        return false;
    }
}
