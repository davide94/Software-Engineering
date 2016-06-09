package it.polimi.ingsw.cg26.server.model.cards;

import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 *
 */
public class PoliticDeckTest {

    private PoliticDeck deck;

    @Before
    public void setUp() throws Exception {
        deck = new PoliticDeck(new LinkedList<>());
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail() throws Exception {
        new PoliticDeck(null);
    }

    @Test
    public void testGetState() throws Exception {
        deck.getState();
    }

    @Test
    public void testDraw() throws Exception {
        PoliticCard card = new PoliticCard(new PoliticColor("colorname"));
        deck.add(card);
        assertEquals(card, deck.draw());
    }

    @Test
    public void testDraw2() throws Exception {
        PoliticCard card = new PoliticCard(new PoliticColor("colorname"));
        deck.add(card);
        deck.discard(deck.draw());
        assertEquals(card, deck.draw());
    }

    @Test (expected = NullPointerException.class)
    public void testDiscardShouldFail() throws Exception {
        deck.discard(null);
    }

    @Test (expected = NoRemainingCardsException.class)
    public void testDrawShouldFail() throws Exception {
        deck.draw();
    }

    @Test (expected = NullPointerException.class)
    public void testDiscardAllShouldFail() throws Exception {
        deck.discardAll(null);
    }

    @Test
    public void testDiscardAll() throws Exception {
        LinkedList<PoliticCard> cards = new LinkedList<>();
        cards.add(new PoliticCard(new PoliticColor("colorname")));
        deck.discardAll(cards);
        deck.draw();
    }
}