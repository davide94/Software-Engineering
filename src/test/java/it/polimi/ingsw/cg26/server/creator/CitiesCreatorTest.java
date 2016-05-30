package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.model.board.City;
import it.polimi.ingsw.cg26.server.model.board.CityColor;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.server.model.cards.RewardTile;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.time.Instant;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 *
 */
public class CitiesCreatorTest {

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
    public void testCreateCities() throws Exception {
        /*
            <cities>
                <city name="arkon" position="" color="blue">
                    <near name="burgen" />
                    <near name="castrum" />
                </city>
                <city name="burgen" position="" color="blue">
                    <near name="arkon" />
                    <near name="castrum" />
                </city>
                <city name="castrum" position="" color="blue">
                    <near name="arkon" />
                    <near name="burgen" />
                </city>
            </cities>
         */

        Collection<City> cities = new LinkedList<>();
        for (List<City> l: CitiesCreator.createCities(root, politicDeck))
            cities.addAll(l);

        Collection<City> citiesTest = new LinkedList<>();
        citiesTest.add(City.createCity("arkon", CityColor.createCityColor("blue"), new RewardTile(new LinkedList<Bonus>())));
        citiesTest.add(City.createCity("burgen", CityColor.createCityColor("blue"), new RewardTile(new LinkedList<Bonus>())));
        citiesTest.add(City.createCity("castrum", CityColor.createCityColor("blue"), new RewardTile(new LinkedList<Bonus>())));

        assertEquals(cities, citiesTest);
    }

}