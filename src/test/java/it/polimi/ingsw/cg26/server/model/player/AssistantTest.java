package it.polimi.ingsw.cg26.server.model.player;

import it.polimi.ingsw.cg26.server.model.board.NobilityCell;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 *
 */
public class AssistantTest {

    private Assistant assistant;

    @Before
    public void setUp() throws Exception {
        assistant = new Assistant();
    }

    @Test (expected = NullPointerException.class)
    public void testBackToOwnerShouldThrowNullPointerException() throws Exception {
        assistant.backToOwner();
    }

    @Test
    public void testBackToOwnerShouldWork() throws Exception {
        NobilityCell nobilityCell = NobilityCell.createNobilityCell(10, null, new LinkedList<Bonus>());
        NobilityCell nobilityCell2 = NobilityCell.createNobilityCell(9, nobilityCell, new LinkedList<Bonus>());
        LinkedList<PoliticCard> cards = new LinkedList<>();
        LinkedList<Assistant> assistants = new LinkedList<>();
        Player player = new Player(1234, "name", nobilityCell2, 10, cards, assistants);

        assistant.setOwner(player);
        assistant.backToOwner();

        assertEquals(player.getAssistantsNumber(), 1);
    }

    @Test
    public void testGetState() throws Exception {
        assistant.getState();
    }

}