package it.polimi.ingsw.cg26.server.model.player;

/**
 * The VictoryPoints class models and manages the victory points owned by a player
 */
public class VictoryPoints {

    /**
     * Number of victory points
     */
    private int value;

    /**
     * Constructs a VictoryPoints class with 0 points
     */
    public VictoryPoints() {
        this.value = 0;
    }

    /**
     * Increments the number of victory points
     * @param increment is the number of victory points to be added
     * @throws IllegalArgumentException if the parameter is negative
     */
    public synchronized void addPoints(int increment) {
        if (increment < 0)
            throw new IllegalArgumentException();
        this.value += increment;
    }

    /**
     * Returns the number of victory points owned by the player
     * @return the number of victory points owned by the player
     */
    public int getValue() {
        return this.value;
    }

}