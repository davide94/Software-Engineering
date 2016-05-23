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
public class PlayerTest {

    private Player player;

    @Before
    public void setUp() throws Exception {
        NobilityCell nobilityCell = NobilityCell.createNobilityCell(10, null, new LinkedList<Bonus>());
        NobilityCell nobilityCell2 = NobilityCell.createNobilityCell(9, nobilityCell, new LinkedList<Bonus>());
        LinkedList<PoliticCard> cards = new LinkedList<>();
        LinkedList<Assistant> assistants = new LinkedList<>();
        player = new Player(1234, "name", nobilityCell2, 10, cards, assistants);
    }

    @Test
    public void testGetState() throws Exception {

    }

    @Test
    public void testGetName() throws Exception {
        assertEquals(player.getName(), "name");
    }

    @Test
    public void testGetToken() throws Exception {
        assertEquals(player.getToken(), 1234);
    }

    @Test
    public void testCanPerformMainAction() throws Exception {
        player.setRemainingMainActions(0);
        assertFalse(player.canPerformMainAction());
        player.setRemainingMainActions(1);
        assertTrue(player.canPerformMainAction());
    }

    @Test
    public void testCanPerformQuickAction() throws Exception {
        player.setRemainingQuickActions(0);
        assertFalse(player.canPerformQuickAction());
        player.setRemainingQuickActions(1);
        assertTrue(player.canPerformQuickAction());
    }

    @Test
    public void testPerformMainAction() throws Exception {
        player.setRemainingMainActions(1);
        player.performMainAction();
        assertFalse(player.canPerformMainAction());
    }

    @Test
    public void testPerformQuickAction() throws Exception {
        player.setRemainingQuickActions(1);
        player.performQuickAction();
        assertFalse(player.canPerformQuickAction());
    }

    @Test
    public void testSetRemainingMainActions() throws Exception {
        player.setRemainingMainActions(0);
        assertFalse(player.canPerformMainAction());
        player.setRemainingMainActions(1);
        assertTrue(player.canPerformMainAction());
    }

    @Test
    public void testSetRemainingQuickActions() throws Exception {
        player.setRemainingQuickActions(0);
        assertFalse(player.canPerformQuickAction());
        player.setRemainingQuickActions(1);
        assertTrue(player.canPerformQuickAction());
    }

    @Test
    public void testAddRemainingMainActions() throws Exception {
        player.setRemainingMainActions(0);
        player.addRemainingMainActions(0);
        assertFalse(player.canPerformMainAction());
        player.addRemainingMainActions(1);
        assertTrue(player.canPerformMainAction());
    }

    @Test
    public void testAddRemainingQuickActions() throws Exception {
        player.setRemainingQuickActions(0);
        player.addRemainingQuickActions(0);
        assertFalse(player.canPerformQuickAction());
        player.addRemainingQuickActions(1);
        assertTrue(player.canPerformQuickAction());
    }

    @Test
    public void testIncrementNobility() throws Exception {
        player.incrementNobility();
        assertEquals(player.getNobilityCell().getIndex(), 10);
    }

    @Test
    public void testGetNobilityCell() throws Exception {
        assertEquals(player.getNobilityCell().getIndex(), 9);
    }

    @Test
    public void testGetAssistantsNumber() throws Exception {

    }

    @Test
    public void testAddConqueredCity() throws Exception {

    }

    @Test
    public void testGetConqueredCities() throws Exception {

    }

    @Test
    public void testTakeAssistants() throws Exception {

    }

    @Test
    public void testAddAssistant() throws Exception {

    }

    @Test
    public void testAddCoins() throws Exception {

    }

    @Test
    public void testRemoveCoins() throws Exception {

    }

    @Test
    public void testAddVictoryPoints() throws Exception {

    }

    @Test
    public void testAddPoliticCard() throws Exception {

    }

    @Test
    public void testHasPermissionTile() throws Exception {

    }

    @Test
    public void testUseBPT() throws Exception {

    }

    @Test
    public void testAddPermissionTile() throws Exception {

    }

    @Test
    public void testHasCards() throws Exception {

    }

    @Test
    public void testTakeCards() throws Exception {

    }

    @Test
    public void testTakeCard() throws Exception {

    }

    @Test
    public void testGetCoinsNumber() throws Exception {

    }
}