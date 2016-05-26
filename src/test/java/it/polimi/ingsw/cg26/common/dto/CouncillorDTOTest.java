package it.polimi.ingsw.cg26.common.dto;

import it.polimi.ingsw.cg26.server.model.player.Assistant;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class CouncillorDTOTest {

    private CouncillorDTO councillor;

    private PoliticColorDTO color;

    @Before
    public void setUp() throws Exception {
        color = new PoliticColorDTO("colorName");
        councillor = new CouncillorDTO(color);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail() throws Exception {
        new CouncillorDTO(null);
    }

    @Test
    public void testGetColor() throws Exception {
        assertEquals(councillor.getColor(), color);
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(councillor.equals(councillor));
        assertTrue(councillor.equals(new CouncillorDTO(color)));
        assertFalse(councillor.equals(new CouncillorDTO(new PoliticColorDTO("otherColorName"))));
        assertFalse(councillor.equals(null));
        assertFalse(councillor.equals(new Assistant()));
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(councillor.hashCode(), new CouncillorDTO(color).hashCode());
    }

    @Test
    public void testToString() throws Exception {
        councillor.toString();
    }
}