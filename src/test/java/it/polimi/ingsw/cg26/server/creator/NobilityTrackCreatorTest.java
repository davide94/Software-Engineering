package it.polimi.ingsw.cg26.server.creator;

import it.polimi.ingsw.cg26.server.model.board.NobilityCell;
import it.polimi.ingsw.cg26.server.model.board.NobilityTrack;
import it.polimi.ingsw.cg26.server.model.bonus.*;
import it.polimi.ingsw.cg26.server.model.cards.PoliticDeck;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class NobilityTrackCreatorTest {

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

        Bonus b2 = new VictoryBonus(new CoinBonus(new EmptyBonus(), 2), 2);

        Bonus b6 = new MainActionBonus(new EmptyBonus(), 1);

        NobilityCell c7 = NobilityCell.createNobilityCell(7, null, new EmptyBonus());
        NobilityCell c6 = NobilityCell.createNobilityCell(6, c7, b6);
        NobilityCell c5 = NobilityCell.createNobilityCell(5, c6, new EmptyBonus());
        NobilityCell c4 = NobilityCell.createNobilityCell(4, c5, new EmptyBonus());
        NobilityCell c3 = NobilityCell.createNobilityCell(3, c4, new EmptyBonus());
        NobilityCell c2 = NobilityCell.createNobilityCell(2, c3, b2);
        NobilityCell c1 = NobilityCell.createNobilityCell(1, c2, new EmptyBonus());
        NobilityCell c0 = NobilityCell.createNobilityCell(0, c1, new EmptyBonus());

        NobilityTrack nobilityTrackTest = NobilityTrack.createNobilityTrack(c0);

        assertEquals(nobilityTrack, nobilityTrackTest);
    }
}