package it.polimi.ingsw.cg26.model.player;

/**
 * 
 */
public class VictoryPoints {

    /**
     * number of victory points
     */
    private int value;

    /**
     * Default constructor
     */
    public VictoryPoints() {
        this.value = 0;
    }

    /**
     * incrementally modifies the number of victory points
     * @param increment can only be positive
     */
    public void addPoints(int increment) {
        if (increment > 0) {
            this.value += increment;
        }
    }

    /**
     * @return the number of victory points
     */
    public int getValue() {
        return this.value;
    }

}