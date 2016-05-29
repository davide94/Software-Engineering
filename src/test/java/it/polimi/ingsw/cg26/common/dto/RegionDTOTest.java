package it.polimi.ingsw.cg26.common.dto;

import it.polimi.ingsw.cg26.server.model.cards.RewardTile;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 *
 */
public class RegionDTOTest {

    private RegionDTO region;

    private String name;
    private LinkedList<CityDTO> cities;
    private BusinessPermissionTileDeckDTO deck;
    private BalconyDTO balcony;
    private RewardTileDTO bonus;

    @Before
    public void setUp() throws Exception {
        name = "regionName";
        cities = new LinkedList<>();
        CityColorDTO color = new CityColorDTO("colorName");
        LinkedList<BonusDTO> bonuses = new LinkedList<>();
        bonuses.add(new BonusDTO("bonus1Name", 3));
        LinkedList<EmporiumDTO> emporiums = new LinkedList<>();
        emporiums.add(new EmporiumDTO("aPlayerName"));
        LinkedList<String> nearCities = new LinkedList<>();
        nearCities.add("aNearCityName");
        cities.add(new CityDTO("cityName", color, new RewardTileDTO(bonuses), emporiums, nearCities));
        deck = new BusinessPermissionTileDeckDTO(new LinkedList<>());
        balcony = new BalconyDTO(new LinkedList<>());
        bonus = new RewardTileDTO(new LinkedList<>());
        region = new RegionDTO(name, cities, deck, balcony, bonus);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail1() throws Exception {
        new RegionDTO(null, cities, deck, balcony, bonus);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail2() throws Exception {
        new RegionDTO(name, null, deck, balcony, bonus);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail3() throws Exception {
        new RegionDTO(name, cities, null, balcony, bonus);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail4() throws Exception {
        new RegionDTO(name, cities, deck, null, bonus);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail5() throws Exception {
        new RegionDTO(name, cities, deck, balcony, null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorShouldFail6() throws Exception {
        new RegionDTO("", cities, deck, balcony, bonus);
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals(region.getName(), name);
    }

    @Test
    public void testGetCities() throws Exception {
        assertEquals(region.getCities(), cities);
    }

    @Test
    public void testGetBalcony() throws Exception {
        assertEquals(region.getBalcony(), balcony);
    }

    @Test (expected = NullPointerException.class)
    public void testSetBalconyShouldFail() throws Exception {
        region.setBalcony(null);
    }

    @Test
    public void testSetBalcony() throws Exception {
        LinkedList<CouncillorDTO> councillors = new LinkedList<>();
        councillors.add(new CouncillorDTO(new PoliticColorDTO("councillorColorName")));
        BalconyDTO b = new BalconyDTO(councillors);
        region.setBalcony(b);
        assertEquals(region.getBalcony(), b);
    }

    @Test
    public void testGetDeck() throws Exception {
        assertEquals(region.getDeck(), deck);
    }

    @Test (expected = NullPointerException.class)
    public void testSetDeckShouldFail() throws Exception {
        region.setDeck(null);
    }

    @Test
    public void testSetDeck() throws Exception {
        LinkedList<BusinessPermissionTileDTO> cards = new LinkedList<>();
        LinkedList<String > c = new LinkedList<>();
        c.add("city1Name");
        c.add("city2Name");
        LinkedList<BonusDTO> b = new LinkedList<>();
        b.add(new BonusDTO("bonusName", 6));
        cards.add(new BusinessPermissionTileDTO(c, new RewardTileDTO(b), 0, "playerName"));
        BusinessPermissionTileDeckDTO d = new BusinessPermissionTileDeckDTO(cards);
        region.setDeck(d);
        assertEquals(region.getDeck(), d);
    }

    @Test
    public void testGetBonus() throws Exception {
        assertEquals(region.getBonus(), bonus);
    }

    @Test
    public void testToString() throws Exception {
        region.toString();
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(region.hashCode(), new RegionDTO(name, cities, deck, balcony, bonus).hashCode());
    }

    @Test
    public void testEquals() throws Exception {
        String n = "otherRegionName";
        LinkedList<CityDTO> c = new LinkedList<>();

        CityColorDTO color = new CityColorDTO("colorName");
        LinkedList<BonusDTO> bonuses = new LinkedList<>();
        bonuses.add(new BonusDTO("bonus1Name", 3));
        LinkedList<EmporiumDTO> emporiums = new LinkedList<>();
        emporiums.add(new EmporiumDTO("aPlayerName"));
        LinkedList<String> nearCities = new LinkedList<>();
        nearCities.add("aNearCityName");
        c.add(new CityDTO("othercityName", color, new RewardTileDTO(bonuses), emporiums, nearCities));

        assertTrue(region.equals(region));
        assertTrue(region.equals(new RegionDTO(name, cities, deck, balcony, bonus)));
        assertFalse(region.equals(new RegionDTO(n, cities, deck, balcony, bonus)));
        assertFalse(region.equals(new RegionDTO(name, c, deck, balcony, bonus)));
        assertFalse(region.equals(null));
        assertFalse(region.equals(new Assistant()));
    }
}