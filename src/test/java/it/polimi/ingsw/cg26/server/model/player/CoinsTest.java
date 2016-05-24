package it.polimi.ingsw.cg26.server.model.player;

import it.polimi.ingsw.cg26.server.exceptions.NotEnoughMoneyException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class CoinsTest {

    private Coins coins;

    @Before
    public void setUp() throws Exception {
        coins = new Coins();
    }

    @Test
    public void testAddCoinsAndGetValueShouldWork() throws Exception {
        coins.addCoins(5);
        assertEquals(coins.getValue(), 5);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddCoinsShouldThrowIllegalArgumentException() throws Exception {
        coins.addCoins(-1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testRemoveCoinsShouldThrowIllegalArgumentException() throws Exception {
        coins.removeCoins(-1);
    }

    @Test (expected = NotEnoughMoneyException.class)
    public void testRemoveCoinsShouldThrowNotEnoughMoneyException() throws Exception {
        coins.removeCoins(1);
    }

    @Test
    public void testRemoveCoinsShouldWork() throws Exception {
        coins.addCoins(5);
        coins.removeCoins(2);
        assertEquals(coins.getValue(), 3);
    }
}