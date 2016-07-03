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

    default int isPendingBPTBonusRequest() {
        return 0;
    }

    default int isPendingCityBonusRequest() {
        return 0;
    }

    default int isPendingPlayerBonusRequest() {
        return 0;
    }
}
