package it.polimi.ingsw.cg26.common.state;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 *
 */
public class BusinessPermissionTileDeckStateTest {

    private LinkedList<BusinessPermissionTileState> cards;

    private BusinessPermissionTileDeckState deck;

    @Before
    public void setUp() throws Exception {
        LinkedList<String> cities = new LinkedList<>();
        cities.add("city1Name");
        cities.add("city2Name");
        LinkedList<BonusState> bonuses = new LinkedList<>();
        bonuses.add(new BonusState("bonusName", 6));
        cards = new LinkedList<>();
        cards.add(new BusinessPermissionTileState(cities, bonuses, 0, "playerName"));

        deck = new BusinessPermissionTileDeckState(cards);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail() throws Exception {
        new BusinessPermissionTileDeckState(null);
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