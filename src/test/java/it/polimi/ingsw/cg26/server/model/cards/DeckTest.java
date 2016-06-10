package it.polimi.ingsw.cg26.server.model.cards;

import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 *
 */
public class DeckTest {
/*
    Deck<PoliticCard> deck;

    @Before
    public void setUp() throws Exception {
        deck = new Deck<>();
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail() throws Exception {
        new Deck<PoliticCard>(null);
    }

    @Test
    public void testConstructor() throws Exception {
        LinkedList<PoliticCard> cards = new LinkedList<>();
        PoliticCard card1 = new PoliticCard(new PoliticColor("red"));
        PoliticCard card2 = new PoliticCard(new PoliticColor("blue"));
        cards.add(card1);
        cards.add(card2);
        new Deck<PoliticCard>(cards);
    }

    @Test
    public void testHasNext() throws Exception {

    }

    @Test(expected = NoRemainingCardsException.class)
    public void testDrawShouldFail() throws Exception {
        deck.draw();
    }

    @Test(expected = NullPointerException.class)
    public void testAddShouldFail() throws Exception {
        deck.add(null);
    }

    @Test
    public void testAddAndDraw() throws Exception {
        PoliticCard card1 = new PoliticCard(new PoliticColor("red"));
        PoliticCard card2 = new PoliticCard(new PoliticColor("blue"));
        deck.add(card1);
        deck.add(card2);
        assertEquals(card1, deck.draw());
        assertEquals(card2, deck.draw());
    }

    @Test (expected = NullPointerException.class)
    public void testAddAllShouldFail() throws Exception {
        deck.addAll(null);
    }

    @Test
    public void testAddAll() throws Exception {
        LinkedList<PoliticCard> cards = new LinkedList<>();
        PoliticCard card1 = new PoliticCard(new PoliticColor("red"));
        PoliticCard card2 = new PoliticCard(new PoliticColor("blue"));
        cards.add(card1);
        cards.add(card2);
        deck.addAll(cards);
        assertEquals(card1, deck.draw());
        assertEquals(card2, deck.draw());
    }
    */
}