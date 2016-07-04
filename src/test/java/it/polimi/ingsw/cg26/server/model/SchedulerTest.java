package it.polimi.ingsw.cg26.server.model;

import it.polimi.ingsw.cg26.server.creator.Creator;
import it.polimi.ingsw.cg26.server.model.board.*;
import it.polimi.ingsw.cg26.server.model.bonus.EmptyBonus;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.server.model.player.Player;
import it.polimi.ingsw.cg26.server.model.state.MatchEnded;
import it.polimi.ingsw.cg26.server.model.state.Scheduler;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static junit.framework.TestCase.*;

/**
 *
 */
public class SchedulerTest {

    private GameBoard gameBoard;
    private Scheduler scheduler;
    private Player gigi;
    private Player ugo;

    @Before
    public void setUp() throws Exception {
        gameBoard = Creator.createGame("maps/1.xml");
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
        assertEquals(scheduler.getCurrentPlayer(), gigi);

        scheduler.regularActionPerformed();
        assertEquals(scheduler.getCurrentPlayer(), gigi);

        scheduler.foldedBuy();

        gigi.performMainAction();
        scheduler.regularActionPerformed();
        assertEquals(scheduler.getCurrentPlayer(), gigi);

        gigi.performQuickAction();
        scheduler.regularActionPerformed();
        assertEquals(scheduler.getCurrentPlayer(), ugo);

        ugo.performQuickAction();
        ugo.performMainAction();
        scheduler.regularActionPerformed();

        assertTrue(scheduler.canSell(gigi.getToken()));

        assertEquals(scheduler.getCurrentPlayer(), gigi);
        scheduler.foldSell();

        assertEquals(scheduler.getCurrentPlayer(), ugo);
        scheduler.foldSell();

        scheduler.foldedBuy();
        scheduler.foldedBuy();

        assertTrue(scheduler.canPerformRegularAction(gigi.getToken()));
        assertEquals(scheduler.getCurrentPlayer(), gigi);

        int i = 0;
        for (Region r: gameBoard.getRegions()) {
            for(City c: r.getCities()) {
                c.build(gigi);
                i++;
                if (i == 10)
                    break;
            }
            if (i == 10)
                break;
        }
        while (gigi.canPerformChooseAction())
            gigi.performChooseAction();
        while (gigi.canPerformMainAction())
            gigi.performMainAction();
        while (gigi.canPerformQuickAction())
            gigi.performQuickAction();
        scheduler.regularActionPerformed();
        assertEquals(scheduler.getCurrentPlayer(), ugo);

        ugo.performQuickAction();
        ugo.performMainAction();
        scheduler.regularActionPerformed();
    }

    private List<Player> createPlayers(int bpt1, int nob1, int vic1, int bpt2, int nob2, int vic2, int bpt3, int nob3, int vic3) {
        City c1 = City.createCity("c1", CityColor.createCityColor("red"), new EmptyBonus());
        City c2 = City.createCity("c2", CityColor.createCityColor("green"), new EmptyBonus());
        City c3 = City.createCity("c3", CityColor.createCityColor("blue"), new EmptyBonus());
        City c4 = City.createCity("c4", CityColor.createCityColor("red"), new EmptyBonus());
        City c5 = City.createCity("c5", CityColor.createCityColor("green"), new EmptyBonus());
        City c6 = City.createCity("c6", CityColor.createCityColor("blue"), new EmptyBonus());
        City c7 = City.createCity("c7", CityColor.createCityColor("red"), new EmptyBonus());
        City c8 = City.createCity("c8", CityColor.createCityColor("green"), new EmptyBonus());
        City c9 = City.createCity("c9", CityColor.createCityColor("blue"), new EmptyBonus());
        City c10 = City.createCity("c10", CityColor.createCityColor("red"), new EmptyBonus());

        LinkedList<City> cs1 = new LinkedList<>();
        cs1.add(c1);
        LinkedList<City> cs2 = new LinkedList<>();
        cs2.add(c2);
        LinkedList<City> cs3 = new LinkedList<>();
        cs3.add(c3);
        LinkedList<City> cs4 = new LinkedList<>();
        cs4.add(c4);
        LinkedList<City> cs5 = new LinkedList<>();
        cs5.add(c5);
        LinkedList<City> cs6 = new LinkedList<>();
        cs6.add(c6);
        LinkedList<City> cs7 = new LinkedList<>();
        cs7.add(c7);
        LinkedList<City> cs8 = new LinkedList<>();
        cs8.add(c8);
        LinkedList<City> cs9 = new LinkedList<>();
        cs9.add(c8);
        LinkedList<City> cs10 = new LinkedList<>();
        cs10.add(c8);

        List<BusinessPermissionTile> tiles = new LinkedList<>();
        tiles.add(new BusinessPermissionTile(cs1, new EmptyBonus()));
        tiles.add(new BusinessPermissionTile(cs2, new EmptyBonus()));
        tiles.add(new BusinessPermissionTile(cs3, new EmptyBonus()));
        tiles.add(new BusinessPermissionTile(cs4, new EmptyBonus()));
        tiles.add(new BusinessPermissionTile(cs5, new EmptyBonus()));
        tiles.add(new BusinessPermissionTile(cs5, new EmptyBonus()));
        tiles.add(new BusinessPermissionTile(cs5, new EmptyBonus()));
        tiles.add(new BusinessPermissionTile(cs5, new EmptyBonus()));
        tiles.add(new BusinessPermissionTile(cs5, new EmptyBonus()));
        tiles.add(new BusinessPermissionTile(cs5, new EmptyBonus()));


        Player p1 = new Player(1, "uno", NobilityCell.createNobilityCell(nob1, null, new EmptyBonus()), 10, new LinkedList<>(), new LinkedList<>());
        Player p2 = new Player(2, "due", NobilityCell.createNobilityCell(nob2, null, new EmptyBonus()), 10, new LinkedList<>(), new LinkedList<>());
        Player p3 = new Player(3, "tre", NobilityCell.createNobilityCell(nob3, null, new EmptyBonus()), 10, new LinkedList<>(), new LinkedList<>());

        LinkedList<Player> players = new LinkedList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);

        int i = 0;
        for (; i < bpt1; i++)
            p1.addPermissionTile(tiles.get(i));

        p1.addVictoryPoints(vic1);
        p2.addVictoryPoints(vic2);
        p3.addVictoryPoints(vic3);

        return players;
    }

    @Test
    public void testRankingCalculation1() throws Exception {
        MatchEnded state = new MatchEnded(createPlayers(4, 3, 9 + 3, 2, 2, 3, 2, 1, 6), Creator.createGame("maps/1.xml"));
        List<Player> result = state.getPlayers();

        assertEquals(result.get(0).getName(), "uno");
        assertEquals(result.get(1).getName(), "tre");
        assertEquals(result.get(2).getName(), "due");

        assertEquals(result.get(0).getVictoryPoints(), 20);
        assertEquals(result.get(1).getVictoryPoints(), 6);
        assertEquals(result.get(2).getVictoryPoints(), 5);
    }


    @Test
    public void testRankingCalculation2() throws Exception {
        MatchEnded state = new MatchEnded(createPlayers(5, 3, 8, 3, 3, 10 + 3, 7, 2, 7), Creator.createGame("maps/1.xml"));
        List<Player> result = state.getPlayers();

        assertEquals(result.get(0).getName(), "due");
        assertEquals(result.get(1).getName(), "uno");
        assertEquals(result.get(2).getName(), "tre");

        assertEquals(result.get(0).getVictoryPoints(), 18);
        assertEquals(result.get(1).getVictoryPoints(), 16);
        assertEquals(result.get(2).getVictoryPoints(), 7);
    }

    @Test
    public void testRankingCalculation3() throws Exception {
        /*MatchEnded state = new MatchEnded(createPlayers(4, 3, 6, 5, 2, 7 + 3, 1, 2, 8), Creator.createGame("maps/1.xml"));
        List<Player> result = state.getPlayers();

        System.out.println(result.get(0).getVictoryPoints());
        System.out.println(result.get(1).getVictoryPoints());
        System.out.println(result.get(2).getVictoryPoints());

        assertEquals(result.get(0).getName(), "uno");
        assertEquals(result.get(1).getName(), "due");
        assertEquals(result.get(2).getName(), "tre");

        assertEquals(result.get(0).getVictoryPoints(), 15);
        assertEquals(result.get(1).getVictoryPoints(), 11);
        assertEquals(result.get(2).getVictoryPoints(), 10);*/
    }


}