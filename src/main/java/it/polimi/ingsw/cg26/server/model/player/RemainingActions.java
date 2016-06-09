package it.polimi.ingsw.cg26.server.model.player;

import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;

/**
 * The RemainingActions class models and manages the remaining commands in a player's turn
 */
public abstract class RemainingActions {

    /**
     * Number of remaining commands in this turn
     */
    private int remaining;

    /**
     * Constructs an RemainingActions class with 0 commands remaining
     */
    public RemainingActions() {
        this.remaining = 0;
    }

    /**
     * Checks if it is possible to perform an action
     * @return true if it is possible, false if not
     */
    public boolean canPerform() {
        return this.remaining > 0;
    }

    /**
     * Returns the number of actions that can be performed
     * @return the number of actions that can be performed
     */
    public int getValue() {
        return remaining;
    }

    /**
     * Performs an action: the number of remaining commands is decremented by one
     * @throws NoRemainingActionsException if there are no remaining commands
     */
    public void perform() throws NoRemainingActionsException {
        if (this.remaining == 0)
            throw new NoRemainingActionsException();
        this.remaining --;
    }

    /**
     * Sets the number of remaining commands in this turn
     * @param remainingActions is the number to be set as number of remaining commands
     * @throws IllegalArgumentException if the parameter is negative
     */
    public void setRemaining(int remainingActions) {
        if (remainingActions < 0)
            throw new IllegalArgumentException();
        this.remaining = remainingActions;
    }

    /**
     * Increments the number of commands in this turn
     * @param increment is the number of commands to be added for this turn
     * @throws IllegalArgumentException if the increment is negative
     */
    public synchronized void addActions(int increment) {
        if (increment < 0)
            throw new IllegalArgumentException();
        this.remaining += increment;
    }

}