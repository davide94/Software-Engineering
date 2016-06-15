package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.model.board.Councillor;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 *
 */
public class CouncillorsCreatorTest {

    private Node root;
    private PoliticDeck politicDeck;

    @Before
    public void setUp() throws Exception {
        DOMParserInterface parserInterface = new XMLAdapter();
        Document document = parserInterface.parse("maps/test.xml", "maps/schema.xsd");
        root = document.getDocumentElement();

        politicDeck = new PoliticDeck(new LinkedList<>());
    }

    @Test (expected = NullPointerException.class)
    public void testCreateCouncillorsShouldThrowNullPointerException() throws Exception {
        CouncillorsCreator.createCouncillors(null);
    }

    @Test
    public void testCreateCouncillors() throws Exception {
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

        List<Councillor> councillors = CouncillorsCreator.createCouncillors(root);

        List<Councillor> councillorsTest = new LinkedList<>();

        councillorsTest.addAll(Collections.nCopies(4, Councillor.createCouncillor(new PoliticColor("white"))));
        councillorsTest.addAll(Collections.nCopies(4, Councillor.createCouncillor(new PoliticColor("black"))));
        councillorsTest.addAll(Collections.nCopies(4, Councillor.createCouncillor(new PoliticColor("blue"))));
        councillorsTest.addAll(Collections.nCopies(4, Councillor.createCouncillor(new PoliticColor("orange"))));
        councillorsTest.addAll(Collections.nCopies(4, Councillor.createCouncillor(new PoliticColor("pink"))));
        councillorsTest.addAll(Collections.nCopies(4, Councillor.createCouncillor(new PoliticColor("violet"))));

        assertTrue(councillors.containsAll(councillorsTest) && councillorsTest.containsAll(councillors));
    }
}