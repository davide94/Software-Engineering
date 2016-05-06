package it.polimi.ingsw.cg26.model.creator;

import it.polimi.ingsw.cg26.exceptions.BadInputFileException;
import it.polimi.ingsw.cg26.model.GameLogic;
import it.polimi.ingsw.cg26.model.board.*;
import it.polimi.ingsw.cg26.model.bonus.*;
import it.polimi.ingsw.cg26.model.cards.*;
import it.polimi.ingsw.cg26.model.market.Market;
import it.polimi.ingsw.cg26.model.player.Assistant;
import it.polimi.ingsw.cg26.model.player.Player;
import javafx.util.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

/**
 * 
 */
public class ModelCreator {

    /**
     * Default constructor
     */
    public ModelCreator() {
    }

    /**
     * @param file is the path+name of the configuration file
     */
    public void newGame(String file, int playersNumber) throws IOException {

        try {
            DOMParserInterface parserInterface = new XMLAdapter();

            if ( !parserInterface.validate(file, "src/main/schema.xml"))
                throw new BadInputFileException();

            Document document = parserInterface.parse(file);

            Node game = document.getFirstChild();

            Node cityBonusesRoot = getNode("citybonuses", game);
            Node nobilityTrackRoot = getNode("nobilitytrack", game);
            Node politicRoot = getNode("politic", game);
            Node coastRoot = getNode("coast", game);
            Node hillsRoot = getNode("hills", game);
            Node mountainsRoot = getNode("mountains", game);

            // create cities
            LinkedList<LinkedList<Bonus>> cityBonuses = createBonuses(cityBonusesRoot);

            LinkedList<City> coastCities = createCities(getNode("cities", coastRoot), cityBonuses);
            LinkedList<City> hillsCities = createCities(getNode("cities", hillsRoot), cityBonuses);
            LinkedList<City> mountainsCities = createCities(getNode("cities", mountainsRoot), cityBonuses);
            // TODO create links between cities;

            // create king
            King king = new King();

            // create councillors
            // create politicCards
            Pair<LinkedList<Councillor>, LinkedList<PoliticCard>> politic = createPolitic(politicRoot);
            LinkedList<Councillor> councillors = politic.getKey();

            // create PoliticCardsDeck
            PoliticDeck politicDeck = new PoliticDeck(politic.getValue());

            // create balcony
            Balcony kingsBalcony = new Balcony();
            Balcony coastBalcony = new Balcony();
            Balcony hillsBalcony = new Balcony();
            Balcony mountainsBalcony = new Balcony();


            Random random = new Random();

            for (int i = 0; i < 4; i++) {
                kingsBalcony.elect(councillors.remove(random.nextInt(councillors.size())));
                coastBalcony.elect(councillors.remove(random.nextInt(councillors.size())));
                hillsBalcony.elect(councillors.remove(random.nextInt(councillors.size())));
                mountainsBalcony.elect(councillors.remove(random.nextInt(councillors.size())));
            }

            // create BPT
            // create BPTDeck
            LinkedList<BusinessPermissionTile> coastTiles = createTiles(getNode("permissionTiles", coastRoot), coastCities);
            LinkedList<BusinessPermissionTile> hillsTiles = createTiles(getNode("permissionTiles", hillsRoot), hillsCities);
            LinkedList<BusinessPermissionTile> mountainsTiles = createTiles(getNode("permissionTiles", mountainsRoot), mountainsCities);

            BusinessPermissionTileDeck coastTilesDeck = new BusinessPermissionTileDeck(coastTiles);
            BusinessPermissionTileDeck hillsTilesDeck = new BusinessPermissionTileDeck(hillsTiles);
            BusinessPermissionTileDeck mountainsTilesDeck = new BusinessPermissionTileDeck(mountainsTiles);

            // TODO create regional bonus
            // create regions
            Region coast = new Region(coastCities, coastTilesDeck, coastBalcony, null);
            Region hills = new Region(hillsCities, hillsTilesDeck, hillsBalcony, null);
            Region mountains = new Region(mountainsCities, mountainsTilesDeck, mountainsBalcony, null);

            LinkedList<Region> regions = new LinkedList<Region>();
            regions.add(coast);
            regions.add(hills);
            regions.add(mountains);

            // create nobility track
            NobilityTrack nobilityTrack = createNobilityTrack(nobilityTrackRoot);

            // create gameBoard
            GameBoard gameBoard = new GameBoard(politicDeck, councillors, kingsBalcony, regions, nobilityTrack, king);

            // create market
            Market market = new Market();

            // create GameLogic
            GameLogic gameLogic = new GameLogic(gameBoard);

            // create players
            for (int i = 0 ; i < playersNumber; i++) {
                LinkedList<Assistant> assistants = new LinkedList<Assistant>();
                for (int j = 0; j <= i; j++) {
                    assistants.add(new Assistant());
                }
                Player player = new Player(gameLogic, i + 10, assistants);
                gameLogic.addPlayer(player);
            }


        } catch (FactoryConfigurationError e) {
            System.out.printf("unable to get a document builder factory");
        } catch (ParserConfigurationException e){
            System.out.printf("parser was unable to be configured");
        } catch (SAXException e) {
            System.out.printf("parsing error");
        }

    }

    /**
     *
     * @param root
     * @return
     */
    private LinkedList<LinkedList<Bonus>> createBonuses(Node root) {
        LinkedList<LinkedList<Bonus>> bonuses = new LinkedList<LinkedList<Bonus>>();
        NodeList l = root.getChildNodes();
        for (int i = 0; i < l.getLength(); i++) {
            Node node = l.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equalsIgnoreCase("bonus")) {
                bonuses.add(createBonus(node));
            }
        }
        return bonuses;
    }

    /**
     *
     * @param root
     * @return
     */
    private LinkedList<Bonus> createBonus(Node root) {
        LinkedList<Bonus> bonuses = new LinkedList<Bonus>();
        if (hasAttribute("draw", root)) {
            bonuses.add(new CardBonus(Integer.parseInt(getAttribute("draw", root))));
        }
        if (hasAttribute("earn", root)) {
            bonuses.add(new CoinBonus(Integer.parseInt(getAttribute("earn", root))));
        }
        if (hasAttribute("assistants", root)) {
            bonuses.add(new AssistantBonus(Integer.parseInt(getAttribute("assistants", root))));
        }
        if (hasAttribute("nobility", root)) {
            bonuses.add(new NoblityBonus(Integer.parseInt(getAttribute("nobility", root))));
        }
        if (hasAttribute("victory", root)) {
            bonuses.add(new VictoryBonus(Integer.parseInt(getAttribute("victory", root))));
        }
        if (hasAttribute("action", root)) {
            bonuses.add(new MainActionBonus(Integer.parseInt(getAttribute("action", root))));
        }
        return bonuses;
    }

    /**
     *
     * @param root
     * @param bonuses
     * @return
     */
    private LinkedList<City> createCities(Node root, LinkedList<LinkedList<Bonus>> bonuses) {
        LinkedList<City> cities = new LinkedList<City>();
        for (Node node: getNodes("city", root)) {
            CityColor color = new CityColor(getAttribute("color", node));
            String name = getAttribute("name", node);
            cities.add(new City(name, color, bonuses.poll()));
        }
        return cities;
    }

    /**
     *
     * @param root
     * @return
     */
    private Pair<LinkedList<Councillor>, LinkedList<PoliticCard>> createPolitic(Node root) {
        LinkedList<Councillor> councillors = new LinkedList<Councillor>();
        LinkedList<PoliticCard> cards = new LinkedList<PoliticCard>();
        for (Node node: getNodes("color", root)) {
            String colorString = getAttribute("name", node);
            int councillorsNumber = Integer.parseInt(getAttribute("councillors", node));
            int cardsNumber = Integer.parseInt(getAttribute("cards", node));
            CouncillorColor color = new CouncillorColor(colorString);
            for ( ; councillorsNumber > 0; councillorsNumber--) {
                Councillor councillor = new Councillor(color);
                councillors.add(councillor);
            }
            for ( ; cardsNumber > 0; cardsNumber--) {
                PoliticCard card = new PoliticCard(color);
                cards.add(card);
            }
        }
        return new Pair(councillors, cards);
    }

    /**
     *
     * @param root
     * @param cities
     * @return
     */
    private LinkedList<BusinessPermissionTile> createTiles(Node root, LinkedList<City> cities) {
        LinkedList<BusinessPermissionTile> tiles = new LinkedList<BusinessPermissionTile>();
        for (Node node: getNodes("permissionTile", root)) {
            LinkedList<City> c = getCities(getNode("cities", node), cities);
            LinkedList<Bonus> b = createBonus(getNode("bonus", node));
            tiles.add(new BusinessPermissionTile(c, b));
        }
        return tiles;
    }

    /**
     *
     * @param root
     * @return
     */
    private NobilityTrack createNobilityTrack(Node root) {
        LinkedList<NobilityCell> cells = new LinkedList<NobilityCell>();
        // TODO implement
        return new NobilityTrack(cells);
    }

    /**
     *
     * @param root
     * @param allCities
     * @return
     */
    private LinkedList<City> getCities(Node root, LinkedList<City> allCities) {
        LinkedList<City> cities = new LinkedList<City>();
        for (Node node: getNodes("city", root)) {
            String name = getAttribute("name", node);
            for (City city: allCities) {
                if (city.getName().equalsIgnoreCase(name)) {
                    cities.add(city);
                    break;
                }
            }
        }
        return cities;
    }

    /**
     *
     * @param name
     * @param root
     * @return
     */
    private Boolean hasNode(String name, Node root) {
        NodeList l = root.getChildNodes();
        for (int i = 0; i < l.getLength(); i++) {
            Node node = l.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param name
     * @param root
     * @return
     */
    private Node getNode(String name, Node root) {
        NodeList l = root.getChildNodes();
        for (int i = 0; i < l.getLength(); i++) {
            Node node = l.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equalsIgnoreCase(name)) {
                return node;
            }
        }
        return null;
    }

    /**
     * 
     * @param name
     * @param root
     * @return
     */
    private LinkedList<Node> getNodes(String name, Node root) {
        LinkedList<Node> nodes = new LinkedList<Node>();
        NodeList l = root.getChildNodes();
        for (int i = 0; i < l.getLength(); i++) {
            Node node = l.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equalsIgnoreCase(name)) {
                nodes.add(node);
            }
        }
        return nodes;
    }

    /**
     *
     * @param name
     * @param node
     * @return
     */
    private Boolean hasAttribute(String name, Node node) {
        NamedNodeMap attributes = node.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++ ) {
            Node attr = attributes.item(i);
            if (attr.getNodeName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param name
     * @param node
     * @return
     */
    private String getAttribute(String name, Node node) {
        NamedNodeMap attributes = node.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++ ) {
            Node attr = attributes.item(i);
            if (attr.getNodeName().equalsIgnoreCase(name)) {
                return attr.getNodeValue();
            }
        }
        return "";
    }

    /**
     *
     */
    private void dump(Node root, int rientro) {
        NodeList l = root.getChildNodes();
        for (int i = 0; i < l.getLength(); i++) {
            Node node = l.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                for (int j = 0; j < rientro; j++) {
                    System.out.print("  ");
                }
                System.out.println(node.getNodeName());
                if (node.hasChildNodes()) {
                    dump(node, rientro + 1);
                }
            }
        }
    }

}