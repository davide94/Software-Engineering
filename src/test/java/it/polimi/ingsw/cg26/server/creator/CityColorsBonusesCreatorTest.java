package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.model.board.CityColor;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.bonus.EmptyBonus;
import it.polimi.ingsw.cg26.server.model.bonus.VictoryBonus;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class CityColorsBonusesCreatorTest {

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
    public void testCreateCityColorsBonusesShouldThrowNullPointerException1() throws Exception {
        CityColorsBonusesCreator.createCityColorsBonuses(null, politicDeck);
    }

    @Test (expected = NullPointerException.class)
    public void testCreateCityColorsBonusesShouldThrowNullPointerException2() throws Exception {
        CityColorsBonusesCreator.createCityColorsBonuses(root, null);
    }

    @Test
    public void testCreateCityColorsBonuses() throws Exception {
        /*
            <cityColorsBonuses>
                <color name="blue"><bonus victory="5" /></color>
                <color name="red"><bonus victory="8" /></color>
                <color name="grey"><bonus victory="12" /></color>
                <color name="yellow"><bonus victory="20" /></color>
            </cityColorsBonuses>
         */

        Map<CityColor, Bonus> cityColorsBonuses = CityColorsBonusesCreator.createCityColorsBonuses(root, politicDeck);

        Map<CityColor, Bonus> cityColorsBonusesTest = new HashMap<>();

        Bonus b1 = new VictoryBonus(new EmptyBonus(), 5);
        cityColorsBonusesTest.put(CityColor.createCityColor("blue"), b1);

        Bonus b2 = new VictoryBonus(new EmptyBonus(), 8);
        cityColorsBonusesTest.put(CityColor.createCityColor("red"), b2);

        Bonus b3 = new VictoryBonus(new EmptyBonus(), 12);
        cityColorsBonusesTest.put(CityColor.createCityColor("grey"), b3);

        Bonus b4 = new VictoryBonus(new EmptyBonus(), 20);
        cityColorsBonusesTest.put(CityColor.createCityColor("yellow"), b4);

        assertEquals(cityColorsBonuses, cityColorsBonusesTest);
    }
}