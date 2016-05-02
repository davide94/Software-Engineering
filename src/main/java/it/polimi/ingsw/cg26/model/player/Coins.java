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
     * incrementally modifies the number of coins
     * @param increment if positive is the increment but can also be a decrement if negative
     */
    public void modifyCoins(int increment) throws NotEnoughMoneyException {
        if (this.value + increment < 0) {
            throw new NotEnoughMoneyException();
        }
        this.value += increment;
    }

    /**
     * @return the number of coins
     */
    public int getValue() {
        return this.value;
    }

}