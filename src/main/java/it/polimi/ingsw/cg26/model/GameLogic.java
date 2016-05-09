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
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
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
        this.gameboard=gameBoard;
    }

    /**
     * 
     */
    public void addPlayer(Player player) {
        this.players.add(player);
    }

    /**
     * 
     */
    public void start() {
        boolean endgame = false;
        int i=0;
        while(!endgame){
        	currentPlayer = players.get(i);
        	//turno
        	if(i==players.size()-1){
        		i=0;
        	} else {
        		i++;
        	}
        }
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
        if(region == null || color == null){
        	throw new NullPointerException();
        } else {
        	this.gameboard.elect(region, color);
        }
    }
    
    /**
     * 
     * @param region
     * @param color
     */
    public void electAsMainAction(String region, String color){
    	if(region == null || color == null){
    		throw new NullPointerException();
    	} else {
    		this.elect(region, color);
    		this.currentPlayer.addCoins(4);
    		this.currentPlayer.performMainAction();
    	}
    }

    /**
     * @param
     */
    public void acquireBPT(Collection<String> politicCardsColors, String region, int numberBPT) {
    	if(politicCardsColors == null || region == null){
    		throw new NullPointerException();
    	} else {
    		List<PoliticCard> usedPoliticCards = currentPlayer.getCards(politicCardsColors);
    		this.gameboard.acquireBPT(usedPoliticCards, region, numberBPT);
    		this.currentPlayer.performMainAction();
    	}
    }

    /**
     * @param city 
     */
    public void build(String city) {
    	if(city == null){
    		throw new NullPointerException();
    	} else {
    		this.gameboard.build(currentPlayer, city);
    		this.currentPlayer.performMainAction();
    	}
    }

    /**
     * @param city 
     */
    public void buildKing(String city, Collection<String> politicCardsColors) {
    	if(city == null){
    		throw new NullPointerException();
    	} else {
    		List<PoliticCard> usedPoliticCards = currentPlayer.getCards(politicCardsColors);
    		this.gameboard.buildKing(usedPoliticCards, city);
    		this.currentPlayer.performMainAction();
    	}
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
    	if(region == null){
    		throw new NullPointerException();
    	} else {
    		this.gameboard.changeBPT(region);
    		this.currentPlayer.performQuickAction();
    	}
    }

    /**
     * @param region 
     * @param color
     */
    public void electWithAssistant(String region, String color) {
    	if(region == null || color == null){
    		throw new NullPointerException();
    	} else {
    		this.currentPlayer.takeAssistant();
    		this.elect(region, color);
    		this.currentPlayer.performQuickAction();
    	}
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