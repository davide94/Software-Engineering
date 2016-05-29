package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.model.board.City;
import it.polimi.ingsw.cg26.server.model.board.CityColor;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.server.model.cards.RewardTile;
import org.w3c.dom.Node;

import java.util.*;

/**
 *
 */
public class CitiesCreator {

    private CitiesCreator() {
        // Nothing to do here
    }

    protected static List<List<City>> createCities(Node root, PoliticDeck politicDeck) {
        List<RewardTile> bonuses = BonusesCreator.createBonuses(Creator.getNode(root, "cityBonuses"), politicDeck);
        Collections.shuffle(bonuses);

        List<List<City>> cities = new LinkedList<>();
        List<City> allCities = new LinkedList<>();

        for (Node region: Creator.getNodes(Creator.getNode(root, "regions"), "region")) {
            List<City> regionCities = new LinkedList<>();
            for (Node node : Creator.getNodes(Creator.getNode(region, "cities"), "city")) {
                regionCities.add(createCity(node, bonuses));
            }
            cities.add(regionCities);
            allCities.addAll(regionCities);
        }
        for (Node region: Creator.getNodes(Creator.getNode(root, "regions"), "region"))
            linkCities(region, allCities);
        return cities;
    }

    private static City createCity(Node root, List<RewardTile> bonuses) {
        String colorString = Creator.getAttribute(root, "color");
        String name = Creator.getAttribute(root, "name");
        CityColor color = CityColor.createCityColor(colorString);
        RewardTile bonus = new RewardTile(new LinkedList<>());
        if (!Creator.hasChild("king", root))
            bonus = bonuses.get(0);
        return City.createCity(name, color, bonus);
    }

    private static void linkCities(Node root, List<City> cities) {
        for (Node node: Creator.getNodes(Creator.getNode(root, "cities"), "city")) {
            String name = Creator.getAttribute(node, "name");
            City city = null;
            for (City c: cities)
                if (c.getName().equalsIgnoreCase(name))
                    city = c;
            for (Node near: Creator.getNodes(node, "near")) {
                String nameNear = Creator.getAttribute(near, "name");
                City nearCity = getCity(nameNear, cities);
                if (city != null && nearCity != null) {
                    city.link(nearCity);
                    nearCity.link(city);
                }
            }
        }
    }

    protected static City getCity(String name, List<City> cities) {
        for (City city: cities)
            if (city.getName().equalsIgnoreCase(name))
                return city;
        return null;
    }

    protected static List<City> getCities(Node root, List<City> cities) {
        List<City> citiesToReturn = new LinkedList<>();
        for (Node node: Creator.getNodes(root, "city")) {
            String name = Creator.getAttribute(node, "name");
            City city = getCity(name, cities);
            if (city != null)
                citiesToReturn.add(city);
        }
        return citiesToReturn;
    }
}
