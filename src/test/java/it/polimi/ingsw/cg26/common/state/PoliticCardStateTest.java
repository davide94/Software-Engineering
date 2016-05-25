package it.polimi.ingsw.cg26.common.state;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class PoliticCardStateTest {

    private PoliticCardState card;

    private PoliticColorState color;

    @Before
    public void setUp() throws Exception {
        color = new PoliticColorState("colorname");
        card = new PoliticCardState(color, 0, "playerName");
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail1() throws Exception {
        new PoliticCardState(null, 0, "playerName");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorShouldFail2() throws Exception {
        new PoliticCardState(null, -1, "playerName");
    }

    @Test
    public void testGetColor() throws Exception {
        assertEquals(card.getColor(), color);
    }
}