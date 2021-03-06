package it.polimi.ingsw.cg26.common.dto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 *
 */
public class KingDTOTest {

    private KingDTO king;

    @Before
    public void setUp() throws Exception {
        king = new KingDTO("currentCityName");
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail1() throws Exception {
        new KingDTO(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorShouldFail2() throws Exception {
        new KingDTO("");
    }

    @Test
    public void testGetCurrentCity() throws Exception {
        assertEquals(king.getCurrentCity(), "currentCityName");
    }

    @Test (expected = NullPointerException.class)
    public void testSetCurrentCityShouldFail1() throws Exception {
        king.setCurrentCity(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetCurrentCityShouldFail2() throws Exception {
        king.setCurrentCity("");
    }

    @Test
    public void testSetCurrentCity() throws Exception {
        king.setCurrentCity("aCityName");
        assertEquals(king.getCurrentCity(), "aCityName");
    }

    @Test
    public void testToString() throws Exception {
        king.toString();
    }
    
    @Test
    public void testHashCodeEquals() throws Exception {
    	KingDTO king = new KingDTO("currentCityName");
    	KingDTO differentKing = new KingDTO("Milano");
    	
    	assertEquals(king.hashCode(), this.king.hashCode());
    	assertTrue(this.king.equals(king));
    	assertFalse(this.king.equals(null));
    	assertFalse(this.king.equals(new KingDeckDTO(new ArrayList<>())));
    	assertFalse(this.king.equals(differentKing));
    }
}