package it.polimi.ingsw.cg26.common.dto;

import it.polimi.ingsw.cg26.server.model.player.Assistant;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class CityColorDTOTest {

    private CityColorDTO color;

    @Before
    public void setUp() throws Exception {
        color = new CityColorDTO("colorName");
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail1() throws Exception {
        new CityColorDTO(null);
    }


    @Test (expected = IllegalArgumentException.class)
    public void testConstructorShouldFail2() throws Exception {
        new CityColorDTO("");
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
        assertEquals(color.hashCode(), new CityColorDTO("colorName").hashCode());
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(color.equals(color));
        assertTrue(color.equals(new CityColorDTO("colorName")));
        assertFalse(color.equals(new CityColorDTO("otherColorName")));
        assertFalse(color.equals(null));
        assertFalse(color.equals(new Assistant()));
    }
}