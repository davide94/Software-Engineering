package it.polimi.ingsw.cg26.common.state;

import it.polimi.ingsw.cg26.server.model.player.Assistant;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class CouncillorStateTest {

    private CouncillorState councillor;

    private PoliticColorState color;

    @Before
    public void setUp() throws Exception {
        color = new PoliticColorState("colorName");
        councillor = new CouncillorState(color);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail() throws Exception {
        new CouncillorState(null);
    }

    @Test
    public void testGetColor() throws Exception {
        assertEquals(councillor.getColor(), color);
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(councillor.equals(councillor));
        assertTrue(councillor.equals(new CouncillorState(color)));
        assertFalse(councillor.equals(new CouncillorState(new PoliticColorState("otherColorName"))));
        assertFalse(councillor.equals(null));
        assertFalse(councillor.equals(new Assistant()));
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(councillor.hashCode(), new CouncillorState(color).hashCode());
    }

    @Test
    public void testToString() throws Exception {
        councillor.toString();
    }
}