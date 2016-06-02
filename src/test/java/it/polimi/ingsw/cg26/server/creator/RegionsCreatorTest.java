package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.model.board.Balcony;
import it.polimi.ingsw.cg26.server.model.board.City;
import it.polimi.ingsw.cg26.server.model.board.Councillor;
import it.polimi.ingsw.cg26.server.model.board.Region;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.bonus.EmptyBonus;
import it.polimi.ingsw.cg26.server.model.bonus.VictoryBonus;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTileDeck;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 *
 */
public class RegionsCreatorTest {

    private Node root;
    private List<List<City>> cities;
    private PoliticDeck politicDeck;
    private List<Councillor> councillors;
    private Region regionTest;
    private Region region;

    @Before
    public void setUp() throws Exception {
        Instant before = Instant.now();

        DOMParserInterface parserInterface = new XMLAdapter();

        Document document = parserInterface.parse("src/test/resources/configTest.xml", "src/main/resources/schema.xsd");

        root = document.getFirstChild();

        politicDeck = PoliticDeckCreator.createDeck(root);

        councillors = CouncillorsCreator.createCouncillors(root);

        List<City> cities = CitiesCreator.createCities(root, politicDeck).get(0);
        BusinessPermissionTileDeck deck = BusinessPermitTileDeckCreator.createDeck(getNode(getNode(getNode(root, "regions"), "region"), "permissionTiles"), cities, politicDeck);
        Balcony balcony = Balcony.createBalcony(4);
        Bonus bonus = new VictoryBonus(new EmptyBonus(), 5);

        regionTest = RegionsCreator.createRegions(root, CitiesCreator.createCities(root, politicDeck),politicDeck,councillors).get(0);
        region = Region.createRegion("coast", cities, deck, balcony, bonus);
    }

    @Test (expected = NullPointerException.class)
    public void testCreateRegionsShouldThrowNullPointerException1() throws Exception {
        RegionsCreator.createRegions(null, cities, politicDeck, councillors);
    }

    @Test (expected = NullPointerException.class)
    public void testCreateRegionsShouldThrowNullPointerException2() throws Exception {
        RegionsCreator.createRegions(root, null, politicDeck, councillors);
    }

    @Test (expected = NullPointerException.class)
    public void testCreateRegionsShouldThrowNullPointerException3() throws Exception {
        RegionsCreator.createRegions(root, cities, null, councillors);
    }

    @Test (expected = NullPointerException.class)
    public void testCreateRegionsShouldThrowNullPointerException4() throws Exception {
        RegionsCreator.createRegions(root, cities, politicDeck, null);
    }

    @Test
    public void testCreateRegionsShouldBeEquals() throws Exception {

        assertEquals(region, regionTest);
    }

    @Test
    public void testCreateRegionsShouldContainSameCities() throws Exception {

        assertTrue(region.getCities().containsAll(regionTest.getCities()) && regionTest.getCities().containsAll(region.getCities()));
    }

    @Test
    public void testCreateRegionsShouldHaveSameBPTDeck() throws Exception {

        assertEquals(region.getBPTDeck(), regionTest.getBPTDeck());
    }

    @Test
    public void testCreateRegionsShouldHaveSameBonus() throws Exception {

        assertEquals(region.getRegionBonus(), regionTest.getRegionBonus());
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