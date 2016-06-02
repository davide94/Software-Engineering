package it.polimi.ingsw.cg26.common.dto;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.bonusdto.EmptyBonusDTO;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 *
 */
public class BusinessPermissionTileDeckDTOTest {

    private LinkedList<BusinessPermissionTileDTO> cards;

    private BusinessPermissionTileDeckDTO deck;

    @Before
    public void setUp() throws Exception {
        LinkedList<String> cities = new LinkedList<>();
        cities.add("city1Name");
        cities.add("city2Name");
        cards = new LinkedList<>();
        cards.add(new BusinessPermissionTileDTO(cities, new EmptyBonusDTO(), 0, "playerName"));

        deck = new BusinessPermissionTileDeckDTO(cards);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail() throws Exception {
        new BusinessPermissionTileDeckDTO(null);
    }

    @Test
    public void testGetOpenCards() throws Exception {
        assertEquals(deck.getOpenCards(), cards);
    }

    @Test
    public void testToString() throws Exception {
        deck.toString();
    }
}