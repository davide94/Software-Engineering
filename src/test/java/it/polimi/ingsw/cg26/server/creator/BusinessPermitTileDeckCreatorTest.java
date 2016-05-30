package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.model.board.City;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.bonus.VictoryBonus;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTileDeck;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.server.model.cards.RewardTile;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.time.Instant;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 *
 */
public class BusinessPermitTileDeckCreatorTest {

    private Node root;
    private PoliticDeck politicDeck;
    private List<City> cities;

    @Before
    public void setUp() throws Exception {
        Instant before = Instant.now();

        DOMParserInterface parserInterface = new XMLAdapter();

        Document document = parserInterface.parse("src/test/resources/configTest.xml", "src/main/resources/schema.xsd");

        root = document.getFirstChild();

        politicDeck = new PoliticDeck(new LinkedList<>());

        cities = new LinkedList<>();
        for (List<City> l: CitiesCreator.createCities(root, politicDeck))
            cities.addAll(l);
    }

    @Test (expected = NullPointerException.class)
    public void testCreateDeckShouldThrowNullPointerException1() throws Exception {
        BusinessPermitTileDeckCreator.createDeck(null, cities, politicDeck);
    }

    @Test (expected = NullPointerException.class)
    public void testCreateDeckShouldThrowNullPointerException2() throws Exception {
        Node node = getNode(getNode(getNode(root, "regions"), "region"), "permissionTiles");
        BusinessPermitTileDeckCreator.createDeck(node, null, politicDeck);
    }

    @Test (expected = NullPointerException.class)
    public void testCreateDeckShouldThrowNullPointerException3() throws Exception {
        Node node = getNode(getNode(getNode(root, "regions"), "region"), "permissionTiles");
        BusinessPermitTileDeckCreator.createDeck(node, cities, null);
    }


    @Test
    public void testCreateDeck() throws Exception {
        /*
            <permissionTiles>
                <permissionTile>
                    <bonus  victory="7" />
                    <cities>
                        <city name="arkon" />
                    </cities>
                </permissionTile>
            </permissionTiles>
         */

        Node node = getNode(getNode(getNode(root, "regions"), "region"), "permissionTiles");

        BusinessPermissionTileDeck deck = BusinessPermitTileDeckCreator.createDeck(node, cities, politicDeck);

        Collection<BusinessPermissionTile> tiles = new LinkedList<>();

        LinkedList<Bonus> bonuses = new LinkedList<Bonus>();
        bonuses.add(new VictoryBonus(7));
        LinkedList<City> tileCities = new LinkedList<>();
        tileCities.add(cities.get(0));
        tiles.add(new BusinessPermissionTile(tileCities, new RewardTile(bonuses)));

        BusinessPermissionTileDeck deck1 = new BusinessPermissionTileDeck(tiles);
        assertEquals(deck, deck1);
    }

    private static LinkedList<Node> getNodes(Node root, String name) {
        LinkedList<Node> nodes = new LinkedList<>();
        NodeList l = root.getChildNodes();
        for (int i = 0; i < l.getLength(); i++) {
            Node node = l.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equalsIgnoreCase(name)) {
                nodes.add(node);
            }
        }
        return nodes;
    }

    private static Node getNode(Node root, String name) {
        return getNodes(root, name).poll();
    }
}