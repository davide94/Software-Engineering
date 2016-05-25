package it.polimi.ingsw.cg26.common.state;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 *
 */
public class KingDeckStateTest {

    private KingDeckState deck;

    private LinkedList<BonusState> bonuses;

    private LinkedList<RewardTileState> cards;

    @Before
    public void setUp() throws Exception {
        bonuses = new LinkedList<>();
        bonuses.add(new BonusState("bonusName", 3));
        cards = new LinkedList<>();
        cards.add(new RewardTileState(bonuses));
        deck = new KingDeckState(cards);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail() throws Exception {
        new KingDeckState(null);
    }

    @Test
    public void testGetCards() throws Exception {
        assertEquals(deck.getCards(), cards);
    }

    @Test
    public void testToString() throws Exception {
        deck.toString();
    }
}