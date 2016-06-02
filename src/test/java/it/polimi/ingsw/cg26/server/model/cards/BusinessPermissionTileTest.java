package it.polimi.ingsw.cg26.server.model.cards;

import it.polimi.ingsw.cg26.server.model.board.City;
import it.polimi.ingsw.cg26.server.model.board.CityColor;
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
public class BusinessPermissionTileTest {

    private BusinessPermissionTile tile;

    @Before
    public void setUp() throws Exception {
        LinkedList<City> cities = new LinkedList<>();
        City city = City.createCity("cityname", CityColor.createCityColor("colorname"), new EmptyBonus());
        cities.add(city);
        Bonus bonuses = new AssistantBonus(new EmptyBonus(), 4);
        tile = new BusinessPermissionTile(cities, bonuses);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail() throws Exception {
        new BusinessPermissionTile(null, null);
    }

    @Test
    public void testGetState() throws Exception {
        NobilityCell nobilityCell = NobilityCell.createNobilityCell(10, null, new EmptyBonus());
        NobilityCell nobilityCell2 = NobilityCell.createNobilityCell(9, nobilityCell, new EmptyBonus());
        LinkedList<PoliticCard> cards = new LinkedList<>();
        cards.add(new PoliticCard(new PoliticColor("aaaa")));
        LinkedList<Assistant> assistants = new LinkedList<>();
        assistants.add(new Assistant());

        tile.setOwner(new Player(1234, "name", nobilityCell2, 10, cards, assistants));
        tile.getState();
        // TODO
    }

    @Test
    public void testCanBuildIn() throws Exception {
        City city1 = City.createCity("cityname", CityColor.createCityColor("colorname"), new EmptyBonus());
        City city2 = City.createCity("othercityname", CityColor.createCityColor("colorname"), new EmptyBonus());
        assertTrue(tile.canBuildIn(city1.getState()));
        assertFalse(tile.canBuildIn(city2.getState()));
    }

    @Test (expected = NullPointerException.class)
    public void testBackToOwnerShouldFail() throws Exception {
        tile.backToOwner();
    }

    @Test
    public void testBackToOwnerAndSetOwnerShouldSucceed() throws Exception {
        NobilityCell nobilityCell = NobilityCell.createNobilityCell(10, null, new EmptyBonus());
        NobilityCell nobilityCell2 = NobilityCell.createNobilityCell(9, nobilityCell, new EmptyBonus());
        LinkedList<PoliticCard> cards = new LinkedList<>();
        cards.add(new PoliticCard(new PoliticColor("aaaa")));
        LinkedList<Assistant> assistants = new LinkedList<>();
        assistants.add(new Assistant());

        Player player = new Player(1234, "name", nobilityCell2, 10, cards, assistants);

        tile.setOwner(player);
        tile.backToOwner();

        assertTrue(player.hasPermissionTile(tile.getState()).equals(tile));
    }

    @Test
    public void testToString() throws Exception {
        tile.toString();
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(tile.hashCode(), tile.hashCode());
    }

    @Test
    public void testEquals() throws Exception {
        LinkedList<City> cities = new LinkedList<>();
        City city = City.createCity("cityname", CityColor.createCityColor("colorname"), new EmptyBonus());
        cities.add(city);
        Bonus bonuses = new AssistantBonus(new EmptyBonus(), 4);

        assertTrue(tile.equals(new BusinessPermissionTile(cities, bonuses)));
        assertTrue(tile.equals(tile));
        assertFalse(tile.equals(null));
        assertFalse(tile.equals(new PoliticColor("red")));
    }
}