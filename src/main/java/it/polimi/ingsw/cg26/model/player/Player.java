package it.polimi.ingsw.cg26.model.player;

import it.polimi.ingsw.cg26.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.exceptions.NotEnoughMoneyException;
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
    VictoryPoints victoryPoints = new VictoryPoints();

    /**
     *
     */
    Coins coins = new Coins();

    /**
     *
     */
    Actions mainActions = new MainActions();

    /**
     *
     */
    Actions quickactions = new QuickActions();

    /**
     *
     */
    NobilityCell currentNobilityCell;

    /**
     *
     */
    Set<Assistant> assistants = new HashSet<Assistant>();

    /**
     *
     */
    Set<PoliticCard> cards = new HashSet<PoliticCard>();

    /**
     *
     */
    Set<BusinessPermissionTile> tiles = new HashSet<BusinessPermissionTile>();

    /**
     * @param  coins number of coins the player have
     */
    public void Player(int coins, Set<Assistant> assistants) {
        try {
            this.coins.modifyCoins(coins);
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
    public void receiveAssistant(Assistant assistant) {
        if (assistant != null) {
            this.assistants.add(assistant);
        }
    }

    /**
     * @param
     */
    public void receiveCoins(int i) throws NotEnoughMoneyException {
        this.coins.modifyCoins(i);
    }

    /**
     * @param
     */
    public void receiveVictoryPoints(int i) {
        this.victoryPoints.addPoints(i);
    }

    /**
     * @param
     */
    public void receivePoliticCard(PoliticCard c) {
        if (c != null) {
            this.cards.add(c);
        }
    }

    /**
     * @param
     */
    public void receivePermissionTile(BusinessPermissionTile bpt) {
        if (bpt != null) {
            this.tiles.add(bpt);
        }
    }

}