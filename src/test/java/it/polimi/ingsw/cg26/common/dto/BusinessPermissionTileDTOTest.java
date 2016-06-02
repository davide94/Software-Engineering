package it.polimi.ingsw.cg26.common.dto;

import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.CardBonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.CoinBonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.EmptyBonusDTO;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 *
 */
public class BusinessPermissionTileDTOTest {

    private LinkedList<String> cities;

    private BonusDTO bonuses;

    private BusinessPermissionTileDTO tile;

    @Before
    public void setUp() throws Exception {
        cities = new LinkedList<>();
        cities.add("city1Name");
        cities.add("city2Name");
        bonuses = new CoinBonusDTO(new EmptyBonusDTO(), 7);
        tile = new BusinessPermissionTileDTO(cities, bonuses, 0, "playerName");
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail1() throws Exception {
        new BusinessPermissionTileDTO(null, bonuses, 0, "playerName");
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail2() throws Exception {
        new BusinessPermissionTileDTO(cities, null, 0, "playerName");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorShouldFail3() throws Exception {
        new BusinessPermissionTileDTO(cities, bonuses, -4, "playerName");
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
        BonusDTO b = new CardBonusDTO(new EmptyBonusDTO(), 8);

        assertTrue(tile.equals(tile));
        assertTrue(tile.equals(new BusinessPermissionTileDTO(cities, bonuses, 0, "playerName")));
        assertFalse(tile.equals(new BusinessPermissionTileDTO(c, bonuses, 0, "PlayerName")));
        assertFalse(tile.equals(new BusinessPermissionTileDTO(cities, b, 0, "PlayerName")));
        assertFalse(tile.equals(null));
        assertFalse(tile.equals(new Assistant()));
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(tile.hashCode(), new BusinessPermissionTileDTO(cities, bonuses, 0, "playerName").hashCode());
    }

    @Test
    public void testToString() throws Exception {
        tile.toString();
    }
}