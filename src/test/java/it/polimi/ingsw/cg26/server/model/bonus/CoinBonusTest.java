package it.polimi.ingsw.cg26.server.model.bonus;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import it.polimi.ingsw.cg26.server.model.board.NobilityCell;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 */
public class CoinBonusTest {

	private Bonus bonus;
	
	@Before
	public void setUp(){
		this.bonus = new EmptyBonus();
	}
	
    @Test
    public void thestCreate() {
        assertNotNull("Not possible", new CoinBonus(bonus, 100));
    }

    @Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithNegativeMultiplicity() {
		new CoinBonus(bonus, -1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithZeroMultiplicity() {
		new CoinBonus(bonus, 0);
	}
	
	@Test
	public void testApplyCoinBonusWithMultiplicity6ToAPlayerWith2CoinsShouldAtTheEndHave8Coins() throws Exception {
		NobilityCell cell = NobilityCell.createNobilityCell(1, null, new EmptyBonus());
		Player player = new Player(1, "Marco", cell, 2, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());
		CoinBonus coinBonus = new CoinBonus(bonus, 6);
		coinBonus.apply(player);
		
		assertEquals(8, player.getCoinsNumber());
	}
	
	@Test
	public void testGetState(){
		CoinBonus coinBonus = new CoinBonus(bonus, 3);
		BonusDTO bonusDTO = coinBonus.getState();
		
		assertEquals("3 Coins", bonusDTO.toString());
	}
	
	@Test
	public void testToString(){
		CoinBonus coinBonus = new CoinBonus(bonus, 5);
		
		assertEquals("\nCoinBonus{multiplicity=5}", coinBonus.toString());
	}
	
	@Test
	public void testHashCodeEquals(){
		CoinBonus coinBonus1 = new CoinBonus(bonus, 5);
		CoinBonus coinBonus2 = new CoinBonus(bonus, 5);
		
		assertTrue(coinBonus1.equals(coinBonus2));
		assertEquals(coinBonus1.hashCode(), coinBonus2.hashCode());
	}
}