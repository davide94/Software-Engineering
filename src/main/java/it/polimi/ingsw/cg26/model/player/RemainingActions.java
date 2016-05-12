package it.polimi.ingsw.cg26.model.player;

import it.polimi.ingsw.cg26.exceptions.NoRemainingActionsException;

/**
 * The RemainingActions class models and manages the remaining actions in a player's turn
 */
public abstract class RemainingActions {

    /**
     * Number of remaining actions in this turn
     */
    private int remaining;

    /**
     * Constructs an RemainingActions class with 0 actions remaining
     */
    public RemainingActions() {
        this.remaining = 1;
    }

    /**
     *
     * @return
     */
    public boolean canPerform() {
        return this.remaining > 0;
    }

    /**
     * Performs an action: the number of remaining actions is decremented by one
     * @throws NoRemainingActionsException if there are no remaining actions
     */
    public void perform() {
        if (this.remaining == 0)
            throw new NoRemainingActionsException();
        this.remaining --;
    }

    /**
     * Sets the number of remaining actions in this turn
     * @param remainingActions is the number to be set as number of remaining actions
     * @throws IllegalArgumentException if the parameter is negative
     */
    public void setRemaining(int remainingActions) {
        if (remaining < 0)
            throw new IllegalArgumentException();
        this.remaining = remainingActions;
    }

    /**
     * Increments the number of actions in this turn
     * @param increment is the number of actions to be added for this turn
     * @throws IllegalArgumentException if the increment is negative
     */
    public synchronized void addActions(int increment) {
        if (increment < 0)
            throw new IllegalArgumentException();
        this.remaining += increment;
    }

}