package it.polimi.ingsw.cg26.common.dto;

import it.polimi.ingsw.cg26.server.model.player.Assistant;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class PoliticColorDTOTest {

    private PoliticColorDTO color;

    @Before
    public void setUp() throws Exception {
        color = new PoliticColorDTO("colorName");
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail1() throws Exception {
        new PoliticColorDTO(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorShouldFail2() throws Exception {
        new PoliticColorDTO("");
    }

    @Test
    public void testGetColor() throws Exception {
        assertEquals(color.getColor(), "colorName");
    }

    @Test
    public void testGetColoredColor() throws Exception {
        String colors[] = {"white", "black", "blue", "orange", "pink", "violet", "multicolor"};
        for (String c: colors) {
            color = new PoliticColorDTO(c);
            color.getColoredColor();
        }
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(color.equals(color));
        assertTrue(color.equals(new PoliticColorDTO("colorName")));
        assertFalse(color.equals(new PoliticColorDTO("otherColorName")));
        assertFalse(color.equals(null));
        assertFalse(color.equals(new Assistant()));
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(color.hashCode(), new PoliticColorDTO("colorName").hashCode());
    }

    @Test
    public void testToString() throws Exception {
        color.toString();
    }
}