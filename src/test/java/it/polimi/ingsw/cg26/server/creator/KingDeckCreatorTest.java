package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
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

        LinkedList<Bonus> b1 = new LinkedList<>();
        b1.add(new VictoryBonus(25));
        tiles.add(new RewardTile(b1));

        LinkedList<Bonus> b2 = new LinkedList<>();
        b2.add(new VictoryBonus(18));
        tiles.add(new RewardTile(b2));

        LinkedList<Bonus> b3 = new LinkedList<>();
        b3.add(new VictoryBonus(14));
        tiles.add(new RewardTile(b3));

        LinkedList<Bonus> b4 = new LinkedList<>();
        b4.add(new VictoryBonus(8));
        tiles.add(new RewardTile(b4));

        LinkedList<Bonus> b5 = new LinkedList<>();
        b5.add(new VictoryBonus(5));
        tiles.add(new RewardTile(b5));

        assertEquals(new KingDeck(tiles), KingDeckCreator.createKingDeck(root, politicDeck));
    }
}