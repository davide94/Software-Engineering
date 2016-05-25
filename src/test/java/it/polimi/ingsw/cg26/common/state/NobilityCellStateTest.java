package it.polimi.ingsw.cg26.common.state;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 *
 */
public class NobilityCellStateTest {

    private NobilityCellState cell;

    private LinkedList<BonusState> bonuses;

    @Before
    public void setUp() throws Exception {
        bonuses = new LinkedList<>();
        bonuses.add(new BonusState("bonusName", 4));
        cell = new NobilityCellState(8, bonuses);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail1() throws Exception {
        new NobilityCellState(8, null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorShouldFail2() throws Exception {
        new NobilityCellState(-2, bonuses);
    }

    @Test
    public void testGetIndex() throws Exception {
        assertEquals(cell.getIndex(), 8);
    }

    @Test
    public void testGetBonuses() throws Exception {
        assertEquals(cell.getBonuses(), bonuses);
    }

    @Test
    public void testToString() throws Exception {
        cell.toString();
    }
}