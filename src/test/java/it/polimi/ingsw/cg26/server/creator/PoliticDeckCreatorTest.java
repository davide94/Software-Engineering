package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 *
 */
public class PoliticDeckCreatorTest {

    private Node root;
    private PoliticDeck politicDeck;

    @Before
    public void setUp() throws Exception {
        DOMParserInterface parserInterface = new XMLAdapter();
        Document document = parserInterface.parse("maps/test.xml", "maps/schema.xsd");
        root = document.getDocumentElement();
    }

    @Test (expected = NullPointerException.class)
    public void testCreateDeckShouldThrowNullPointerException() throws Exception {
        PoliticDeckCreator.createDeck(null);
    }

    @Test
    public void testCreateDeck() throws Exception {
        /*
            <politic>
                <color name="multicolor" cards="13" />
                <color name="white" councillors="4" cards="13" />
                <color name="black" councillors="4" cards="13" />
                <color name="blue" councillors="4" cards="13" />
                <color name="orange" councillors="4" cards="13" />
                <color name="pink" councillors="4" cards="13" />
                <color name="violet" councillors="4" cards="13" />
            </politic>
         */

        List<PoliticCard> cards = new LinkedList<>();

        cards.addAll(Collections.nCopies(13, new PoliticCard(new PoliticColor("white"))));
        cards.addAll(Collections.nCopies(13, new PoliticCard(new PoliticColor("black"))));
        cards.addAll(Collections.nCopies(13, new PoliticCard(new PoliticColor("blue"))));
        cards.addAll(Collections.nCopies(13, new PoliticCard(new PoliticColor("orange"))));
        cards.addAll(Collections.nCopies(13, new PoliticCard(new PoliticColor("pink"))));
        cards.addAll(Collections.nCopies(13, new PoliticCard(new PoliticColor("violet"))));
        cards.addAll(Collections.nCopies(13, new PoliticCard(new PoliticColor("multicolor"))));

        PoliticDeck deck = PoliticDeckCreator.createDeck(root);

        Collection<PoliticCard> c = new LinkedList<>();
        while (true) {
            try {
                c.add(deck.draw());
            } catch (NoRemainingCardsException e) { break; }
        }
        System.out.println(c);
        System.out.println(cards);
        assertTrue(c.containsAll(cards) && cards.containsAll(c));
    }
}