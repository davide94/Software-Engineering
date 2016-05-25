package it.polimi.ingsw.cg26.common.state;

import it.polimi.ingsw.cg26.server.model.player.Assistant;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 *
 */
public class RegionStateTest {

    private RegionState region;

    private String name;
    private LinkedList<CityState> cities;
    private BusinessPermissionTileDeckState deck;
    private BalconyState balcony;
    private RewardTileState bonus;

    @Before
    public void setUp() throws Exception {
        name = "regionName";
        cities = new LinkedList<>();
        CityColorState color = new CityColorState("colorName");
        LinkedList<BonusState> bonuses = new LinkedList<>();
        bonuses.add(new BonusState("bonus1Name", 3));
        LinkedList<EmporiumState> emporiums = new LinkedList<>();
        emporiums.add(new EmporiumState("aPlayerName"));
        LinkedList<String> nearCities = new LinkedList<>();
        nearCities.add("aNearCityName");
        cities.add(new CityState("cityName", color, bonuses, emporiums, nearCities));
        deck = new BusinessPermissionTileDeckState(new LinkedList<>());
        balcony = new BalconyState(new LinkedList<>());
        bonus = new RewardTileState(new LinkedList<>());
        region = new RegionState(name, cities, deck, balcony, bonus);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail1() throws Exception {
        new RegionState(null, cities, deck, balcony, bonus);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail2() throws Exception {
        new RegionState(name, null, deck, balcony, bonus);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail3() throws Exception {
        new RegionState(name, cities, null, balcony, bonus);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail4() throws Exception {
        new RegionState(name, cities, deck, null, bonus);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail5() throws Exception {
        new RegionState(name, cities, deck, balcony, null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorShouldFail6() throws Exception {
        new RegionState("", cities, deck, balcony, bonus);
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
        LinkedList<CouncillorState> councillors = new LinkedList<>();
        councillors.add(new CouncillorState(new PoliticColorState("councillorColorName")));
        BalconyState b = new BalconyState(councillors);
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
        LinkedList<BusinessPermissionTileState> cards = new LinkedList<>();
        LinkedList<String > c = new LinkedList<>();
        c.add("city1Name");
        c.add("city2Name");
        LinkedList<BonusState> b = new LinkedList<>();
        b.add(new BonusState("bonusName", 6));
        cards.add(new BusinessPermissionTileState(c, b, 0, "playerName"));
        BusinessPermissionTileDeckState d = new BusinessPermissionTileDeckState(cards);
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
        assertEquals(region.hashCode(), new RegionState(name, cities, deck, balcony, bonus).hashCode());
    }

    @Test
    public void testEquals() throws Exception {
        String n = "otherRegionName";
        LinkedList<CityState> c = new LinkedList<>();

        CityColorState color = new CityColorState("colorName");
        LinkedList<BonusState> bonuses = new LinkedList<>();
        bonuses.add(new BonusState("bonus1Name", 3));
        LinkedList<EmporiumState> emporiums = new LinkedList<>();
        emporiums.add(new EmporiumState("aPlayerName"));
        LinkedList<String> nearCities = new LinkedList<>();
        nearCities.add("aNearCityName");
        c.add(new CityState("othercityName", color, bonuses, emporiums, nearCities));

        assertTrue(region.equals(region));
        assertTrue(region.equals(new RegionState(name, cities, deck, balcony, bonus)));
        assertFalse(region.equals(new RegionState(n, cities, deck, balcony, bonus)));
        assertFalse(region.equals(new RegionState(name, c, deck, balcony, bonus)));
        assertFalse(region.equals(null));
        assertFalse(region.equals(new Assistant()));
    }
}