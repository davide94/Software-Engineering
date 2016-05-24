package it.polimi.ingsw.cg26.server.model.player;

import it.polimi.ingsw.cg26.common.state.PoliticCardState;
import it.polimi.ingsw.cg26.common.state.PoliticColorState;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCardsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.server.model.board.City;
import it.polimi.ingsw.cg26.server.model.board.CityColor;
import it.polimi.ingsw.cg26.server.model.board.NobilityCell;
import it.polimi.ingsw.cg26.server.model.bonus.AssistantBonus;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
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
        cards.add(new PoliticCard(new PoliticColor("aaaa")));
        LinkedList<Assistant> assistants = new LinkedList<>();
        assistants.add(new Assistant());
        player = new Player(1234, "name", nobilityCell2, 10, cards, assistants);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldThrowNullPointerException() throws Exception {
        new Player(1, "", null, 1, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorShouldThrowIllegalArgumentException() throws Exception {
        NobilityCell nobilityCell = NobilityCell.createNobilityCell(10, null, new LinkedList<Bonus>());
        NobilityCell nobilityCell2 = NobilityCell.createNobilityCell(9, nobilityCell, new LinkedList<Bonus>());
        LinkedList<PoliticCard> cards = new LinkedList<>();
        LinkedList<Assistant> assistants = new LinkedList<>();
        player = new Player(1234, "name", nobilityCell2, -10, cards, assistants);
    }

    @Test
    public void testGetState() throws Exception {
        player.getState();
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
        assertEquals(player.getAssistantsNumber(), 1);
    }

    @Test(expected = NoRemainingAssistantsException.class)
    public void testTakeAssistantsShouldThrowNoRemainingAssistantsException() throws Exception {
        player.takeAssistants(4);
    }

    @Test
    public void testTakeAssistantsShouldWork() throws Exception {
        player.takeAssistants(1);
    }

    @Test(expected = NullPointerException.class)
    public void testAddAssistantShouldThrowNullPointerException() throws Exception {
        player.addAssistant(null);
    }

    @Test
    public void testAddAssistant() throws Exception {
        player.addAssistant(new Assistant());
        assertEquals(player.getAssistantsNumber(), 2);
    }

    @Test
    public void testAddCoins() throws Exception {
        player.addCoins(5);
        assertEquals(player.getCoinsNumber(), 15);
    }

    @Test
    public void testRemoveCoins() throws Exception {
        player.removeCoins(5);
        assertEquals(player.getCoinsNumber(), 5);
    }

    @Test
    public void testAddVictoryPoints() throws Exception {
        player.addVictoryPoints(5);
        assertEquals(player.getVictoryPoints(), 5);
    }

    @Test
    public void testGetVictoryPoints() throws Exception {
        player.addVictoryPoints(5);
        assertEquals(player.getVictoryPoints(), 5);
    }
    @Test (expected = NullPointerException.class)
    public void testAddPoliticCardShouldThrowNullPointerException() throws Exception {
        player.addPoliticCard(null);
    }

    @Test
    public void testAddPoliticCard() throws Exception {
        PoliticCard card = new PoliticCard(new PoliticColor("ccc"));
        player.addPoliticCard(card);
        Collection<PoliticCardState> cards = new LinkedList<>();
        cards.add(card.getState());
        assertTrue(player.hasCards(cards));
    }

    @Test
    public void testHasPermissionTile() throws Exception {
        LinkedList<City> cities = new LinkedList<>();
        City city = City.createCity("cityname", CityColor.createCityColor("colorname"), new LinkedList<Bonus>());
        cities.add(city);
        LinkedList<Bonus> bonuses = new LinkedList<>();
        Bonus bonus = new AssistantBonus(4);
        bonuses.add(bonus);
        BusinessPermissionTile tile = new BusinessPermissionTile(cities, bonuses);
        player.addPermissionTile(tile);
        assertEquals(player.hasPermissionTile(tile.getState()), tile);
    }

    @Test
    public void testGetRealBPT() throws Exception {
        LinkedList<City> cities = new LinkedList<>();
        City city = City.createCity("cityname", CityColor.createCityColor("colorname"), new LinkedList<Bonus>());
        cities.add(city);
        LinkedList<Bonus> bonuses = new LinkedList<>();
        Bonus bonus = new AssistantBonus(4);
        bonuses.add(bonus);
        BusinessPermissionTile tile = new BusinessPermissionTile(cities, bonuses);
        player.addPermissionTile(tile);
        assertEquals(player.getRealBPT(tile.getState()), tile);
    }

    @Test
    public void testUseBPT() throws Exception {
        LinkedList<City> cities = new LinkedList<>();
        City city = City.createCity("cityname", CityColor.createCityColor("colorname"), new LinkedList<Bonus>());
        cities.add(city);
        LinkedList<Bonus> bonuses = new LinkedList<>();
        Bonus bonus = new AssistantBonus(4);
        bonuses.add(bonus);
        BusinessPermissionTile tile = new BusinessPermissionTile(cities, bonuses);
        player.addPermissionTile(tile);
        player.useBPT(tile);
    }

    @Test
    public void testAddPermissionTile() throws Exception {
        LinkedList<City> cities = new LinkedList<>();
        City city = City.createCity("cityname", CityColor.createCityColor("colorname"), new LinkedList<Bonus>());
        cities.add(city);
        LinkedList<Bonus> bonuses = new LinkedList<>();
        Bonus bonus = new AssistantBonus(4);
        bonuses.add(bonus);
        BusinessPermissionTile tile = new BusinessPermissionTile(cities, bonuses);
        player.addPermissionTile(tile);
        assertEquals(player.hasPermissionTile(tile.getState()), tile);
    }

    @Test
    public void testHasCards() throws Exception {
        PoliticCard card1 = new PoliticCard(new PoliticColor("color1"));
        PoliticCard card2 = new PoliticCard(new PoliticColor("color2"));
        PoliticCard card3 = new PoliticCard(new PoliticColor("color3"));

        player.addPoliticCard(card1);
        player.addPoliticCard(card2);

        LinkedList<PoliticCardState> cards1 = new LinkedList<>();
        cards1.add(card1.getState());
        cards1.add(card2.getState());
        assertTrue(player.hasCards(cards1));

        LinkedList<PoliticCardState> cards2 = new LinkedList<>();
        cards2.add(card1.getState());
        assertTrue(player.hasCards(cards2));

        LinkedList<PoliticCardState> cards3 = new LinkedList<>();
        cards3.add(card3.getState());
        assertFalse(player.hasCards(cards3));

        LinkedList<PoliticCardState> cards4 = new LinkedList<>();
        cards4.add(card1.getState());
        cards4.add(card3.getState());
        assertFalse(player.hasCards(cards4));
    }

    @Test (expected = InvalidCardsException.class)
    public void testTakeCardsShouldThrowInvalidCardsException() throws Exception {
        LinkedList<PoliticCardState> cards = new LinkedList<>();
        cards.add(new PoliticCardState(new PoliticColorState("colorname"), 0, "giocatore"));
        player.takeCards(cards);
    }

    @Test (expected = InvalidCardsException.class)
    public void testTakeCard() throws Exception {
        player.takeCard(new PoliticCardState(new PoliticColorState("colorname"), 0, "giocatore"));
    }

    @Test
    public void testGetCoinsNumber() throws Exception {
        assertEquals(player.getCoinsNumber(), 10);
    }
}