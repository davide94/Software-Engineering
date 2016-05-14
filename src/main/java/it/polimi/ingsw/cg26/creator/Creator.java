package it.polimi.ingsw.cg26.creator;

import it.polimi.ingsw.cg26.Logger;
import it.polimi.ingsw.cg26.controller.Controller;
import it.polimi.ingsw.cg26.exceptions.BadInputFileException;
import it.polimi.ingsw.cg26.model.board.*;
import it.polimi.ingsw.cg26.model.bonus.*;
import it.polimi.ingsw.cg26.model.cards.*;
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
     */
    public Controller newGame(String file) throws IOException, ParserConfigurationException {

        try {
            Instant before = Instant.now();

            DOMParserInterface parserInterface = new XMLAdapter();

            Document document = parserInterface.parse(file, "src/main/resources/schema.xsd");

            Node root = document.getFirstChild();

            LinkedList<PoliticCard> cards = createCards(root);
            LinkedList<Councillor> councillors = createCouncillors(root);
            PoliticDeck politicDeck = new PoliticDeck(cards);

            LinkedList<LinkedList<City>> cities = createCities(root);

            LinkedList<Region> regions = createRegions(root, cities);

            Balcony kingsBalcony = new Balcony(4);

            electCouncillors(kingsBalcony, regions, councillors);

            NobilityTrack nobilityTrack = createNobilityTrack(root);

            King king = createKing(root, cities);

            GameBoard gameBoard = new GameBoard(politicDeck, councillors, kingsBalcony, regions, nobilityTrack, king);

            //Market market = new Market();

            //Logger.log(gameBoard.getState());

            Controller controller = new Controller(gameBoard);

            long delta = Duration.between(before, Instant.now()).toMillis();
            //Logger.log(Logger.DEBUG, "Game created in " + delta + " ms");

            return controller;

        } catch (SAXException e) {
            Logger.log(e.getMessage());
            throw new BadInputFileException(e);
        }

    }

    private LinkedList<PoliticCard> createCards(Node root) {
        LinkedList<PoliticCard> cards = new LinkedList<>();
        for (Node node: getNodes(getNode(root, "politic"), "color")) {
            String colorString = getAttribute(node, "name");
            int cardsNumber = Integer.parseInt(getAttribute(node, "cards"));
            PoliticColor color = new PoliticColor(colorString);
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
            PoliticColor color = new PoliticColor(colorString);
            for ( ; councillorsNumber > 0; councillorsNumber--) {
                Councillor councillor = new Councillor(color);
                councillors.add(councillor);
            }
        }
        return councillors;
    }

    private LinkedList<LinkedList<City>> createCities(Node root) {
        LinkedList<LinkedList<Bonus>> bonuses = createBonuses(getNode(root, "cityBonuses"));
        LinkedList<LinkedList<City>> cities = new LinkedList<>();
        LinkedList<City> allCities = new LinkedList<>();
        Random random = new Random();
        for (Node region: getNodes(getNode(root, "regions"), "region")) {
            LinkedList<City> regionCities = new LinkedList<>();
            for (Node node : getNodes(getNode(region, "cities"), "city")) {
                String colorString = getAttribute(node, "color");
                String name = getAttribute(node, "name");
                CityColor color = new CityColor(colorString);
                LinkedList<Bonus> bonus = new LinkedList<>();
                if (!colorString.equalsIgnoreCase("viola"))
                    bonus.addAll(bonuses.remove(random.nextInt(bonuses.size())));
                City city = new City(name, color, bonus);
                regionCities.add(city);
            }
            cities.add(regionCities);
            allCities.addAll(regionCities);
        }
        for (Node region: getNodes(getNode(root, "regions"), "region"))
            linkCities(region, allCities);
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

    private LinkedList<Region> createRegions(Node root, LinkedList<LinkedList<City>> allCities) {
        LinkedList<Region> regions = new LinkedList<>();
        int index = 0;
        for (Node regionRoot: getNodes(getNode(root, "regions"), "region")) {
            LinkedList<City> cities = allCities.get(index);
            String name = getAttribute(regionRoot, "name");
            LinkedList<BusinessPermissionTile> tiles = createTiles(getNode(regionRoot, "permissionTiles"), cities); // TODO filtrare
            BusinessPermissionTileDeck tilesDeck = new BusinessPermissionTileDeck(tiles);
            Balcony balcony = new Balcony(4);
            LinkedList<Bonus> bonus = createBonus(getNode(regionRoot, "bonus"));
            regions.add(new Region(name, cities, tilesDeck, balcony, bonus));
            index++;
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

        for (Region region: regions) {
            for (int i = 0; i < 4; i++) {
                if (councillors.isEmpty())
                    throw new BadInputFileException();
                Councillor councillor = councillors.remove(random.nextInt(councillors.size()));
                region.getBalcony().elect(councillor);
            }
        }

        for (int i = 0; i < 4; i++) {
            if (councillors.isEmpty())
                throw new BadInputFileException();
            Councillor councillor = councillors.remove(random.nextInt(councillors.size()));
            kingsBalcony.elect(councillor);
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

    private King createKing(Node root, LinkedList<LinkedList<City>> cities) {
        King king = null;
        LinkedList<City> allCities = new LinkedList<>();
        for (LinkedList<City> region: cities)
            allCities.addAll(region);
        for (Node region: getNodes(getNode(root, "regions"), "region"))
            for (Node city: getNodes(getNode(region, "cities"), "city"))
                if (hasChild("king", city)) {
                    String name = getAttribute(city, "name");
                    king = new King(getCity(name, allCities));
                }
        if (king == null)
            throw new BadInputFileException();
        return king;
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