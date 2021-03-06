package it.polimi.ingsw.cg26.server.model.player;

import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.common.dto.PoliticColorDTO;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCardsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.server.model.board.City;
import it.polimi.ingsw.cg26.server.model.board.CityColor;
import it.polimi.ingsw.cg26.server.model.board.NobilityCell;
import it.polimi.ingsw.cg26.server.model.bonus.AssistantBonus;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.bonus.EmptyBonus;
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
    private NobilityCell nobilityCell;
    private LinkedList<PoliticCard> cards;
    private LinkedList<Assistant> assistants;

    @Before
    public void setUp() throws Exception {
        NobilityCell nobilityCell1 = NobilityCell.createNobilityCell(10, null, new EmptyBonus());
        nobilityCell = NobilityCell.createNobilityCell(9, nobilityCell1, new EmptyBonus());
        cards = new LinkedList<>();
        cards.add(new PoliticCard(new PoliticColor("aaaa")));
        assistants = new LinkedList<>();
        assistants.add(new Assistant());
        player = new Player(1234, "name", nobilityCell, 10, cards, assistants);
        player.setRemainingMainActions(1);
        player.setRemainingQuickActions(1);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldThrowNullPointerException1() throws Exception {
        new Player(1, null, nobilityCell, 1, cards, assistants);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldThrowNullPointerException2() throws Exception {
        new Player(1, "", null, 1, cards, assistants);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldThrowNullPointerException3() throws Exception {
        new Player(1, "", nobilityCell, 1, null, assistants);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldThrowNullPointerException4() throws Exception {
        new Player(1, "", nobilityCell, 1, cards, null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorShouldThrowIllegalArgumentException() throws Exception {
        NobilityCell nobilityCell = NobilityCell.createNobilityCell(10, null, new EmptyBonus());
        NobilityCell nobilityCell2 = NobilityCell.createNobilityCell(9, nobilityCell, new EmptyBonus());
        LinkedList<PoliticCard> cards = new LinkedList<>();
        LinkedList<Assistant> assistants = new LinkedList<>();
        player = new Player(1234, "name", nobilityCell2, -10, cards, assistants);
    }

    @Test
    public void testGetState() throws Exception {
        Player player2 = new Player(1234, "name", nobilityCell, 10, cards, assistants);
        player2.setRemainingMainActions(1);
        player2.setRemainingQuickActions(1);
        assertEquals(player.getState().getName(), player2.getState().getName());
        assertEquals(player.getState().getAssistantsNumber(), player2.getState().getAssistantsNumber());
        assertEquals(player.getState().getTiles(), player2.getState().getTiles());
        assertEquals(player.getState().getCards(), player2.getState().getCards());
        assertEquals(player.getState().getCoins(), player2.getState().getCoins());
        assertEquals(player.getState().getDiscardedTiles(), player2.getState().getDiscardedTiles());
        assertEquals(player.getState().getNobilityCell(), player2.getState().getNobilityCell());
        assertEquals(player.getState().getRemainingMainActions(), player2.getState().getRemainingMainActions());
        assertEquals(player.getState().getRemainingQuickActions(), player2.getState().getRemainingQuickActions());
        assertEquals(player.getState().getToken(), player2.getState().getToken());
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
        Collection<PoliticCardDTO> cards = new LinkedList<>();
        cards.add(card.getState());
        assertTrue(player.hasCards(cards));
    }

    @Test
    public void testHasPermissionTile() throws Exception {
        LinkedList<City> cities = new LinkedList<>();
        City city = City.createCity("cityname", CityColor.createCityColor("colorname"), new EmptyBonus());
        cities.add(city);
        Bonus bonuses = new AssistantBonus(new EmptyBonus(), 4);
        BusinessPermissionTile tile = new BusinessPermissionTile(cities, bonuses);
        player.addPermissionTile(tile);
        assertEquals(player.hasPermissionTile(tile.getState()), tile);
    }

    @Test
    public void testGetRealBPT() throws Exception {
        LinkedList<City> cities = new LinkedList<>();
        City city = City.createCity("cityname", CityColor.createCityColor("colorname"), new EmptyBonus());
        cities.add(city);
        Bonus bonuses = new AssistantBonus(new EmptyBonus(), 4);
        BusinessPermissionTile tile = new BusinessPermissionTile(cities,  bonuses);
        player.addPermissionTile(tile);
        assertEquals(player.removeRealBPT(tile.getState()), tile);
    }

    @Test
    public void testUseBPT() throws Exception {
        LinkedList<City> cities = new LinkedList<>();
        City city = City.createCity("cityname", CityColor.createCityColor("colorname"), new EmptyBonus());
        cities.add(city);
        Bonus bonuses = new AssistantBonus(new EmptyBonus(), 4);
        BusinessPermissionTile tile = new BusinessPermissionTile(cities, bonuses);
        player.addPermissionTile(tile);
        player.useBPT(tile);
    }

    @Test
    public void testAddPermissionTile() throws Exception {
        LinkedList<City> cities = new LinkedList<>();
        City city = City.createCity("cityname", CityColor.createCityColor("colorname"), new EmptyBonus());
        cities.add(city);
        Bonus bonuses = new AssistantBonus(new EmptyBonus(), 4);
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

        LinkedList<PoliticCardDTO> cards1 = new LinkedList<>();
        cards1.add(card1.getState());
        cards1.add(card2.getState());
        assertTrue(player.hasCards(cards1));

        LinkedList<PoliticCardDTO> cards2 = new LinkedList<>();
        cards2.add(card1.getState());
        assertTrue(player.hasCards(cards2));

        LinkedList<PoliticCardDTO> cards3 = new LinkedList<>();
        cards3.add(card3.getState());
        assertFalse(player.hasCards(cards3));

        LinkedList<PoliticCardDTO> cards4 = new LinkedList<>();
        cards4.add(card1.getState());
        cards4.add(card3.getState());
        assertFalse(player.hasCards(cards4));
    }

    @Test (expected = InvalidCardsException.class)
    public void testTakeCardsShouldThrowInvalidCardsException1() throws Exception {
        LinkedList<PoliticCardDTO> cards = new LinkedList<>();
        cards.add(new PoliticCardDTO(new PoliticColorDTO("colorname"), 0, "giocatore"));
        player.takeCards(cards);
    }

    @Test (expected = InvalidCardsException.class)
    public void testTakeCardsShouldThrowInvalidCardsException2() throws Exception {
        player.takeCard(new PoliticCardDTO(new PoliticColorDTO("colorname"), 0, "giocatore"));
    }

    @Test
    public void testTakeCards() throws Exception {
        PoliticCard card1 = new PoliticCard(new PoliticColor("color1"));
        PoliticCard card2 = new PoliticCard(new PoliticColor("color2"));
        PoliticCard card3 = new PoliticCard(new PoliticColor("color3"));

        player.addPoliticCard(card1);
        player.addPoliticCard(card2);

        Collection<PoliticCardDTO> required = new LinkedList<>();
        required.add(card1.getState());

        Collection<PoliticCard> expected = new LinkedList<>();
        expected.add(card1);

        assertEquals(player.takeCards(required), expected);
    }

    @Test
    public void testGetCoinsNumber() throws Exception {
        assertEquals(player.getCoinsNumber(), 10);
    }
}