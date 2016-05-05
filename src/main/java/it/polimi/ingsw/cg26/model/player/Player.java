package it.polimi.ingsw.cg26.model.player;

import it.polimi.ingsw.cg26.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.exceptions.NotEnoughMoneyException;
import it.polimi.ingsw.cg26.model.GameLogic;
import it.polimi.ingsw.cg26.model.board.NobilityCell;
import it.polimi.ingsw.cg26.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.model.cards.PoliticCard;

import java.util.Collection;
import java.util.LinkedList;

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
    private LinkedList<Assistant> assistants = new LinkedList<Assistant>();

    /**
     *
     */
    private LinkedList<PoliticCard> cards = new LinkedList<PoliticCard>();

    /**
     *
     */
    private LinkedList<BusinessPermissionTile> tiles = new LinkedList<BusinessPermissionTile>();

    /**
     * @param  coins number of coins the player have
     */
    public void Player(GameLogic gameLogic, int coins, Collection<Assistant> assistants) {
        // TODO

        this.gameLogic = gameLogic;

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

    /**
     * @return
     */
    public Assistant takeAssistant() {
        if (this.assistants.iterator().hasNext()) {
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

}