package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.model.bonus.*;
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
public class BonusesCreatorTest {

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
    public void testCreateBonuses() throws Exception {

        /*
            <bonus assistants="1" earn="2" />
            <bonus nobility="3" victory="4" />
            <bonus earn="5" victory="6" />
            <bonus draw="7" action="8" />
        */

        List<RewardTile> tiles = new LinkedList<>();

        Collection<Bonus> bonuses1 = new LinkedList<>();
        bonuses1.add(new AssistantBonus(1));
        bonuses1.add(new CoinBonus(2));
        tiles.add(new RewardTile(bonuses1));

        Collection<Bonus> bonuses2 = new LinkedList<>();
        bonuses2.add(new NobilityBonus(3));
        bonuses2.add(new VictoryBonus(4));
        tiles.add(new RewardTile(bonuses2));

        Collection<Bonus> bonuses3 = new LinkedList<>();
        bonuses3.add(new CoinBonus(5));
        bonuses3.add(new VictoryBonus(6));
        tiles.add(new RewardTile(bonuses3));

        Collection<Bonus> bonuses4 = new LinkedList<>();
        bonuses4.add(new CardBonus(7, politicDeck));
        bonuses4.add(new MainActionBonus(8));
        tiles.add(new RewardTile(bonuses4));

        List<RewardTile> bonuses = BonusesCreator.createBonuses(Creator.getNode(root, "cityBonuses"), politicDeck);

        assertEquals(bonuses, tiles);
    }
}