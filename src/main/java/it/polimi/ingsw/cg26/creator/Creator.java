package it.polimi.ingsw.cg26.creator;

import it.polimi.ingsw.cg26.controller.Controller;
import it.polimi.ingsw.cg26.exceptions.BadInputFileException;
import it.polimi.ingsw.cg26.model.GameLogic;
import it.polimi.ingsw.cg26.model.board.*;
import it.polimi.ingsw.cg26.model.bonus.*;
import it.polimi.ingsw.cg26.model.cards.*;
import it.polimi.ingsw.cg26.model.market.Market;
import it.polimi.ingsw.cg26.model.player.Assistant;
import it.polimi.ingsw.cg26.model.player.Player;
import it.polimi.ingsw.cg26.view.View;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/**
 * The Creator class is used to create the initial game structure.
 * To build all the classes required to start a game use newGame(String file, int playersNumber).
 */
public class Creator {

    /**
     * Default constructor
     */
    public Creator() {
    }

    /**
     * Creates the initial game structure.
     * @param file is the path+name of the configuration file
     * @param playersNumber is the number of players
     */
    public Controller newGame(String file, int playersNumber) throws IOException, ParserConfigurationException {

        try {
            DOMParserInterface parserInterface = new XMLAdapter();

            Document document = parserInterface.parse(file, "src/main/resources/schema.xsd");

            Node game = document.getFirstChild();

            if (!hasChild("politic", game))
                throw new BadInputFileException();

            Node politicRoot = getNode("politic", game);

            // create councillors + PoliticCardsDeck
            //Pair<LinkedList<Councillor>, LinkedList<PoliticCard>> politic = createPolitic(politicRoot);
            LinkedList<Councillor> councillors = createCouncillors(politicRoot);
            PoliticDeck politicDeck = new PoliticDeck(createCards(politicRoot));

            // create regions
            //Pair<LinkedList<City>, LinkedList<Region>> pair = createRegions(game);
            //LinkedList<City> cities = pair.fst;
            LinkedList<Region> regions = createRegions(game);
            /*
            for (City c1: cities) {
                for(City c2: cities) {
                    System.out.println("Distance from " + c1.getName() + " to " + c2.getName() + " is: " + c1.distanceFrom(c2));
                }
            }
            */
            // create balcony
            Balcony kingsBalcony = new Balcony(4);

            // elect councillors
            electCouncillors(kingsBalcony, regions, councillors);

            // create nobility track
            NobilityTrack nobilityTrack = createNobilityTrack(game);

            // create king
            King king = createKing(game, null);

            // create gameBoard
            GameBoard gameBoard = new GameBoard(politicDeck, councillors, kingsBalcony, regions, nobilityTrack, king);

            // create market
            Market market = new Market();

            // create GameLogic
            GameLogic gameLogic = new GameLogic(gameBoard);

            // create players
            LinkedList<Player> players = createPlayers(playersNumber, gameLogic, nobilityTrack.getFirstCell());

            View view = new View();
            Controller controller = new Controller(gameLogic);

            gameLogic.addObserver(view);
            view.addObserver(controller);

            return controller;

        } catch (SAXException e) {
            System.out.println(e.getMessage());
            throw new BadInputFileException();
        }

    }

    /**
     *
     */
    private LinkedList<Region> createRegions(Node root) {
        if (!hasChild("citybonuses", root) || !hasChild("regions", root))
            throw new BadInputFileException();

        LinkedList<City> cities = new LinkedList<>();
        LinkedList<Region> regions = new LinkedList<>();
        LinkedList<LinkedList<Bonus>> cityBonuses = createBonuses(getNode("citybonuses", root));
        Node regionsRoot = getNode("regions", root);
        for (Node regionRoot: getNodes("region", regionsRoot)) {
            //Pair<LinkedList<City>, Region> pair = createRegion(root, regionRoot, cityBonuses);
            //cities.addAll(pair.fst);
            Region region = createRegion(root, regionRoot, cityBonuses);
            regions.add(region);
        }
        /*
        // TODO tre for annidati non si possono vedere
        for (Node regionRoot: getNodes("region", regionsRoot)) {
            for (Node node: getNodes("city", getNode("cities", regionRoot))) {
                String name = getAttribute("name", node);
                City city = null;
                for (City c: cities) {
                    if (c.getName().equalsIgnoreCase(name)) {
                        city = c;
                    }
                }
                for (Node near: getNodes("near", node)) {
                    String nameNear = getAttribute("name", near);
                    City nearCity = null;
                    for (City c: cities) {
                        if (c.getName().equalsIgnoreCase(nameNear)) {
                            nearCity = c;
                        }
                    }
                    if (city != null && nearCity != null) {
                        city.link(nearCity);
                        nearCity.link(city);
                    }
                }
            }
        }*/
        return regions;
    }

    /**
     *
     */
    private Region createRegion(Node root, Node regionRoot, LinkedList<LinkedList<Bonus>> cityBonuses) {
        String name = getAttribute("name", regionRoot);
        LinkedList<City> cities = createCities(getNode("cities", regionRoot), cityBonuses);
        LinkedList<BusinessPermissionTile> tiles = createTiles(getNode("permissionTiles", regionRoot), cities);
        BusinessPermissionTileDeck tilesDeck = new BusinessPermissionTileDeck(tiles);
        Balcony balcony = new Balcony(4);
        if (!hasChild("bonus", regionRoot))
            throw new BadInputFileException();
        LinkedList<Bonus> bonus = createBonus(getNode("bonus", regionRoot));
        Region region = new Region(name, cities, tilesDeck, balcony, bonus);
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
            CityColor color = new CityColor(getAttribute("color", node));
            String name = getAttribute("name", node);
            City city = new City(name, color, bonuses.poll());
            cities.add(city);
        }

        return cities;
    }

    /**
     * creates a collection of bonus
     * @param root the root node that may have the bonus elements as childes
     * @return the collection of bonus
     */
    private LinkedList<LinkedList<Bonus>> createBonuses(Node root) {
        LinkedList<LinkedList<Bonus>> bonuses = new LinkedList<>();
        for (Node node: getNodes("bonus", root))
            bonuses.add(createBonus(node));
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
    private LinkedList<Councillor> createCouncillors(Node root) {
        LinkedList<Councillor> councillors = new LinkedList<>();
        for (Node node: getNodes("color", root)) {
            String colorString = getAttribute("name", node);
            int councillorsNumber = Integer.parseInt(getAttribute("councillors", node));
            CouncillorColor color = new CouncillorColor(colorString);
            for ( ; councillorsNumber > 0; councillorsNumber--) {
                Councillor councillor = new Councillor(color);
                councillors.add(councillor);
            }
        }
        return councillors;
    }

    private LinkedList<PoliticCard> createCards(Node root) {
        LinkedList<PoliticCard> cards = new LinkedList<>();
        for (Node node: getNodes("color", root)) {
            String colorString = getAttribute("name", node);
            int cardsNumber = Integer.parseInt(getAttribute("cards", node));
            CouncillorColor color = new CouncillorColor(colorString);

            for ( ; cardsNumber > 0; cardsNumber--) {
                PoliticCard card = new PoliticCard(color);
                cards.add(card);
            }
        }
        return cards;
    }

    /**
     *
     * @param root
     * @param cities
     * @return
     */
    private LinkedList<BusinessPermissionTile> createTiles(Node root, LinkedList<City> cities) {
        LinkedList<BusinessPermissionTile> tiles = new LinkedList<>();
        for (Node node: getNodes("permissionTile", root)) {
            LinkedList<City> c = getCities(getNode("cities", node), cities);
            LinkedList<Bonus> b = createBonus(getNode("bonus", node));
            tiles.add(new BusinessPermissionTile(c, b));
        }
        return tiles;
    }

    private King createKing(Node root, LinkedList<City> cities) {
        King king = null;
        for (Node region: getNodes("region", getNode("regions", root))) {
            for (Node cityNode: getNodes("city", getNode("cities", region))) {
                String name = getAttribute("name", cityNode);
                if (hasChild("king", cityNode)) {
                    for (City city: cities) {
                        if (city.getName().equalsIgnoreCase(name)) {
                            king = new King(city);
                            break;
                        }
                    }
                }
            }
        }
        if (king == null)
            throw new BadInputFileException();
        return king;
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
        Node nobilityTrackRoot = getNode("nobilitytrack", root);
        int len = Integer.parseInt(getAttribute("len", nobilityTrackRoot));
        ArrayList<LinkedList<Bonus>> bonuses = new ArrayList<>(Collections.nCopies(len, new LinkedList<>()));
        for (Node node: getNodes("bonus", nobilityTrackRoot)) {
            int position = Integer.parseInt(getAttribute("position", node)) - 1;
            bonuses.set(position, createBonus(node));
        }
        NobilityCell last = new NobilityCell(len - 1, null, bonuses.get(len - 1));
        for (int i = len - 2; i >= 0; i--) {
            LinkedList<Bonus> bonus = bonuses.get(i);
            NobilityCell cell = new NobilityCell(i, last, bonus);
            last = cell;
        }
        return new NobilityTrack(last);
    }

    /**
     *
     */
    private LinkedList<Player> createPlayers(int n, GameLogic gameLogic, NobilityCell firstCell) {
        LinkedList<Player> players = new LinkedList<>();
        for (int i = 0 ; i < n; i++) {
            LinkedList<Assistant> assistants = new LinkedList<>();
            for (int j = 0; j <= i; j++) {
                assistants.add(new Assistant());
            }
            Player player = new Player(gameLogic, firstCell, i + 10, assistants);
            players.add(player);
            gameLogic.addPlayer(player);
        }
        return players;
    }

    /**
     *
     * @param root
     * @param allCities
     * @return
     */
    private LinkedList<City> getCities(Node root, LinkedList<City> allCities) {
        LinkedList<City> cities = new LinkedList<>();
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
    private Boolean hasChild(String name, Node root) {
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