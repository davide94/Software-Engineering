package it.polimi.ingsw.cg26.client.model.state;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.client.model.state.pendingRequest.PendingBPTRequest;
import it.polimi.ingsw.cg26.client.model.state.pendingRequest.PendingCityBonusRequest;
import it.polimi.ingsw.cg26.client.model.state.pendingRequest.PendingPlayerRequest;
import it.polimi.ingsw.cg26.common.ClientState;
import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.common.dto.CityDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A state machine that simulates the client status
 */
public class StateContext implements ClientState {

	/**
	 * the state of the machine
	 */
    private State state;

    private Model model;
    
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Public constructor
     */
    public StateContext(Model model) {
        this.state = new MatchNotStarted();
        this.model = model;
    }

    public Map<String, String> commands() {
        return state.commands();
    }

    public boolean isYourTurn() {
        return state.isYourTurn();
    }

    public boolean isYourTurnToBuy() {
        return state.isYourTurnToBuy();
    }

    public boolean isYourTurnToSell() {
        return state.isYourTurnToSell();
    }

    public boolean isMatchEnded() {
        return state.isMatchEnded();
    }
    
    /**
     * Print a message that a regular match is started
     * and change the state
     */
    @Override
    public void regularGameStarted() {
        model.addMessage("Regular game started!");
        log.info("regularGameStarted");
        state = new NotYourTurn();
    }

    /**
     * Print a message that the game is ended
     * and change the state
     */
    @Override
    public void matchEnded() {
        model.addMessage("The match ended!");
        log.info("matchEnded");
        state = new MatchEnded();
    }

    /**
     * Print a message that your turn to play is started
     * and change the state
     */
    @Override
    public void turnStarted() {
        model.addMessage("Your turn started!");
        log.info("turnStarted");
        state = new YourTurn();
    }

    /**
     * Print a message that your turn to play is ended
     * and change the state
     */
    @Override
    public void turnEnded() {
        model.addMessage("Your turn ended!");
        log.info("turnEnded");
        state = new NotYourTurn();
    }

    /**
     * Print a message that the market phase is started
     * and change the state
     */
    @Override
    public void marketStarted() {
        log.info("marketStarted");
        state = new NotYourTurnMarket();
    }

    /**
     * Print a message that your turn to sell is started
     * and change the state
     */
    @Override
    public void sellTurnStarted() {
        model.addMessage("Now is your turn to sell!");
        log.info("sellTurnStarted");
        state = new YourTurnToSell();
    }

    /**
     * Print a message that your turn to sell is ended
     * and change the state
     */
    @Override
    public void sellTurnEnded() {
        log.info("sellTurnEnded");
        state = new NotYourTurnMarket();
    }
    
    /**
     * Print a message that your turn to buy is started
     * and change the state
     */
    @Override
    public void buyTurnStarted() {
        model.addMessage("Now is your turn to buy!");
        log.info("buyTurnStarted");
        state = new YourTurnToBuy();
    }

    /**
     * Print a message that your turn to buy is ended
     * and change the state
     */
    @Override
    public void buyTurnEnded() {
        log.info("buyTurnEnded");
        state = new NotYourTurnMarket();
    }

    /**
     * Print a message if the action has been done successfully
    */
    @Override
    public void actionSuccessful() {
        log.info("actionSuccessful");
    }
    
    /**
     * Print a message if the action is failed
     */
    @Override
    public void actionFailed(String cause) {
        model.addMessage("Action failed because " + cause);
        log.info("actionFailed");
    }

    @Override
    public void pendingBPTRequest(int multiplicity) {
        log.info("pendingBPTRequest");
        state = new PendingBPTRequest(model, multiplicity);
    }

    @Override
    public void pendingCityBonusRequest(int multiplicity) {
        log.info("pendingCityBonusRequest");
        state = new PendingCityBonusRequest(model, multiplicity);
    }

    @Override
    public void pendingPlayerRequest(int multiplicity) {
        log.info("pendingPlayerRequest");
        state = new PendingPlayerRequest(model, multiplicity);
    }

    public void pendingRequestPerformed() {
        state = new YourTurn();
    }

    public Optional<List<BusinessPermissionTileDTO>> getPendingBPTBonusRequest() {
        return state.getPendingBPTBonusRequest();
    }

    public Optional<List<CityDTO>> getPendingCityBonusRequest() {
        return state.getPendingCityBonusRequest();
    }

    public Optional<List<BusinessPermissionTileDTO>> getPendingPlayerBonusRequest() {
        return state.getPendingPlayerBonusRequest();
    }

    public Optional<Integer> getPendingRequestMultiplicity() {
        return state.getPendingRequestMultiplicity();
    }
}
