package it.polimi.ingsw.cg26.model.player;

import it.polimi.ingsw.cg26.exceptions.NoRemainingActionsException;

/**
 * 
 */
public abstract class Actions {

    /**
     * number of remaining actions in this turn
     */
    private int remaining;

    /**
     * Default constructor
     */
    public Actions() {
        this.remaining = 0;
    }

    /**
     * performing an action, the number of remaining actions should be decremented
     * @throws NoRemainingActionsException when a player try to perform an action but he do not have remaining actions
     */
    public void perform() throws NoRemainingActionsException {
        if (this.remaining == 0) {
            throw new NoRemainingActionsException();
        }
        this.remaining --;
    }

    /**
     * @param remaining is the number to be set as number of remaining actions
     */
    public void setRemaining(int remaining) {
        if (remaining >= 0) {
            this.remaining = 0;
        }
    }

}