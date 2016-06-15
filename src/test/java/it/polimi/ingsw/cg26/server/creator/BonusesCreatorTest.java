package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.model.bonus.*;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class BonusesCreatorTest {

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
    public void testcreateBonusShouldThrowNullPointerException1() throws Exception {
        BonusesCreator.createBonuses(Creator.getNode(root, "cityBonuses"), null);
    }

    @Test (expected = NullPointerException.class)
    public void testcreateBonusShouldThrowNullPointerException2() throws Exception {
        BonusesCreator.createBonuses(null, politicDeck);
    }

    @Test
    public void testCreateBonuses() throws Exception {
        /*
            <bonus assistants="1" earn="2" />
            <bonus nobility="3" victory="4" />
            <bonus earn="5" victory="6" />
            <bonus draw="7" action="8" />
        */

        List<Bonus> tiles = new LinkedList<>();

        Bonus bonuses1 = new AssistantBonus(new CoinBonus(new EmptyBonus(), 2), 1);
        tiles.add(bonuses1);

        Bonus bonuses2 = new VictoryBonus(new NobilityBonus(new EmptyBonus(), 3), 4);
        tiles.add(bonuses2);

        Bonus bonuses3 = new VictoryBonus(new CoinBonus(new EmptyBonus(), 5), 6);
        tiles.add(bonuses3);

        Bonus bonuses4 = new MainActionBonus(new CardBonus(new EmptyBonus(), 7, politicDeck), 8);
        tiles.add(bonuses4);

        List<Bonus> bonuses = BonusesCreator.createBonuses(Creator.getNode(root, "cityBonuses"), politicDeck);

        assertEquals(bonuses, tiles);
    }
}