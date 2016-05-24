package it.polimi.ingsw.cg26.server.model.cards;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class PoliticColorTest {

    private PoliticColor color;

    @Before
    public void setUp() throws Exception {
        color = new PoliticColor("colorname");
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail() throws Exception {
        new PoliticColor(null);
    }

    @Test
    public void testGetState() throws Exception {
        color.getState();
    }

    @Test
    public void testColorString() throws Exception {
        assertEquals(color.colorString(), "colorname");
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(color.equals(new PoliticColor("colorname")));
        assertTrue(color.equals(color));
        assertFalse(color.equals(null));
        assertFalse(color.equals(new PoliticColor("red")));
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(color.hashCode(), color.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        color.toString();
    }
}