package it.polimi.ingsw.cg26.client.model.state;

import it.polimi.ingsw.cg26.common.ClientState;

import java.util.Map;

/**
 * A state machine that simulates the client status
 */
public class StateContext implements ClientState {

	/**
	 * the state of the machine
	 */
    private State state;

    /**
     * Public constructor
     */
    public StateContext() {
        this.state = new GameNotStarted();
    }

    public Map<String, String> commands() {
        return state.commands();
    }

    
    /**
     * Print a message that a regular match is started
     * and change the state
     */
    public void regularGameStarted() {
        System.out.println("regularGameStarted");
        state = new NotYourTurn();
    }

    
    
    /**
     * Print a message that the game is ended
     * and change the state
     */
    public void matchEnded() {
        System.out.println("matchEnded");
        state = new GameEnded();
    }

    
    /**
     * Print a message that your turn to play is started
     * and change the state
     */
    public void turnStarted() {
        System.out.println("turnStarted");
        state = new YourTurn();
    }

    
    /**
     * Print a message that your turn to play is ended
     * and change the state
     */
    public void turnEnded() {
        System.out.println("turnEnded");
        state = new NotYourTurn();
    }

    /**
     * Print a message that the market phase is started
     * and change the state
     */
    public void marketStarted() {
        System.out.println("marketStarted");
        state = new NotYourTurnMarket();
    }

    
    /**
     * Print a message that your turn to sell is started
     * and change the state
     */
    public void sellTurnStarted() {
        System.out.println("sellTurnStarted");
        state = new YourTurnToSell();
    }

    
    /**
     * Print a message that your turn to sell is ended
     * and change the state
     */
    public void sellTurnEnded() {
        System.out.println("sellTurnEnded");
        state = new NotYourTurnMarket();
    }

    
    /**
     * Print a message that your turn to buy is started
     * and change the state
     */
    public void buyTurnStarted() {
        System.out.println("buyTurnStarted");
        state = new YourTurnToBuy();
    }

    /**
     * Print a message that your turn to buy is ended
     * and change the state
     */
    public void buyTurnEnded() {
        System.out.println("buyTurnEnded");
        state = new NotYourTurnMarket();
    }

    
    /**
     * Print a message if the action has been done successfully
    */
    public void actionSuccessful() {
        System.out.println("actionSuccessful");
    }

    
    /**
     * Print a message if the action is failed
     */
    public void actionFailed() {
        System.out.println("actionFailed");
    }

}
