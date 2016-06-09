package it.polimi.ingsw.cg26.client.model.state;

import java.util.Map;

/**
 *
 */
public class WaitingActionResponse implements State {
    @Override
    public Map<String, String> whatCanIDo() {
        return null;
    }

    @Override
    public State gameStarted() {
        return null;
    }

    @Override
    public State gameEnded() {
        return null;
    }

    @Override
    public State turnStarted() {
        return null;
    }

    @Override
    public State turnEnded() {
        return null;
    }

    @Override
    public State marketStarted() {
        return null;
    }

    @Override
    public State marketEnded() {
        return null;
    }

    @Override
    public State sellTurnStarted() {
        return null;
    }

    @Override
    public State sellTurnEnded() {
        return null;
    }

    @Override
    public State buyTurnStarted() {
        return null;
    }

    @Override
    public State buyTurnEnded() {
        return null;
    }

    @Override
    public State actionSuccessful() {
        return null;
    }

    @Override
    public State actionFailed() {
        return null;
    }
}
