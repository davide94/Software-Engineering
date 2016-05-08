package it.polimi.ingsw.cg26.model.player;

import it.polimi.ingsw.cg26.exceptions.*;
import it.polimi.ingsw.cg26.model.GameLogic;
import it.polimi.ingsw.cg26.model.board.NobilityCell;
import it.polimi.ingsw.cg26.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.model.cards.PoliticCard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 */
public class Player {

    /**
     *
     */
    private GameLogic gameLogic;

    /**
     *
     */
    private VictoryPoints victoryPoints = new VictoryPoints();

    /**
     *
     */
    private Coins coins = new Coins();

    /**
     *
     */
    private Actions mainActions = new MainActions();

    /**
     *
     */
    private Actions quickactions = new QuickActions();

    /**
     *
     */
    private NobilityCell currentNobilityCell;

    /**
     *
     */
    private LinkedList<Assistant> assistants = new LinkedList<>();

    /**
     *
     */
    private LinkedList<PoliticCard> cards = new LinkedList<>();

    /**
     *
     */
    private LinkedList<BusinessPermissionTile> tiles = new LinkedList<>();

    /**
     * @param  coins number of coins the player have
     */
    public Player(GameLogic gameLogic, NobilityCell nobilityCell, int coins, Collection<Assistant> assistants) {
        // TODO

        this.gameLogic = gameLogic;
        this.currentNobilityCell = nobilityCell;

        try {
            this.coins.addCoins(coins);
        } catch (NotEnoughMoneyException e) {
            e.printStackTrace();
        }

        this.assistants.addAll(assistants);
    }

    /**
     * 
     */
    public void performMainAction() {
        this.mainActions.perform();
    }

    /**
     * 
     */
    public void performQuickAction() {
        this.quickactions.perform();
    }

    /**
     * @param
     */
    public void setRemainingMainActions(int i) {
        this.mainActions.setRemaining(i);
    }

    /**
     * @param
     */
    public void setRemainingQuickActions(int i) {
        this.quickactions.setRemaining(i);
    }

    /**
     *
     */
    public void addRemainingMainActions(int increment) {
        this.mainActions.addActions(increment);
    }

    /**
     *
     */
    public void addRemainingQuickActions(int increment) {
        this.quickactions.addActions(increment);
    }

    /**
     *
     */
    public void incrementNobility() {
        if (this.currentNobilityCell.hasNext()) {
            this.currentNobilityCell = this.currentNobilityCell.next();
            // TODO prendere i bonus nella nuova cella, che per ora non posso fare perché NobilityCell non è ancora implementato
        }
        // TODO if not next?
    }

    /**
     * @return
     */
    public NobilityCell getNobilityCell() {
        return this.currentNobilityCell;
    }
    
    public int numberOfAssistants(){
    	return this.assistants.size();
    }

    /**
     * @return
     */
    public Assistant takeAssistant() {
        if (this.assistants.size() > 0) {
            return this.assistants.poll();
        } else {
            throw new NoRemainingAssistantsException();
        }
    }

    /**
     * @param
     */
    public void addAssistant(Assistant assistant) {
        if (assistant != null) {
            this.assistants.add(assistant);
        }
    }

    /**
     * @param increment
     */
    public void addCoins(int increment) {
        if (increment < 0) {
            throw new IllegalArgumentException();
        } else {
            this.coins.addCoins(increment);
        }
    }

    /**
     * @param decrement
     */
    public void removeCoins(int decrement) {
        if (decrement < 0) {
            throw new IllegalArgumentException();
        } else {
            this.coins.removeCoins(decrement);
        }
    }

    /**
     * @param
     */
    public void addVictoryPoints(int increment) {
        this.victoryPoints.addPoints(increment);
    }

    /**
     * @param
     */
    public void addPoliticCard(PoliticCard c) {
        if (c == null) {
            throw new NullPointerException();
        }
        this.cards.add(c);
    }

    /**
     * @param
     */
    public void addPermissionTile(BusinessPermissionTile bpt) {
        if (bpt == null) {
            throw new NullPointerException();
        }
        this.tiles.add(bpt);
    }
    
    /**
     * 
     * @param politicCardsColors
     * @return list of card selected by the player to be used
     */
    public List<PoliticCard> getCards(String[] politicCardsColors){
    	List<PoliticCard> usedPoliticCards = new ArrayList<PoliticCard>();
    	for(PoliticCard iterCard : this.cards){
    		for(String iterColor : politicCardsColors){
    			if(iterCard.getColor().colorString()==iterColor){
    				usedPoliticCards.add(iterCard);
    				iterColor=null;
    				break;
    			}
    		}
    	}
    	for(String iterColor : politicCardsColors){
    		if(iterColor!=null){
    			throw new InvalidCardsException();
    		}
    	}
    	return usedPoliticCards;
    }

}