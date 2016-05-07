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
 * The ModelCreator class is used to create the initial game structure.
 * To build all the classes required to start a game use newGame(String file, int playersNumber).
 */
public class ModelCreator {

    /**
     * Default constructor
     */
    public ModelCreator() {
    }

    /**
     * Creates the initial game structure.
     * @param file is the path+name of the configuration file
     * @param playersNumber is the number of players
     */
    public void newGame(String file, int playersNumber) throws IOException {

        try {
            DOMParserInterface parserInterface = new XMLAdapter();

            if (!parserInterface.validate(file, "src/main/schema.xml"))
                throw new BadInputFileException();

            Document document = parserInterface.parse(file);

            Node game = document.getFirstChild();

            if (!hasNode("nobilitytrack", game) || !hasNode("politic", game))
                throw new BadInputFileException();

            Node nobilityTrackRoot = getNode("nobilitytrack", game);
            Node politicRoot = getNode("politic", game);

            // create king
            King king = new King();

            // create councillors + PoliticCardsDeck
            Pair<LinkedList<Councillor>, LinkedList<PoliticCard>> politic = createPolitic(politicRoot);
            LinkedList<Councillor> councillors = politic.getKey();
            PoliticDeck politicDeck = new PoliticDeck(politic.getValue());

            // create regions
            LinkedList<Region> regions = createRegions(game);

            // create balcony
            Balcony kingsBalcony = new Balcony();

            // elect councillors
            electCouncillors(kingsBalcony, regions, councillors);

            // create nobility track
            NobilityTrack nobilityTrack = createNobilityTrack(nobilityTrackRoot);

            // create gameBoard
            GameBoard gameBoard = new GameBoard(politicDeck, councillors, kingsBalcony, regions, nobilityTrack, king);

            // create market
            Market market = new Market();

            // create GameLogic
            GameLogic gameLogic = new GameLogic(gameBoard);

            // create players
            createPlayers(playersNumber, gameLogic);


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
     */
    private LinkedList<Region> createRegions(Node root) {
        if (!hasNode("citybonuses", root) || !hasNode("regions", root))
            throw new BadInputFileException();

        LinkedList<Region> regions = new LinkedList<>();
        LinkedList<LinkedList<Bonus>> cityBonuses = createBonuses(getNode("citybonuses", root));
        Node regionsRoot = getNode("regions", root);
        for (Node regionRoot: getNodes("region", regionsRoot)) {
            Region region = createRegion(root, regionRoot, cityBonuses);
            regions.add(region);
        }
        // TODO create links between cities
        return regions;
    }

    /**
     *
     */
    private Region createRegion(Node root, Node regionRoot, LinkedList<LinkedList<Bonus>> cityBonuses) {

        LinkedList<City> cities = createCities(getNode("cities", regionRoot), cityBonuses);
        LinkedList<BusinessPermissionTile> tiles = createTiles(getNode("permissionTiles", regionRoot), cities);
        BusinessPermissionTileDeck tilesDeck = new BusinessPermissionTileDeck(tiles);
        Balcony balcony = new Balcony();
        Bonus bonus = null;
        // TODO create bonus
        Region region = new Region(cities, tilesDeck, balcony, bonus);
        return region;
    }

    /**
     *
     * @param root
     * @param bonuses
     * @return
     */
    private LinkedList<City> createCities(Node root, LinkedList<LinkedList<Bonus>> bonuses) {
        LinkedList<City> cities = new LinkedList<>();
        for (Node node: getNodes("city", root)) {
            if (!hasAttribute("color", node) || !hasAttribute("name", node) || bonuses.size() == 0)
                throw new BadInputFileException();

            CityColor color = new CityColor(getAttribute("color", node));
            String name = getAttribute("name", node);
            cities.add(new City(name, color, bonuses.poll()));
        }
        return cities;
    }

    /**
     * creates a collection of bonus
     * @param root the root node that may have the bonus elements as childes
     * @return the collection of bonus
     */
    private LinkedList<LinkedList<Bonus>> createBonuses(Node root) {
        LinkedList<LinkedList<Bonus>> bonuses = new LinkedList<LinkedList<Bonus>>();
        NodeList l = root.getChildNodes();
        for (int i = 0; i < l.getLength(); i++) {
            Node node = l.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equalsIgnoreCase("bonus"))
                bonuses.add(createBonus(node));

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
        if (hasAttribute("draw", root))
            bonuses.add(new CardBonus(Integer.parseInt(getAttribute("draw", root))));

        if (hasAttribute("earn", root))
            bonuses.add(new CoinBonus(Integer.parseInt(getAttribute("earn", root))));

        if (hasAttribute("assistants", root))
            bonuses.add(new AssistantBonus(Integer.parseInt(getAttribute("assistants", root))));

        if (hasAttribute("nobility", root))
            bonuses.add(new NoblityBonus(Integer.parseInt(getAttribute("nobility", root))));

        if (hasAttribute("victory", root))
            bonuses.add(new VictoryBonus(Integer.parseInt(getAttribute("victory", root))));

        if (hasAttribute("action", root))
            bonuses.add(new MainActionBonus(Integer.parseInt(getAttribute("action", root))));

        return bonuses;
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
     */
    private void electCouncillors(Balcony kingsBalcony, LinkedList<Region> regions, LinkedList<Councillor> councillors) {
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            if (councillors.size() == 0)
                throw new BadInputFileException();
            kingsBalcony.elect(councillors.remove(random.nextInt(councillors.size())));
            for (Region region: regions) {
                if (councillors.size() == 0)
                    throw new BadInputFileException();
                region.elect(councillors.remove(random.nextInt(councillors.size())));
            }
        }
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
     */
    private void createPlayers(int n, GameLogic gameLogic) {
        for (int i = 0 ; i < n; i++) {
            LinkedList<Assistant> assistants = new LinkedList<Assistant>();
            for (int j = 0; j <= i; j++) {
                assistants.add(new Assistant());
            }
            Player player = new Player(gameLogic, i + 10, assistants);
            gameLogic.addPlayer(player);
        }
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
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equalsIgnoreCase(name))
                return true;
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
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equalsIgnoreCase(name))
                return node;
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
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attr = attributes.item(i);
            if (attr.getNodeName().equalsIgnoreCase(name))
                return true;
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
            if (attr.getNodeName().equalsIgnoreCase(name))
                return attr.getNodeValue();
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
                if (node.hasChildNodes())
                    dump(node, rientro + 1);
            }
        }
    }

}