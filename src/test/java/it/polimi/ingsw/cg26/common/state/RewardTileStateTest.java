package it.polimi.ingsw.cg26.common.state;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 *
 */
public class RewardTileStateTest {

    private RewardTileState tile;

    private LinkedList<BonusState> bonuses;

    @Before
    public void setUp() throws Exception {
        bonuses = new LinkedList<>();
        bonuses.add(new BonusState("bonusName", 7));
        tile = new RewardTileState(bonuses);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail() throws Exception {
        new RewardTileState(null);
    }

    @Test
    public void testGetBonuses() throws Exception {
        assertEquals(tile.getBonuses(), bonuses);
    }
}