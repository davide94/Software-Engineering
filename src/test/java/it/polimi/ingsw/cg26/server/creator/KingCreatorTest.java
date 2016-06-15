package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.model.board.City;
import it.polimi.ingsw.cg26.server.model.board.CityColor;
import it.polimi.ingsw.cg26.server.model.board.King;
import it.polimi.ingsw.cg26.server.model.bonus.EmptyBonus;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class KingCreatorTest {

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
    public void testCreateKingShouldThrowNullPointerException1() throws Exception {
        KingCreator.createKing(null, CitiesCreator.createCities(root, politicDeck));
    }

    @Test (expected = NullPointerException.class)
    public void testCreateKingShouldThrowNullPointerException2() throws Exception {
        KingCreator.createKing(root, null);
    }

    @Test
    public void testCreateKing() throws Exception {

        King king = KingCreator.createKing(root, CitiesCreator.createCities(root, politicDeck));

        King kingTest = King.createKing(City.createCity("arkon", CityColor.createCityColor("blue"), new EmptyBonus()));

        assertEquals(king, kingTest);
    }
}