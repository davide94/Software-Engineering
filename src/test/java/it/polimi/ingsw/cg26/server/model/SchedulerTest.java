package it.polimi.ingsw.cg26.server.model;

import it.polimi.ingsw.cg26.server.creator.Creator;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class SchedulerTest {

    private Scheduler scheduler;
    private Player gigi;
    private Player ugo;

    @Before
    public void setUp() throws Exception {
        GameBoard gameBoard = Creator.createGame("src/main/resources/config.xml");
        scheduler = gameBoard.getScheduler();
        scheduler.registerPlayer("Gigi");
        scheduler.registerPlayer("Ugo");
        scheduler.initPlayers();
        gigi = scheduler.getPlayers().get(0);
        ugo = scheduler.getPlayers().get(1);
    }

    @Test
    public void getPlayersState() throws Exception {

    }

    @Test
    public void getPlayersFullState() throws Exception {

    }
/*
    @Test
    public void getCurrentPlayer() throws Exception {

        assertEquals(scheduler.getCurrentPlayer().getName(), gigi.getName());

        //scheduler.regularActionPerformed();
        assertEquals(scheduler.getCurrentPlayer().getName(), gigi.getName());

        scheduler.foldedBuy();

        gigi.performMainAction();
        //scheduler.regularActionPerformed();
        assertEquals(scheduler.getCurrentPlayer().getName(), gigi.getName());

        gigi.performQuickAction();
        //scheduler.regularActionPerformed();
        assertEquals(scheduler.getCurrentPlayer().getName(), ugo.getName());

        ugo.performQuickAction();
        ugo.performMainAction();
        //scheduler.actionPerformed();
        //assertTrue(scheduler.isMarket());

        assertEquals(scheduler.getCurrentPlayer().getName(), gigi.getName());
        scheduler.foldSell();

        assertEquals(scheduler.getCurrentPlayer().getName(), ugo.getName());
        scheduler.foldSell();

        scheduler.foldedBuy();
        scheduler.foldedBuy();

        //assertFalse(scheduler.isMarket());
        assertEquals(scheduler.getCurrentPlayer().getName(), gigi.getName());
    }

    @Test
    public void actionPerformed() throws Exception {

    }

    @Test
    public void foldSell() throws Exception {

    }

    @Test
    public void foldBuy() throws Exception {

    }
*/
}