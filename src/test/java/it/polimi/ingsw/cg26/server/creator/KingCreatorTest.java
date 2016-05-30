package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.model.board.City;
import it.polimi.ingsw.cg26.server.model.board.CityColor;
import it.polimi.ingsw.cg26.server.model.board.King;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.server.model.cards.RewardTile;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.time.Instant;
import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 *
 */
public class KingCreatorTest {

    private Node root;
    private PoliticDeck politicDeck;

    @Before
    public void setUp() throws Exception {
        Instant before = Instant.now();

        DOMParserInterface parserInterface = new XMLAdapter();

        Document document = parserInterface.parse("src/test/resources/configTest.xml", "src/main/resources/schema.xsd");

        root = document.getFirstChild();

        politicDeck = new PoliticDeck(new LinkedList<>());
    }

    @Test
    public void testCreateKing() throws Exception {

        King king = KingCreator.createKing(root, CitiesCreator.createCities(root, politicDeck));

        King kingTest = King.createKing(City.createCity("arkon", CityColor.createCityColor("blue"), new RewardTile(new LinkedList<>())));

        assertEquals(king, kingTest);
    }
}