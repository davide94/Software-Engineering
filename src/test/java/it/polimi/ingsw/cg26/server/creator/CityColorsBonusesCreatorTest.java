package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.model.board.CityColor;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.bonus.VictoryBonus;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.server.model.cards.RewardTile;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static org.junit.Assert.*;

/**
 *
 */
public class CityColorsBonusesCreatorTest {

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
    public void testCreateCityColorsBonuses() throws Exception {
        /*
            <cityColorsBonuses>
                <color name="blue"><bonus victory="5" /></color>
                <color name="red"><bonus victory="8" /></color>
                <color name="grey"><bonus victory="12" /></color>
                <color name="yellow"><bonus victory="20" /></color>
            </cityColorsBonuses>
         */

        Map<CityColor, RewardTile> cityColorsBonuses = CityColorsBonusesCreator.createCityColorsBonuses(root, politicDeck);

        Map<CityColor, RewardTile> cityColorsBonusesTest = new HashMap<>();

        LinkedList<Bonus> b1 = new LinkedList<>();
        b1.add(new VictoryBonus(5));
        cityColorsBonusesTest.put(CityColor.createCityColor("blue"), new RewardTile(b1));

        LinkedList<Bonus> b2 = new LinkedList<>();
        b2.add(new VictoryBonus(8));
        cityColorsBonusesTest.put(CityColor.createCityColor("red"), new RewardTile(b2));

        LinkedList<Bonus> b3 = new LinkedList<>();
        b3.add(new VictoryBonus(12));
        cityColorsBonusesTest.put(CityColor.createCityColor("grey"), new RewardTile(b3));

        LinkedList<Bonus> b4 = new LinkedList<>();
        b4.add(new VictoryBonus(20));
        cityColorsBonusesTest.put(CityColor.createCityColor("yellow"), new RewardTile(b4));

        assertEquals(cityColorsBonuses, cityColorsBonusesTest);
    }
}