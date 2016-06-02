package it.polimi.ingsw.cg26.common.dto;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.CardBonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.CoinBonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.EmptyBonusDTO;

import static org.junit.Assert.*;

/**
 *
 */
public class RewardTileDTOTest {

    private RewardTileDTO tile;

    private BonusDTO bonuses;

    @Before
    public void setUp() throws Exception {
        bonuses = new CardBonusDTO(new CoinBonusDTO(new EmptyBonusDTO(), 2), 3);
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