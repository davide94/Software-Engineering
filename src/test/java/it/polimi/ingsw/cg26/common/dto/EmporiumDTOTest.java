package it.polimi.ingsw.cg26.common.dto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class EmporiumDTOTest {

    private EmporiumDTO emporium;

    @Before
    public void setUp() throws Exception {
        emporium = new EmporiumDTO("aPlayerName");
    }

    @Test (expected = NullPointerException.class)
    public void testConstrunctorShouldFail1() throws Exception {
        new EmporiumDTO(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstrunctorShouldFai2l() throws Exception {
        new EmporiumDTO("");
    }

    @Test
    public void testGetPlayer() throws Exception {
        assertEquals(emporium.getPlayer(), "aPlayerName");
    }

    @Test
    public void testToString() throws Exception {
        emporium.toString();
    }
}