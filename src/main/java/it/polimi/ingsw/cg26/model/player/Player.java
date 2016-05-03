package it.polimi.ingsw.cg26.model.player;

import it.polimi.ingsw.cg26.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.exceptions.NotEnoughMoneyException;
import it.polimi.ingsw.cg26.model.GameLogic;
import it.polimi.ingsw.cg26.model.board.NobilityCell;
import it.polimi.ingsw.cg26.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.model.cards.PoliticCard;

import java.util.HashSet;
import java.util.Set;

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
    private Set<Assistant> assistants = new HashSet<Assistant>();

    /**
     *
     */
    private Set<PoliticCard> cards = new HashSet<PoliticCard>();

    /**
     *
     */
    private Set<BusinessPermissionTile> tiles = new HashSet<BusinessPermissionTile>();

    /**
     * @param  coins number of coins the player have
     */
    public void Player(int coins, Set<Assistant> assistants) {
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
    public void performMainAction() throws NoRemainingActionsException {
        this.mainActions.perform();
    }

    /**
     * 
     */
    public void performQuickAction() throws NoRemainingActionsException {
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
        this.currentNobilityCell = this.currentNobilityCell.next();
        // TODO prendere i bonus nella nuova cella, che per ora non posso fare perché NobilityCell non è ancora implementato
    }

    /**
     * @return
     */
    public NobilityCell getNobilityCell() {
        return this.currentNobilityCell;
    }

    /**
     * @return
     */
    public Assistant takeAssistant() throws NoRemainingAssistantsException {
        if (this.assistants.iterator().hasNext()) {
            return this.assistants.iterator().next();
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
        if (c != null) {
            this.cards.add(c);
        }
    }

    /**
     * @param
     */
    public void addPermissionTile(BusinessPermissionTile bpt) {
        if (bpt != null) {
            this.tiles.add(bpt);
        }
    }

}