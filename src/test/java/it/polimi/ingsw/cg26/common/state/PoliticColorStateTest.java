package it.polimi.ingsw.cg26.common.state;

import it.polimi.ingsw.cg26.server.model.player.Assistant;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class PoliticColorStateTest {

    private PoliticColorState color;

    @Before
    public void setUp() throws Exception {
        color = new PoliticColorState("colorName");
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail1() throws Exception {
        new PoliticColorState(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorShouldFail2() throws Exception {
        new PoliticColorState("");
    }

    @Test
    public void testGetColor() throws Exception {
        assertEquals(color.getColor(), "colorName");
    }

    @Test
    public void testGetColoredColor() throws Exception {
        String colors[] = {"white", "black", "blue", "orange", "pink", "violet", "multicolor"};
        for (String c: colors) {
            color = new PoliticColorState(c);
            color.getColoredColor();
        }
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(color.equals(color));
        assertTrue(color.equals(new PoliticColorState("colorName")));
        assertFalse(color.equals(new PoliticColorState("otherColorName")));
        assertFalse(color.equals(null));
        assertFalse(color.equals(new Assistant()));
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(color.hashCode(), new PoliticColorState("colorName").hashCode());
    }

    @Test
    public void testToString() throws Exception {
        color.toString();
    }
}