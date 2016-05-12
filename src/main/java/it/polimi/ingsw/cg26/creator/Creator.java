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
import java.time.Duration;
import java.time.Instant;
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
        // nothing to do here
    }

    /**
     * Creates the initial game structure.
     * @param file is the path+name of the configuration file
     * @param playersNumber is the number of players
     */
    public void newGame(String file, int playersNumber) throws IOException, ParserConfigurationException {

        try {
            Instant before = Instant.now();

            DOMParserInterface parserInterface = new XMLAdapter();

            Document document = parserInterface.parse(file, "src/main/resources/schema.xsd");

            Node root = document.getFirstChild();

            LinkedList<PoliticCard> cards = createCards(root);
            LinkedList<Councillor> councillors = createCouncillors(root);
            PoliticDeck politicDeck = new PoliticDeck(cards);

            LinkedList<City> cities = createCities(root);

            /*for (City c1: cities)
                for (City c2: cities)
                    System.out.println("Distance from " + c1.getName() + " to " +c2.getName() + " = " + c1.distanceFrom(c2));
            */
            LinkedList<Region> regions = createRegions(root, cities);

            Balcony kingsBalcony = new Balcony(4);

            electCouncillors(kingsBalcony, regions, councillors);

            NobilityTrack nobilityTrack = createNobilityTrack(root);

            King king = createKing(root, cities);

            GameBoard gameBoard = new GameBoard(politicDeck, councillors, kingsBalcony, regions, nobilityTrack, king);

            //Market market = new Market();

            GameLogic gameLogic = new GameLogic(gameBoard);

            createPlayers(playersNumber, gameLogic, nobilityTrack.getFirstCell());

            View view = new View();
            Thread view1 = new Thread(view, "Player1");
            Controller controller = new Controller(gameBoard);

            gameLogic.registerObserver(view);
            view.registerObserver(controller);

            Instant after = Instant.now();
            long delta = Duration.between(before, after).toMillis();
            System.out.println("Game created in " + delta + " ms");

            view1.start();

        } catch (SAXException e) {
            System.out.println(e.getMessage());
            throw new BadInputFileException(e);
        }

    }

    private LinkedList<PoliticCard> createCards(Node root) {
        LinkedList<PoliticCard> cards = new LinkedList<>();
        for (Node node: getNodes(getNode(root, "politic"), "color")) {
            String colorString = getAttribute(node, "name");
            int cardsNumber = Integer.parseInt(getAttribute(node, "cards"));
            CouncillorColor color = new CouncillorColor(colorString);
            for ( ; cardsNumber > 0; cardsNumber--) {
                PoliticCard card = new PoliticCard(color);
                cards.add(card);
            }
        }
        Collections.shuffle(cards);
        return cards;
    }

    private LinkedList<Councillor> createCouncillors(Node root) {
        LinkedList<Councillor> councillors = new LinkedList<>();
        for (Node node: getNodes(getNode(root, "politic"), "color")) {
            String colorString = getAttribute(node, "name");
            int councillorsNumber = Integer.parseInt(getAttribute(node, "councillors"));
            CouncillorColor color = new CouncillorColor(colorString);
            for ( ; councillorsNumber > 0; councillorsNumber--) {
                Councillor councillor = new Councillor(color);
                councillors.add(councillor);
            }
        }
        return councillors;
    }

    private LinkedList<City> createCities(Node root) {
        LinkedList<LinkedList<Bonus>> bonuses = createBonuses(getNode(root, "cityBonuses"));
        LinkedList<City> cities = new LinkedList<>();
        Random random = new Random();
        for (Node region: getNodes(getNode(root, "regions"), "region"))
            for (Node node: getNodes(getNode(region, "cities"), "city")) {
                String colorString = getAttribute(node, "color");
                String name = getAttribute(node, "name");
                CityColor color = new CityColor(colorString);
                LinkedList<Bonus> bonus = new LinkedList<>();
                if (!colorString.equalsIgnoreCase("viola"))
                    bonus.addAll(bonuses.remove(random.nextInt(bonuses.size())));
                City city = new City(name, color, bonus);
                cities.add(city);
            }
        for (Node region: getNodes(getNode(root, "regions"), "region"))
            linkCities(region, cities);
        return cities;
    }

    private void linkCities(Node root, LinkedList<City> cities) {
        for (Node node: getNodes(getNode(root, "cities"), "city")) {
            String name = getAttribute(node, "name");
            City city = null;
            for (City c: cities)
                if (c.getName().equalsIgnoreCase(name))
                    city = c;
            for (Node near: getNodes(node, "near")) {
                String nameNear = getAttribute(near, "name");
                City nearCity = getCity(nameNear, cities);
                if (city != null && nearCity != null) {
                    city.link(nearCity);
                    nearCity.link(city);
                }
            }
        }
    }

    private LinkedList<LinkedList<Bonus>> createBonuses(Node root) {
        LinkedList<LinkedList<Bonus>> bonuses = new LinkedList<>();
        for (Node node: getNodes(root, "bonus"))
            bonuses.add(createBonus(node));
        return bonuses;
    }

    private LinkedList<Bonus> createBonus(Node root) {
        LinkedList<Bonus> bonuses = new LinkedList<>();
        if (hasAttribute(root, "draw"))
            bonuses.add(new CardBonus(Integer.parseInt(getAttribute(root, "draw"))));
        if (hasAttribute(root, "earn"))
            bonuses.add(new CoinBonus(Integer.parseInt(getAttribute(root, "earn"))));
        if (hasAttribute(root, "assistants"))
            bonuses.add(new AssistantBonus(Integer.parseInt(getAttribute(root, "assistants"))));
        if (hasAttribute(root, "nobility"))
            bonuses.add(new NoblityBonus(Integer.parseInt(getAttribute(root, "nobility"))));
        if (hasAttribute(root, "victory"))
            bonuses.add(new VictoryBonus(Integer.parseInt(getAttribute(root, "victory"))));
        if (hasAttribute(root, "action"))
            bonuses.add(new MainActionBonus(Integer.parseInt(getAttribute(root, "action"))));
        return bonuses;
    }

    private LinkedList<Region> createRegions(Node root, LinkedList<City> cities) {
        LinkedList<Region> regions = new LinkedList<>();
        for (Node regionRoot: getNodes(getNode(root, "regions"), "region")) {
            String name = getAttribute(regionRoot, "name");
            LinkedList<BusinessPermissionTile> tiles = createTiles(getNode(regionRoot, "permissionTiles"), cities);
            BusinessPermissionTileDeck tilesDeck = new BusinessPermissionTileDeck(tiles);
            Balcony balcony = new Balcony(4);
            LinkedList<Bonus> bonus = createBonus(getNode(regionRoot, "bonus"));
            regions.add(new Region(name, cities, tilesDeck, balcony, bonus));
        }
        return regions;
    }

    private LinkedList<BusinessPermissionTile> createTiles(Node root, LinkedList<City> cities) {
        LinkedList<BusinessPermissionTile> tiles = new LinkedList<>();
        for (Node node: getNodes(root, "permissionTile")) {
            LinkedList<City> c = getCities(getNode(node, "cities"), cities);
            LinkedList<Bonus> b = createBonus(getNode(node, "bonus"));
            tiles.add(new BusinessPermissionTile(c, b));
        }
        return tiles;
    }

    private void electCouncillors(Balcony kingsBalcony, LinkedList<Region> regions, LinkedList<Councillor> councillors) {
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            if (councillors.isEmpty())
                throw new BadInputFileException();
            kingsBalcony.elect(councillors.remove(random.nextInt(councillors.size())));
            for (Region region: regions) {
                if (councillors.isEmpty())
                    throw new BadInputFileException();
                region.elect(councillors.remove(random.nextInt(councillors.size())));
            }
        }
    }

    private NobilityTrack createNobilityTrack(Node root) {
        Node nobilityTrackRoot = getNode(root, "nobilitytrack");
        int len = Integer.parseInt(getAttribute(nobilityTrackRoot, "len"));
        ArrayList<LinkedList<Bonus>> bonuses = new ArrayList<>(Collections.nCopies(len, new LinkedList<>()));
        for (Node node: getNodes(nobilityTrackRoot, "bonus")) {
            int position = Integer.parseInt(getAttribute(node, "position")) - 1;
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

    private King createKing(Node root, LinkedList<City> cities) {
        King king = null;
        for (Node region: getNodes(getNode(root, "regions"), "region"))
            for (Node city: getNodes(getNode(region, "cities"), "city"))
                if (hasChild("king", city)) {
                    String name = getAttribute(city, "name");
                    king = new King(getCity(name, cities));
                }
        if (king == null)
            throw new BadInputFileException();
        return king;
    }

    private void createPlayers(int n, GameLogic gameLogic, NobilityCell firstCell) {
        for (int i = 0 ; i < n; i++) {
            LinkedList<Assistant> assistants = new LinkedList<>();
            for (int j = 0; j <= i; j++)
                assistants.add(new Assistant());
            Player player = new Player(gameLogic, firstCell, i + 10, assistants);
            gameLogic.addPlayer(player);
        }
    }

    private LinkedList<City> getCities(Node root, LinkedList<City> allCities) {
        LinkedList<City> cities = new LinkedList<>();
        for (Node node: getNodes(root, "city")) {
            String name = getAttribute(node, "name");
            City city = getCity(name, allCities);
            if (city != null)
                cities.add(city);
        }
        return cities;
    }

    private City getCity(String name, LinkedList<City> allCities) {
        for (City city: allCities)
            if (city.getName().equalsIgnoreCase(name))
                return city;
        return null;
    }

    private Boolean hasChild(String name, Node root) {
        NodeList l = root.getChildNodes();
        for (int i = 0; i < l.getLength(); i++) {
            Node node = l.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equalsIgnoreCase(name))
                return true;
        }
        return false;
    }

    private LinkedList<Node> getNodes(Node root, String name) {
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

    private Node getNode(Node root, String name) {
        return getNodes(root, name).poll();
    }

    private Boolean hasAttribute(Node root, String name) {
        NamedNodeMap attributes = root.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attr = attributes.item(i);
            if (attr.getNodeName().equalsIgnoreCase(name))
                return true;
        }
        return false;
    }

    private String getAttribute(Node root, String name) {
        NamedNodeMap attributes = root.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++ ) {
            Node attr = attributes.item(i);
            if (attr.getNodeName().equalsIgnoreCase(name))
                return attr.getNodeValue();
        }
        return "";
    }

}