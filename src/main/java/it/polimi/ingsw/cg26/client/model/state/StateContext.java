package it.polimi.ingsw.cg26.client.model.state;

import it.polimi.ingsw.cg26.common.ClientState;

import java.util.Map;

/**
 *
 */
public class StateContext implements ClientState {

    private State state;

    public StateContext() {
        this.state = new GameNotStarted();
    }

    public Map<String, String> commands() {
        return state.commands();
    }

    public void gameStarted() {
        System.out.println("gameStarted");
        state = new NotYourTurn();
    }

    public void gameEnded() {
        System.out.println("gameEnded");
        state = new GameEnded();
    }

    public void turnStarted() {
        System.out.println("turnStarted");
        state = new YourTurn();
    }

    public void turnEnded() {
        System.out.println("turnEnded");
        state = new NotYourTurn();
    }

    public void marketStarted() {
        System.out.println("marketStarted");
        state = new NotYourTurnMarket();
    }

    public void marketEnded() {
        System.out.println("marketEnded");
        state = new NotYourTurn();
    }

    public void sellTurnStarted() {
        System.out.println("sellTurnStarted");
        state = new YourTurnToSell();
    }

    public void sellTurnEnded() {
        System.out.println("sellTurnEnded");
        state = new NotYourTurnMarket();
    }

    public void buyTurnStarted() {
        System.out.println("buyTurnStarted");
        state = new YourTurnToBuy();
    }

    public void buyTurnEnded() {
        System.out.println("buyTurnEnded");
        state = new NotYourTurnMarket();
    }

    public void actionSuccessful() {
        System.out.println("actionSuccessful");
    }

    public void actionFailed() {
        System.out.println("actionFailed");
    }

}
