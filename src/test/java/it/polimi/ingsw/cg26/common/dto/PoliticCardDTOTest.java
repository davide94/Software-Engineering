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
    
    @Test
    public void testHashCodeEquals() throws Exception {
    	PoliticCardDTO card = new PoliticCardDTO(new PoliticColorDTO("colorname"), 0, "playerName");
    	
    	assertEquals(card.hashCode(), this.card.hashCode());
    	assertTrue(this.card.equals(card));
    	assertFalse(this.card.equals(null));
    }
    
    @Test
    public void testToString() throws Exception {
    	String string = "PoliticCard, " + color + ", price: 0, owner: playerName";
    	
    	assertEquals(string, card.toString());
    }
}