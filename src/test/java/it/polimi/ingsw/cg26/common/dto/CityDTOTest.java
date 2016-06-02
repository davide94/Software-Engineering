package it.polimi.ingsw.cg26.common.dto;

import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.EmptyBonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.NobilityBonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.VictoryBonusDTO;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 *
 */
public class CityDTOTest {

    private CityDTO city;

    private CityColorDTO color;
    private BonusDTO bonuses;
    private LinkedList<EmporiumDTO> emporiums;
    private LinkedList<String> nearCities;

    @Before
    public void setUp() throws Exception {
        color = new CityColorDTO("colorName");
        bonuses = new VictoryBonusDTO(new EmptyBonusDTO(), 4);
        emporiums = new LinkedList<>();
        emporiums.add(new EmporiumDTO("aPlayerName"));
        nearCities = new LinkedList<>();
        nearCities.add("aNearCityName");
        city = new CityDTO("cityName", color, bonuses, emporiums, nearCities);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail1() throws Exception {
        new CityDTO(null, color, bonuses, emporiums, nearCities);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail2() throws Exception {
        new CityDTO("cityName", null, bonuses, emporiums, nearCities);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail3() throws Exception {
        new CityDTO("cityName", color, null, emporiums, nearCities);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail4() throws Exception {
        new CityDTO("cityName", color, bonuses, null, nearCities);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldFail5() throws Exception {
        new CityDTO("cityName", color, bonuses, emporiums, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorShouldFail6() throws Exception {
        new CityDTO("", color, bonuses, emporiums, nearCities);
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
        LinkedList<EmporiumDTO> e = new LinkedList<>();
        e.add(new EmporiumDTO("player1Name"));
        e.add(new EmporiumDTO("player2Name"));
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
        BonusDTO b = new NobilityBonusDTO(new EmptyBonusDTO(), 5);
        LinkedList<EmporiumDTO> e = new LinkedList<>();
        e.add(new EmporiumDTO("otherPlayerName"));
        LinkedList<String> c = new LinkedList<>();
        c.add("otherNearCityName");
        assertTrue(city.equals(city));
        assertTrue(city.equals(new CityDTO("cityName", color, bonuses, emporiums, nearCities)));
        assertFalse(city.equals(new CityDTO("otherCityName", color, bonuses, emporiums, nearCities)));
        assertFalse(city.equals(new CityDTO("cityName", new CityColorDTO("otherColorName"), bonuses, emporiums, nearCities)));
        assertFalse(city.equals(new CityDTO("cityName", color, b, emporiums, nearCities)));
        assertFalse(city.equals(new CityDTO("cityName", color, bonuses, e, nearCities)));
        assertFalse(city.equals(new CityDTO("cityName", color, bonuses, emporiums, c)));
        assertFalse(city.equals(null));
        assertFalse(city.equals(new Assistant()));
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(city.hashCode(), new CityDTO("cityName", color, bonuses, emporiums, nearCities).hashCode());
    }
}