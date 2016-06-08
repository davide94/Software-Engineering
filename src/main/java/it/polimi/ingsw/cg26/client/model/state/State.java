package it.polimi.ingsw.cg26.client.model.state;

import it.polimi.ingsw.cg26.common.ClientState;

import java.util.Map;

/**
 *
 */
public interface State extends ClientState {

    Map<String, String> whatCanIDo();

    default State gameStarted() {
        return new NotYourTurn();
    }

    default State gameEnded() {
        return new GameEnded();
    }

    default State turnStarted() {
        return new YourTurn();
    }

    default State turnEnded() {
        return new NotYourTurn();
    }

    default State marketStarted() {
        return new NotYourTurnMarket();
    }

    default State marketEnded() {
        return new NotYourTurn();
    }

    default State sellTurnStarted() {
        return new YourTurnToSell();
    }

    default State sellTurnEnded() {
        return new NotYourTurnMarket();
    }

    default State buyTurnStarted() {
        return new YourTurnToBuy();
    }

    default State buyTurnEnded() {
        return new NotYourTurnMarket();
    }

    default State actionSuccessful() {
        return this;
    }

    default State actionFailed() {
        return this;
    }
}
