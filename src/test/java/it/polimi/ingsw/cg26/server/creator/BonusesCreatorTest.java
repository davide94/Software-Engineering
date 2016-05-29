package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import it.polimi.ingsw.cg26.server.model.cards.RewardTile;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 *
 */
public class BonusesCreatorTest {

    private Node root;

    @Before
    public void setUp() throws Exception {
        /*Instant before = Instant.now();

        DOMParserInterface parserInterface = new XMLAdapter();

        Document document = parserInterface.parse("src/test/resources/config.xml", "src/main/resources/schema.xsd");

        root = document.getFirstChild();*/
    }

    @Test
    public void testCreateBonuses() throws Exception {
        PoliticDeck politicDeck = new PoliticDeck(new LinkedList<>());

        //List<RewardTile> bonuses = BonusesCreator.createBonuses(Creator.getNode(root, "cityBonuses"), politicDeck);

    }

    @Test
    public void testCreateBonus() throws Exception {

    }
}