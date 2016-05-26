package it.polimi.ingsw.cg26.common.dto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class PoliticCardDTOTest {

    private PoliticCardDTO card;

    private PoliticColorDTO color;

    @Before
    public void setUp() throws Exception {
        color = new PoliticColorDTO("colorname");
        card = new PoliticCardDTO(color, 0, "playerName");
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail1() throws Exception {
        new PoliticCardDTO(null, 0, "playerName");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorShouldFail2() throws Exception {
        new PoliticCardDTO(null, -1, "playerName");
    }

    @Test
    public void testGetColor() throws Exception {
        assertEquals(card.getColor(), color);
    }
}