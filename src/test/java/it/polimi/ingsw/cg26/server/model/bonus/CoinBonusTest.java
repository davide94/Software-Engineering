package it.polimi.ingsw.cg26.server.model.bonus;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class CoinBonusTest {

    @Test
    public void thestCreate() {
        assertNotNull("Not possible", new CoinBonus(100));
    }

    @Test
    public void testApply() throws Exception {

    }
}