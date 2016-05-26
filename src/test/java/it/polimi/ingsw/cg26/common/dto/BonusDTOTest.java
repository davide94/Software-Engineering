package it.polimi.ingsw.cg26.common.dto;

import it.polimi.ingsw.cg26.server.model.player.Assistant;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class BonusDTOTest {

    private BonusDTO bonus;

    @Before
    public void setUp() throws Exception {
        bonus = new BonusDTO("bonusname", 5);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail1() throws Exception {
        new BonusDTO(null, 3);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorShouldFail2() throws Exception {
        new BonusDTO("bonusname", -2);
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals(bonus.getKind(), "bonusname");
    }

    @Test
    public void testGetMultiplicity() throws Exception {
        assertEquals(bonus.getMultiplicity(), 5);
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(bonus.equals(bonus));
        assertTrue(bonus.equals(new BonusDTO("bonusname", 5)));
        assertFalse(bonus.equals(null));
        assertFalse(bonus.equals(new BonusDTO("otherbonusname", 5)));
        assertFalse(bonus.equals(new BonusDTO("bonusname", 4)));
        assertFalse(bonus.equals(new Assistant()));
    }

    @Test
    public void testHashCode() throws Exception {
        BonusDTO b = new BonusDTO("bonusname", 5);
        assertEquals(bonus.hashCode(), b.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        bonus.toString();
    }
}