package it.polimi.ingsw.cg26.common.state;

import it.polimi.ingsw.cg26.server.model.player.Assistant;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class BonusStateTest {

    private BonusState bonus;

    @Before
    public void setUp() throws Exception {
        bonus = new BonusState("bonusname", 5);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail1() throws Exception {
        new BonusState(null, 3);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorShouldFail2() throws Exception {
        new BonusState("bonusname", -2);
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals(bonus.getName(), "bonusname");
    }

    @Test
    public void testGetMultiplicity() throws Exception {
        assertEquals(bonus.getMultiplicity(), 5);
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(bonus.equals(bonus));
        assertTrue(bonus.equals(new BonusState("bonusname", 5)));
        assertFalse(bonus.equals(null));
        assertFalse(bonus.equals(new BonusState("otherbonusname", 5)));
        assertFalse(bonus.equals(new BonusState("bonusname", 4)));
        assertFalse(bonus.equals(new Assistant()));
    }

    @Test
    public void testHashCode() throws Exception {
        BonusState b = new BonusState("bonusname", 5);
        assertEquals(bonus.hashCode(), b.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        bonus.toString();
    }
}