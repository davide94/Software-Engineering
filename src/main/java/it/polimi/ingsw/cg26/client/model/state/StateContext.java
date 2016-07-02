package it.polimi.ingsw.cg26.client.model.state;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.client.model.state.pendingRequest.PendingBPTRequest;
import it.polimi.ingsw.cg26.client.model.state.pendingRequest.PendingCityBonusRequest;
import it.polimi.ingsw.cg26.client.model.state.pendingRequest.PendingPlayerRequest;
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

    private Model model;

    /**
     * Public constructor
     */
    public StateContext(Model model) {
        this.state = new GameNotStarted();
        this.model = model;
    }

    public Map<String, String> commands() {
        return state.commands();
    }

    public boolean yourTurn() {
        return state.isYourTurn();
    }

    public boolean yourTurntoBuy() {
        return state.isYourTurntoBuy();
    }

    public boolean yourTurnToSell() {
        return state.isYourTurnToSell();
    }
    
    /**
     * Print a message that a regular match is started
     * and change the state
     */
    public void regularGameStarted() {
        model.addMessage("The match begun!");
        System.out.println("regularGameStarted");
        state = new NotYourTurn();
    }

    
    
    /**
     * Print a message that the game is ended
     * and change the state
     */
    public void matchEnded() {
        model.addMessage("The match ended!");
        System.out.println("matchEnded");
        state = new GameEnded();
    }

    
    /**
     * Print a message that your turn to play is started
     * and change the state
     */
    public void turnStarted() {
        model.addMessage("Your turn started!");
        System.out.println("turnStarted");
        state = new YourTurn();
    }

    
    /**
     * Print a message that your turn to play is ended
     * and change the state
     */
    public void turnEnded() {
        model.addMessage("Your turn ended!");
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
        model.addMessage("Now is your turn to sell!");
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
        model.addMessage("Action successfully performed!");
        System.out.println("actionSuccessful");
    }

    
    /**
     * Print a message if the action is failed
     */
    public void actionFailed() {
        model.addMessage("Action failed because...");
        System.out.println("actionFailed");
    }

    @Override
    public void pendingBPTRequest(int multiplicity) {
        state = new PendingBPTRequest(model, multiplicity);
    }

    @Override
    public void pendingCityBonusRequest(int multiplicity) {
        state = new PendingCityBonusRequest(model, multiplicity);
    }

    @Override
    public void pendingPlayerRequest(int multiplicity) {
        state = new PendingPlayerRequest(model, multiplicity);
    }

    public boolean isPendingBPTBonusRequest() {
        return state.isPendingBPTBonusRequest();
    }

    public boolean isPendingCityBonusRequest() {
        return state.isPendingCityBonusRequest();
    }

    public boolean isPendingPlayerBonusRequest() {
        return state.isPendingPlayerBonusRequest();
    }
}
