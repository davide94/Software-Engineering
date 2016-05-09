package it.polimi.ingsw.cg26.model.player;

import it.polimi.ingsw.cg26.exceptions.NotEnoughMoneyException;

/**
 * The Coins class models and manages the coins owned by a player
 */
public class Coins {

    /**
     * Number of coins
     */
    private int value;

    /**
     * Constructs a Coins class with 0 coins remaining
     */
    public Coins() {
        this.value = 0;
    }

    /**
     * Increments the number of coins owned by the player
     * @param increment is the number of coins to be added
     * @throws IllegalArgumentException if the parameter is negative
     */
    public synchronized void addCoins(int increment) {
        if (increment < 0) {
            throw new IllegalArgumentException();
        }
        this.value += increment;
    }

    /**
     * Decrements the number of coins owned by the player
     * @param decrement is the number of coins to be removed
     * @throws IllegalArgumentException if the parameter is negative
     * @throws NotEnoughMoneyException if the decrement is greater than the number of coins owned by the player
     */
    public synchronized void removeCoins(int decrement) {
        if (decrement < 0) {
            throw new IllegalArgumentException();
        }
        if (this.value < decrement) {
            throw new NotEnoughMoneyException();
        }
        this.value -= decrement;
    }

    /**
     * Returns the number of coins owned by the player
     * @return the number of coins owned by the player
     */
    public int getValue() {
        return this.value;
    }

}