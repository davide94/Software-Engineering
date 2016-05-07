package it.polimi.ingsw.cg26.model;

import it.polimi.ingsw.cg26.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.model.board.GameBoard;
import it.polimi.ingsw.cg26.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.model.market.Sellable;
import it.polimi.ingsw.cg26.model.player.Assistant;
import it.polimi.ingsw.cg26.model.player.Player;
import it.polimi.ingsw.cg26.observer.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 
 */
public class GameLogic extends Observable {
	
	private Player currentPlayer;
	
	private List<Player> players;
	
	private GameBoard gameboard;
	
    /**
     * 
     */
    public GameLogic(GameBoard gameBoard) {
    	players = new ArrayList<Player>();
        // TODO implement here
    }

    /**
     * 
     */
    public void addPlayer(Player player) {
        // TODO implement here
    }

    /**
     * 
     */
    public void start() {
        // TODO implement here
    }

    /**
     * @return
     */
    public void draw() {
    	this.currentPlayer.addPoliticCard(this.gameboard.getPoliticDeck().draw());
    }

    /**
     * @param region 
     * @param color
     */
    private void elect(String region, String color) {
        this.gameboard.elect(region, color);
    }
    
    /**
     * 
     * @param region
     * @param color
     */
    public void electAsMainAction(String region, String color){
    	this.elect(region, color);
    	this.currentPlayer.addCoins(4);
    }

    /**
     * @param
     */
    public void acquireBPT(String[] politicCardsColors, String regions, int numberBPT) {
    	List<PoliticCard> usedPoliticCards = currentPlayer.getCards(politicCardsColors);
    	this.gameboard.acquireBPT(usedPoliticCards, regions, numberBPT);
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
    public void buildKing(String city, String[] politicCardsColors) {
    	List<PoliticCard> usedPoliticCards = currentPlayer.getCards(politicCardsColors);
    	this.gameboard.buildKing(usedPoliticCards, city);
        // TODO implement here
    }

    /**
     * 
     */
    public void engageAssistant() {
    	this.currentPlayer.addAssistant(new Assistant());
    	this.currentPlayer.performQuickAction();
    }

    /**
     * @param region
     */
    public void changeBPT(String region) {
    	if(this.gameboard.changeBPT(region)){
    		this.currentPlayer.performQuickAction();
    	}
    }

    /**
     * @param region 
     * @param color
     */
    public void electWithAssistant(String region, String color) {
    	this.currentPlayer.takeAssistant();
    	this.elect(region, color);
        // TODO implement here
    }

    /**
     * 
     */
    public void additionalMainAction() {
    	if(currentPlayer.numberOfAssistants()>=3){
    		for (int i=0; i<3; i++) {
    			this.currentPlayer.takeAssistant();
			}
    		this.currentPlayer.addRemainingMainActions(1);
    		this.currentPlayer.performQuickAction();
    	} else {
    		throw new NoRemainingActionsException();
    	}
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