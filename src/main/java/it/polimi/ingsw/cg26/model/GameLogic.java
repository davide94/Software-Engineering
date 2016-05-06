package it.polimi.ingsw.cg26.model;

import it.polimi.ingsw.cg26.exceptions.*;
import it.polimi.ingsw.cg26.model.board.GameBoard;
import it.polimi.ingsw.cg26.model.cards.*;
import it.polimi.ingsw.cg26.model.market.Sellable;
import it.polimi.ingsw.cg26.model.player.Player;
import it.polimi.ingsw.cg26.observer.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 
 */
public class GameLogic extends Observable {
	
	private GameBoard gameboard;
	
	private Player currentPlayer;
	
	private List<Player> players;
	
	private List<Councillor> councillorsPool;

    /**
     * 
     */
    public GameLogic() {
    	this.players = new ArrayList<Player>();
    	this.councillorsPool = new ArrayList<Councillor>();
        // TODO implement here
    }

    /**
     * 
     */
    public void newPlayer() {
        // TODO implement here
    }

    /**
     * 
     */
    public void start() {
        // TODO implement here
    }

    /**
     * 
     */
    public void draw() {
        currentPlayer.addPoliticCard(this.gameboard.getPoliticDeck().draw());
    }

    /**
     * @param region 
     * @param color
     */
    public void elect(String region, String color) {
    	for(Councillor councillor : councillorsPool){
    		if(councillor.getColor().colorString()==color){
    			Councillor droppedCouncillor = this.gameboard.elect(region, councillor);
    			councillorsPool.remove(councillor);
    			councillorsPool.add(droppedCouncillor);
    			return;
    		}
    	}
    	throw new NotExistingCouncillorException();
    }

    /**
     * @param
     */
    public void acquireBPT(PoliticCard[] c, String regions, int i) {
        // TODO implement here
    }

    /**
     * @param city 
     */
    public void build(String city, BusinessPermissionTile b) {
        // TODO implement here
    }

    /**
     * @param city 
     */
    public void buildKing(String city, PoliticCard[] c) {
        // TODO implement here
    }

    /**
     * 
     */
    public void engageAssistant() {
        // TODO implement here
    }

    /**
     * @param region
     */
    public void changeBPT(String region) {
        // TODO implement here
    }

    /**
     * @param region 
     * @param color
     */
    public void electWithAssistant(String region, String color) {
        // TODO implement here
    }

    /**
     * 
     */
    public void additionalMainAction() {
        // TODO implement here
    }

    /**
     * @param a
     */
    public void sell(Set<Sellable> a) {
        // TODO implement here
    }

    /**
     * 
     */
    public void buy() {
        // TODO implement here
    }


}