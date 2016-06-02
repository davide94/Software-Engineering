package it.polimi.ingsw.cg26.common.dto;

import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTODecorator;
import it.polimi.ingsw.cg26.common.dto.bonusdto.CardBonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.CoinBonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.EmptyBonusDTO;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class BonusDTOTest {

    private BonusDTO bonus;

    @Before
    public void setUp() throws Exception {
        bonus = new CoinBonusDTO(new CardBonusDTO(new EmptyBonusDTO(), 5), 3);
    }

    @Test (expected = NullPointerException.class)
    public void testConstructorShouldFail1() throws Exception {
        new BonusDTODecorator(null, 3);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorShouldFail2() throws Exception {
        new BonusDTODecorator(bonus, -2);
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(bonus.equals(bonus));
        assertTrue(bonus.equals(new CoinBonusDTO(new CardBonusDTO(new EmptyBonusDTO(), 5), 3)));
        assertFalse(bonus.equals(null));
        assertFalse(bonus.equals(new CardBonusDTO(new EmptyBonusDTO(), 5)));
        assertFalse(bonus.equals(new CoinBonusDTO(new EmptyBonusDTO(), 6)));
        assertFalse(bonus.equals(new Assistant()));
    }

    @Test
    public void testHashCode() throws Exception {
        BonusDTODecorator b = new CoinBonusDTO(new CardBonusDTO(new EmptyBonusDTO(), 5), 3);
        assertEquals(bonus.hashCode(), b.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        bonus.toString();
    }
}