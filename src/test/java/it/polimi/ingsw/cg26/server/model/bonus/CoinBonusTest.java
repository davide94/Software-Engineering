package it.polimi.ingsw.cg26.server.model.bonus;

import it.polimi.ingsw.cg26.server.model.cards.RewardTile;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.BonusDTO;
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

    @Test
    public void thestCreate() {
        assertNotNull("Not possible", new CoinBonus(100));
    }

    @Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithNegativeMultiplicity() {
		new CoinBonus(-1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithZeroMultiplicity() {
		new CoinBonus(0);
	}
	
	@Test
	public void testApplyCoinBonusWithMultiplicity6ToAPlayerWith2CoinsShouldAtTheEndHave8Coins(){
		NobilityCell cell = NobilityCell.createNobilityCell(1, null, new RewardTile(new ArrayList<>()));
		Player player = new Player(1, "Marco", cell, 2, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());
		CoinBonus bonus = new CoinBonus(6);
		bonus.apply(player);
		
		assertEquals(8, player.getCoinsNumber());
	}
	
	@Test
	public void testGetState(){
		CoinBonus bonus = new CoinBonus(3);
		BonusDTO bonusDTO = bonus.getState();
		
		assertEquals("Coins", bonusDTO.getKind());
		assertEquals(3, bonusDTO.getMultiplicity());
	}
	
	@Test
	public void testToString(){
		CoinBonus bonus = new CoinBonus(5);
		
		assertEquals("CoinBonus{multiplicity=5}", bonus.toString());
	}
}