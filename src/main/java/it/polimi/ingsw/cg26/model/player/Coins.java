package it.polimi.ingsw.cg26.model.player;

import it.polimi.ingsw.cg26.exceptions.NotEnoughMoneyException;

/**
 * 
 */
public class Coins {

    /**
     * number of coins
     */
    private int value;

    /**
     * Default constructor
     */
    public Coins() {
        this.value = 0;
    }

    /**
     *
     * @param increment
     */
    public synchronized void addCoins(int increment) {
        if (increment < 0) {
            throw new IllegalArgumentException();
        }
        this.value += increment;
    }

    /**
     *
     * @param decrement
     */
    public synchronized void removeCoins(int decrement) {
        if (decrement < 0) {
            throw new IllegalArgumentException();
        }
        if (this.value - decrement < 0) {
            throw new NotEnoughMoneyException();
        }
        this.value -= decrement;
    }

    /**
     * @return the number of coins
     */
    public int getValue() {
        return this.value;
    }

}