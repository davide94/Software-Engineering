package it.polimi.ingsw.cg26.common.dto;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 *
 */
public class RewardTileDTOTest {

    private RewardTileDTO tile;

    private LinkedList<BonusDTO> bonuses;

    @Before
    public void setUp() throws Exception {
        bonuses = new LinkedList<>();
        bonuses.add(new BonusDTO("bonusName", 7));
        tile = new RewardTileDTO(bonuses);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail() throws Exception {
        new RewardTileDTO(null);
    }

    @Test
    public void testGetBonuses() throws Exception {
        assertEquals(tile.getBonuses(), bonuses);
    }
}