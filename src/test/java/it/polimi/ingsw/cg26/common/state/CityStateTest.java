package it.polimi.ingsw.cg26.common.state;

import it.polimi.ingsw.cg26.server.model.player.Assistant;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 *
 */
public class CityStateTest {

    private CityState city;

    private CityColorState color;
    private LinkedList<BonusState> bonuses;
    private LinkedList<EmporiumState> emporiums;
    private LinkedList<String> nearCities;

    @Before
    public void setUp() throws Exception {
        color = new CityColorState("colorName");
        bonuses = new LinkedList<>();
        bonuses.add(new BonusState("bonus1Name", 3));
        emporiums = new LinkedList<>();
        emporiums.add(new EmporiumState("aPlayerName"));
        nearCities = new LinkedList<>();
        nearCities.add("aNearCityName");
        city = new CityState("cityName", color, bonuses, emporiums, nearCities);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail1() throws Exception {
        new CityState(null, color, bonuses, emporiums, nearCities);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail2() throws Exception {
        new CityState("cityName", null, bonuses, emporiums, nearCities);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail3() throws Exception {
        new CityState("cityName", color, null, emporiums, nearCities);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail4() throws Exception {
        new CityState("cityName", color, bonuses, null, nearCities);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail5() throws Exception {
        new CityState("cityName", color, bonuses, emporiums, null);
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals(city.getName(), "cityName");
    }

    @Test
    public void testGetColor() throws Exception {
        assertEquals(city.getColor(), color);
    }

    @Test
    public void testGetEmporiums() throws Exception {
        assertEquals(city.getEmporiums(), emporiums);
    }

    @Test (expected = NullPointerException.class)
    public void testSetEmporiumsShouldFail() throws Exception {
        city.setEmporiums(null);
    }

    @Test
    public void testSetEmporiums() throws Exception {
        LinkedList<EmporiumState> e = new LinkedList<>();
        e.add(new EmporiumState("player1Name"));
        e.add(new EmporiumState("player2Name"));
        city.setEmporiums(e);
        assertEquals(city.getEmporiums(), e);
    }

    @Test
    public void testGetBonuses() throws Exception {
        assertEquals(city.getBonuses(), bonuses);
    }

    @Test
    public void testGetNearCities() throws Exception {
        assertEquals(city.getNearCities(), nearCities);
    }

    @Test
    public void testToString() throws Exception {
        city.toString();
    }

    @Test
    public void testEquals() throws Exception {
        LinkedList<BonusState> b = new LinkedList<>();
        b.add(new BonusState("bonus2Name", 3));
        LinkedList<EmporiumState> e = new LinkedList<>();
        e.add(new EmporiumState("otherPlayerName"));
        LinkedList<String> c = new LinkedList<>();
        c.add("otherNearCityName");
        assertTrue(city.equals(city));
        assertTrue(city.equals(new CityState("cityName", color, bonuses, emporiums, nearCities)));
        assertFalse(city.equals(new CityState("otherCityName", color, bonuses, emporiums, nearCities)));
        assertFalse(city.equals(new CityState("cityName", new CityColorState("otherColorName"), bonuses, emporiums, nearCities)));
        assertFalse(city.equals(new CityState("cityName", color, b, emporiums, nearCities)));
        assertFalse(city.equals(new CityState("cityName", color, bonuses, e, nearCities)));
        assertFalse(city.equals(new CityState("cityName", color, bonuses, emporiums, c)));
        assertFalse(city.equals(null));
        assertFalse(city.equals(new Assistant()));
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(city.hashCode(), new CityState("cityName", color, bonuses, emporiums, nearCities).hashCode());
    }
}