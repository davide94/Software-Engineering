package it.polimi.ingsw.cg26.server.model.market;

import it.polimi.ingsw.cg26.common.dto.RewardTileDTO;
import it.polimi.ingsw.cg26.server.model.board.NobilityCell;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.server.model.cards.RewardTile;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 *
 */
public class SellableTest {

    private Sellable sellable;

    @Before
    public void setUp() throws Exception {
        sellable = new Assistant();
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetNegativePrice() throws Exception {
        sellable.setPrice(-3);
    }

    @Test
    public void testSetAndGetPrice() throws Exception {
        sellable.setPrice(3);
        assertEquals(sellable.getPrice(), 3);
    }

    @Test
    public void testSetOwnerToNull() throws Exception {
        sellable.setOwner(null);
    }

    @Test
    public void testSetAndGetOwner() throws Exception {
        NobilityCell nobilityCell = NobilityCell.createNobilityCell(10, null, new RewardTile(new LinkedList<Bonus>()));
        NobilityCell nobilityCell2 = NobilityCell.createNobilityCell(9, nobilityCell, new RewardTile(new LinkedList<Bonus>()));
        LinkedList<PoliticCard> cards = new LinkedList<>();
        LinkedList<Assistant> assistants = new LinkedList<>();
        Player player = new Player(1234, "name", nobilityCell2, 10, cards, assistants);

        sellable.setOwner(player);
        assertEquals(sellable.getOwner(), player);
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(sellable.hashCode(), new Assistant().hashCode());
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(sellable.equals(new Assistant()));
        assertTrue(sellable.equals(sellable));
        assertFalse(sellable.equals(null));
        assertFalse(sellable.equals(new PoliticColor("red")));
    }
}