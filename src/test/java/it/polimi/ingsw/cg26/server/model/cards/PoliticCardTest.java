package it.polimi.ingsw.cg26.server.model.cards;

import it.polimi.ingsw.cg26.server.model.board.NobilityCell;
import it.polimi.ingsw.cg26.server.model.bonus.EmptyBonus;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 *
 */
public class PoliticCardTest {

    private PoliticCard card;

    @Before
    public void setUp() throws Exception {
        card = new PoliticCard(new PoliticColor("colorname"));
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail() throws Exception {
        new PoliticCard(null);
    }

    @Test
    public void testGetState() throws Exception {
        card.getState();

        NobilityCell nobilityCell = NobilityCell.createNobilityCell(10, null, new EmptyBonus());
        NobilityCell nobilityCell2 = NobilityCell.createNobilityCell(9, nobilityCell, new EmptyBonus());
        LinkedList<PoliticCard> cards = new LinkedList<>();
        cards.add(new PoliticCard(new PoliticColor("aaaa")));
        LinkedList<Assistant> assistants = new LinkedList<>();
        assistants.add(new Assistant());
        Player player = new Player(1234, "name", nobilityCell2, 10, cards, assistants);
        card.setOwner(player);

        card.getState();
    }

    @Test
    public void testGetColor() throws Exception {
        assertEquals(card.getColor(), new PoliticColor("colorname"));
    }

    @Test (expected = NullPointerException.class)
    public void testBackToOwnerShouldFail() throws Exception {
        card.backToOwner();
    }

    @Test
    public void testBackToOwner() throws Exception {
        NobilityCell nobilityCell = NobilityCell.createNobilityCell(10, null, new EmptyBonus());
        NobilityCell nobilityCell2 = NobilityCell.createNobilityCell(9, nobilityCell, new EmptyBonus());
        LinkedList<PoliticCard> cards = new LinkedList<>();
        cards.add(new PoliticCard(new PoliticColor("aaaa")));
        LinkedList<Assistant> assistants = new LinkedList<>();
        assistants.add(new Assistant());
        Player player = new Player(1234, "name", nobilityCell2, 10, cards, assistants);
        card.setOwner(player);

        card.backToOwner();
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(card.equals(new PoliticCard(new PoliticColor("colorname"))));
        assertTrue(card.equals(card));
        assertFalse(card.equals(null));
        assertFalse(card.equals(new PoliticCard(new PoliticColor("red"))));
        assertFalse(card.equals(new Assistant()));
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(card.hashCode(), card.hashCode());
    }
}