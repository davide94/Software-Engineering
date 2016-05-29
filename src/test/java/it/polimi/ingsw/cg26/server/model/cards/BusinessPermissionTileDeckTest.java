package it.polimi.ingsw.cg26.server.model.cards;

import it.polimi.ingsw.cg26.server.model.board.City;
import it.polimi.ingsw.cg26.server.model.board.CityColor;
import it.polimi.ingsw.cg26.server.model.bonus.AssistantBonus;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 *
 */
public class BusinessPermissionTileDeckTest {

    BusinessPermissionTileDeck deck;

    @Before
    public void setUp() throws Exception {
        LinkedList<City> cities = new LinkedList<>();
        City city = City.createCity("city1name", CityColor.createCityColor("colorname"), new RewardTile(new LinkedList<Bonus>()));
        cities.add(city);
        LinkedList<Bonus> bonuses = new LinkedList<>();
        Bonus bonus = new AssistantBonus(4);
        bonuses.add(bonus);
        BusinessPermissionTile tile1 = new BusinessPermissionTile(cities, new RewardTile(bonuses));

        cities = new LinkedList<>();
        city = City.createCity("city2name", CityColor.createCityColor("colorname"), new RewardTile(new LinkedList<Bonus>()));
        cities.add(city);
        bonuses = new LinkedList<>();
        bonus = new AssistantBonus(4);
        bonuses.add(bonus);
        BusinessPermissionTile tile2 = new BusinessPermissionTile(cities, new RewardTile(bonuses));

        LinkedList<BusinessPermissionTile> tiles = new LinkedList<>();
        tiles.add(tile1);
        tiles.add(tile2);

        deck = new BusinessPermissionTileDeck(tiles);
    }

    @Test
    public void testGetState() throws Exception {
        deck.getState();
    }

    @Test (expected = IllegalArgumentException.class)
    public void testDrawShouldFail() throws Exception {
        deck.draw(-2);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testDrawShouldFail2() throws Exception {
        deck.draw(16);
    }

    @Test
    public void testDraw() throws Exception {
        LinkedList<City> cities = new LinkedList<>();
        City city = City.createCity("city1name", CityColor.createCityColor("colorname"), new RewardTile(new LinkedList<Bonus>()));
        cities.add(city);
        LinkedList<Bonus> bonuses = new LinkedList<>();
        Bonus bonus = new AssistantBonus(4);
        bonuses.add(bonus);
        BusinessPermissionTile tile = new BusinessPermissionTile(cities, new RewardTile(bonuses));

        assertEquals(deck.draw(), tile);
    }

    @Test
    public void testDraw2() throws Exception {
        LinkedList<City> cities = new LinkedList<>();
        City city = City.createCity("city2name", CityColor.createCityColor("colorname"), new RewardTile(new LinkedList<Bonus>()));
        cities.add(city);
        LinkedList<Bonus> bonuses = new LinkedList<>();
        Bonus bonus = new AssistantBonus(4);
        bonuses.add(bonus);
        BusinessPermissionTile tile = new BusinessPermissionTile(cities, new RewardTile(bonuses));

        assertEquals(deck.draw(1), tile);
    }

    @Test
    public void testChange() throws Exception {
        LinkedList<City> cities = new LinkedList<>();
        City city = City.createCity("city1name", CityColor.createCityColor("colorname"), new RewardTile(new LinkedList<Bonus>()));
        cities.add(city);
        LinkedList<Bonus> bonuses = new LinkedList<>();
        Bonus bonus = new AssistantBonus(4);
        bonuses.add(bonus);
        BusinessPermissionTile tile = new BusinessPermissionTile(cities, new RewardTile(bonuses));

        deck.add(tile);
        deck.change();
        assertEquals(deck.draw(), tile);
    }

    @Test
    public void testToString() throws Exception {
        deck.toString();
    }
}