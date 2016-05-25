package it.polimi.ingsw.cg26.common.state;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class EmporiumStateTest {

    private EmporiumState emporium;

    @Before
    public void setUp() throws Exception {
        emporium = new EmporiumState("aPlayerName");
    }

    @Test (expected = NullPointerException.class)
    public void testConstrunctorShouldFail1() throws Exception {
        new EmporiumState(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstrunctorShouldFai2l() throws Exception {
        new EmporiumState("");
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