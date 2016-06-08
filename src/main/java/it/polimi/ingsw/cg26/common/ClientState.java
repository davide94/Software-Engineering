package it.polimi.ingsw.cg26.common;

import it.polimi.ingsw.cg26.client.model.state.*;

/**
 *
 */
public interface ClientState {

    State gameStarted();

    State gameEnded();

    State turnStarted();

    State turnEnded();

    State marketStarted();

    State marketEnded();

    State sellTurnStarted();

    State sellTurnEnded();

    State buyTurnStarted();

    State buyTurnEnded();

    State actionSuccessful();

    State actionFailed();
}
