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
        emporium = EmporiumDTO.createEmporium("aPlayerName");
    }

    @Test (expected = NullPointerException.class)
    public void testConstrunctorShouldFail1() throws Exception {
        EmporiumDTO.createEmporium(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstrunctorShouldFai2l() throws Exception {
        EmporiumDTO.createEmporium("");
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