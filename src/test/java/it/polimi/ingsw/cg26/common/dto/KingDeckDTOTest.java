package it.polimi.ingsw.cg26.common.dto;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 *
 */
public class KingDeckDTOTest {

    private KingDeckDTO deck;

    private LinkedList<BonusDTO> bonuses;

    private LinkedList<RewardTileDTO> cards;

    @Before
    public void setUp() throws Exception {
        bonuses = new LinkedList<>();
        bonuses.add(new BonusDTO("bonusName", 3));
        cards = new LinkedList<>();
        cards.add(new RewardTileDTO(bonuses));
        deck = new KingDeckDTO(cards);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail() throws Exception {
        new KingDeckDTO(null);
    }

    @Test
    public void testGetCards() throws Exception {
        assertEquals(deck.getTiles(), cards);
    }

    @Test
    public void testToString() throws Exception {
        deck.toString();
    }
}