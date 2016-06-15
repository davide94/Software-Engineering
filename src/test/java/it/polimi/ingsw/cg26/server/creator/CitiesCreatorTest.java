package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.model.board.City;
import it.polimi.ingsw.cg26.server.model.board.CityColor;
import it.polimi.ingsw.cg26.server.model.bonus.EmptyBonus;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class CitiesCreatorTest {

    private Node root;
    private PoliticDeck politicDeck;

    @Before
    public void setUp() throws Exception {
        DOMParserInterface parserInterface = new XMLAdapter();
        Document document = parserInterface.parse("maps/test.xml", "maps/schema.xsd");
        root = document.getDocumentElement();

        politicDeck = new PoliticDeck(new LinkedList<>());
    }

    @Test (expected = NullPointerException.class)
    public void testCreateCitiesShouldThrowNullPointerException1() throws Exception {
        CitiesCreator.createCities(null, politicDeck);
    }

    @Test (expected = NullPointerException.class)
    public void testCreateCitiesShouldThrowNullPointerException2() throws Exception {
        CitiesCreator.createCities(root, null);
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
        citiesTest.add(City.createCity("arkon", CityColor.createCityColor("blue"), new EmptyBonus()));
        citiesTest.add(City.createCity("burgen", CityColor.createCityColor("blue"), new EmptyBonus()));
        citiesTest.add(City.createCity("castrum", CityColor.createCityColor("blue"), new EmptyBonus()));

        assertEquals(cities, citiesTest);
    }

}