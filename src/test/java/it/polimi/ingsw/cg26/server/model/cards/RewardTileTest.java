package it.polimi.ingsw.cg26.server.model.cards;

import it.polimi.ingsw.cg26.server.model.board.NobilityCell;
import it.polimi.ingsw.cg26.server.model.bonus.AssistantBonus;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
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
public class RewardTileTest {

    private RewardTile tile;

    @Before
    public void setUp() throws Exception {
    	Bonus bonuses = new AssistantBonus(new EmptyBonus(), 4);
        tile = new RewardTile(bonuses);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail() throws Exception {
        new RewardTile(null);
    }

    @Test
    public void testGetState() throws Exception {
        tile.getState();
    }

    @Test
    public void testApply() throws Exception {
        NobilityCell nobilityCell = NobilityCell.createNobilityCell(10, null, new EmptyBonus());
        NobilityCell nobilityCell2 = NobilityCell.createNobilityCell(9, nobilityCell, new EmptyBonus());
        LinkedList<PoliticCard> cards = new LinkedList<>();
        cards.add(new PoliticCard(new PoliticColor("aaaa")));
        LinkedList<Assistant> assistants = new LinkedList<>();
        assistants.add(new Assistant());
        Player player = new Player(1234, "name", nobilityCell2, 10, cards, assistants);
        tile.apply(player);
        assertEquals(player.getAssistantsNumber(), 5);
    }
}