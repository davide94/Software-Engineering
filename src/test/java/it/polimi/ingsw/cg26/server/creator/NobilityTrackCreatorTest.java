package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.model.board.NobilityCell;
import it.polimi.ingsw.cg26.server.model.board.NobilityTrack;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.bonus.CoinBonus;
import it.polimi.ingsw.cg26.server.model.bonus.MainActionBonus;
import it.polimi.ingsw.cg26.server.model.bonus.VictoryBonus;
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
public class NobilityTrackCreatorTest {

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
    public void testCreateNobilityTrackShouldThrowNullPointerException1() throws Exception {
        KingDeckCreator.createKingDeck(null, politicDeck);
    }

    @Test (expected = NullPointerException.class)
    public void testCreateNobilityTrackShouldThrowNullPointerException2() throws Exception {
        KingDeckCreator.createKingDeck(root, null);
    }
    @Test
    public void testCreateNobilityTrack() throws Exception {
        /*
            <nobilityTrack len="8">
                <bonus position="2" earn="2" victory="2" />
                <bonus position="6" action="1" />
            </nobilityTrack>
         */

        NobilityTrack nobilityTrack = NobilityTrackCreator.createNobilityTrack(root, politicDeck);

        LinkedList<Bonus> b2 = new LinkedList<>();
        b2.add(new CoinBonus(2));
        b2.add(new VictoryBonus(2));

        LinkedList<Bonus> b6 = new LinkedList<>();
        b6.add(new MainActionBonus(1));

        NobilityCell c7 = NobilityCell.createNobilityCell(7, null, new RewardTile(new LinkedList<Bonus>()));
        NobilityCell c6 = NobilityCell.createNobilityCell(6, c7, new RewardTile(b6));
        NobilityCell c5 = NobilityCell.createNobilityCell(5, c6, new RewardTile(new LinkedList<Bonus>()));
        NobilityCell c4 = NobilityCell.createNobilityCell(4, c5, new RewardTile(new LinkedList<Bonus>()));
        NobilityCell c3 = NobilityCell.createNobilityCell(3, c4, new RewardTile(new LinkedList<Bonus>()));
        NobilityCell c2 = NobilityCell.createNobilityCell(2, c3, new RewardTile(b2));
        NobilityCell c1 = NobilityCell.createNobilityCell(1, c2, new RewardTile(new LinkedList<Bonus>()));
        NobilityCell c0 = NobilityCell.createNobilityCell(0, c1, new RewardTile(new LinkedList<Bonus>()));

        NobilityTrack nobilityTrackTest = NobilityTrack.createNobilityTrack(c0);

        assertEquals(nobilityTrack, nobilityTrackTest);
    }
}