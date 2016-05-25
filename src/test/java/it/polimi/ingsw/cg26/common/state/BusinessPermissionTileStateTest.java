package it.polimi.ingsw.cg26.common.state;

import it.polimi.ingsw.cg26.server.model.player.Assistant;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 *
 */
public class BusinessPermissionTileStateTest {

    private LinkedList<String> cities;

    private LinkedList<BonusState> bonuses;

    private BusinessPermissionTileState tile;

    @Before
    public void setUp() throws Exception {
        cities = new LinkedList<>();
        cities.add("city1Name");
        cities.add("city2Name");
        bonuses = new LinkedList<>();
        bonuses.add(new BonusState("bonusName", 6));
        tile = new BusinessPermissionTileState(cities, bonuses, 0, "playerName");
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail1() throws Exception {
        new BusinessPermissionTileState(null, bonuses, 0, "playerName");
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail2() throws Exception {
        new BusinessPermissionTileState(cities, null, 0, "playerName");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorShouldFail3() throws Exception {
        new BusinessPermissionTileState(cities, bonuses, -4, "playerName");
    }

    @Test
    public void testGetCities() throws Exception {
        assertEquals(tile.getCities(), cities);
    }

    @Test
    public void testGetBonuses() throws Exception {
        assertEquals(tile.getBonuses(), bonuses);
    }

    @Test
    public void testEquals() throws Exception {
        LinkedList<String> c = new LinkedList<>();
        c.add("otherCity1Name");
        c.add("city2Name");
        LinkedList<BonusState> b = new LinkedList<>();
        b.add(new BonusState("otherBonusName", 3));

        assertTrue(tile.equals(tile));
        assertTrue(tile.equals(new BusinessPermissionTileState(cities, bonuses, 0, "playerName")));
        assertFalse(tile.equals(new BusinessPermissionTileState(c, bonuses, 0, "PlayerName")));
        assertFalse(tile.equals(new BusinessPermissionTileState(cities, b, 0, "PlayerName")));
        assertFalse(tile.equals(null));
        assertFalse(tile.equals(new Assistant()));
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(tile.hashCode(), new BusinessPermissionTileState(cities, bonuses, 0, "playerName").hashCode());
    }

    @Test
    public void testToString() throws Exception {
        tile.toString();
    }
}