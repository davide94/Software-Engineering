package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.bonus.EmptyBonus;
import it.polimi.ingsw.cg26.server.model.bonus.VictoryBonus;
import it.polimi.ingsw.cg26.server.model.cards.KingDeck;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.server.model.cards.RewardTile;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.time.Instant;
import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 *
 */
public class KingDeckCreatorTest {

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

    @Test (expected = NullPointerException.class)
    public void testCreateKingDeckShouldThrowNullPointerException1() throws Exception {
        KingDeckCreator.createKingDeck(null, politicDeck);
    }

    @Test (expected = NullPointerException.class)
    public void testCreateKingDeckShouldThrowNullPointerException2() throws Exception {
        KingDeckCreator.createKingDeck(root, null);
    }

    @Test
    public void testCreateKingDeck() throws Exception {
        /*
            <kingDeck>
                <bonus position="1" victory="25" />
                <bonus position="2" victory="18" />
                <bonus position="3" victory="14" />
                <bonus position="4" victory="8" />
                <bonus position="5" victory="5" />
            </kingDeck>
         */
        LinkedList<RewardTile> tiles = new LinkedList<>();

        Bonus b1 = new VictoryBonus(new EmptyBonus(), 25);
        tiles.add(new RewardTile(b1));

        Bonus b2 = new VictoryBonus(new EmptyBonus(), 18);
        tiles.add(new RewardTile(b2));

        Bonus b3 = new VictoryBonus(new EmptyBonus(), 14);
        tiles.add(new RewardTile(b3));

        Bonus b4 = new VictoryBonus(new EmptyBonus(), 8);
        tiles.add(new RewardTile(b4));

        Bonus b5 = new VictoryBonus(new EmptyBonus(), 5);
        tiles.add(new RewardTile(b5));

        assertEquals(new KingDeck(tiles), KingDeckCreator.createKingDeck(root, politicDeck));
    }
}