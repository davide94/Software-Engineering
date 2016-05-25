package it.polimi.ingsw.cg26.common.state;

import it.polimi.ingsw.cg26.server.model.player.Assistant;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class CityColorStateTest {

    private CityColorState color;

    @Before
    public void setUp() throws Exception {
        color = new CityColorState("colorName");
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail() throws Exception {
        new CityColorState(null);
    }

    @Test
    public void testGetColor() throws Exception {
        assertEquals(color.getColor(), "colorName");
    }

    @Test
    public void testToString() throws Exception {
        color.toString();
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(color.hashCode(), new CityColorState("colorName").hashCode());
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(color.equals(color));
        assertTrue(color.equals(new CityColorState("colorName")));
        assertFalse(color.equals(new CityColorState("otherColorName")));
        assertFalse(color.equals(null));
        assertFalse(color.equals(new Assistant()));
    }
}