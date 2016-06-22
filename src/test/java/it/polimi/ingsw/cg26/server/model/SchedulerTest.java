package it.polimi.ingsw.cg26.server.model;

import it.polimi.ingsw.cg26.server.creator.Creator;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 *
 */
public class SchedulerTest {

    private Scheduler scheduler;
    private Player gigi;
    private Player ugo;

    @Before
    public void setUp() throws Exception {
        GameBoard gameBoard = Creator.createGame("maps/1.xml");
        scheduler = gameBoard.getScheduler();
        scheduler.registerPlayer("Gigi");
        scheduler.registerPlayer("Ugo");
        scheduler.start();
        gigi = scheduler.getPlayers().get(0);
        ugo = scheduler.getPlayers().get(1);
    }

    @Test (expected=NullPointerException.class)
    public void testShouldNotCreateSchedulerIfGameboardIsNull()throws Exception {
        new Scheduler(null);
    }


    @Test
    public void testRegisterPlayer() throws Exception {
    	scheduler.registerPlayer("Luca");
    	assertEquals(scheduler.getPlayers().get(2).getName(), "Luca");

    }

    @Test
    public void testGetPlayersNumber() throws Exception {
    	assertEquals(scheduler.playersNumber(), 2 );

    }

    @Test
    public void testKillPlayer() throws Exception {
        scheduler.killPlayer(ugo.getToken());
        assertEquals(scheduler.playersNumber(), 1);
        assertEquals(scheduler.getPlayers().get(0), gigi);
    }


    @Test
    public void testDeactivatePlayer() throws Exception {
        scheduler.deactivatePlayer(ugo.getToken());
        assertFalse(scheduler.getPlayers().get(1).isOnline());
    }

    @Test
    public void testGetPlayersState() throws Exception {

    }

    @Test
    public void testGetPlayersFullState() throws Exception {

    }

    @Test
    public void testGetCurrentPlayer() throws Exception {
        assertEquals(scheduler.getCurrentPlayer().getName(), gigi.getName());

        scheduler.regularActionPerformed();
        assertEquals(scheduler.getCurrentPlayer().getName(), gigi.getName());

        scheduler.foldedBuy();

        gigi.performMainAction();
        scheduler.regularActionPerformed();
        assertEquals(scheduler.getCurrentPlayer().getName(), gigi.getName());

        gigi.performQuickAction();
        scheduler.regularActionPerformed();
        assertEquals(scheduler.getCurrentPlayer().getName(), ugo.getName());

        ugo.performQuickAction();
        ugo.performMainAction();
        scheduler.regularActionPerformed();

        assertTrue(scheduler.canSell(gigi.getToken()));

        assertEquals(scheduler.getCurrentPlayer().getName(), gigi.getName());
        scheduler.foldSell();

        assertEquals(scheduler.getCurrentPlayer().getName(), ugo.getName());
        scheduler.foldSell();

        scheduler.foldedBuy();
        scheduler.foldedBuy();

        assertTrue(scheduler.canPerformRegularAction(gigi.getToken()));
        assertEquals(scheduler.getCurrentPlayer().getName(), gigi.getName());
    }
}