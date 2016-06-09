package it.polimi.ingsw.cg26.server.model.bonus;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import it.polimi.ingsw.cg26.server.model.board.NobilityCell;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class VictoryBonusTest {

	private Bonus bonus;
	
	@Before
	public void setUp(){
		this.bonus = new EmptyBonus();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithNegativeMultiplicity() {
		new VictoryBonus(bonus, -1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithZeroMultiplicity() {
		new VictoryBonus(bonus, 0);
	}
	
	@Test
	public void testApplyVicoryBonusWithMultiplicity5OnPlayerWith0PointsShouldHave5Points() throws Exception {
		NobilityCell cell = NobilityCell.createNobilityCell(1, null, new EmptyBonus());
		Player player = new Player(1, "Marco", cell, 2, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());
		VictoryBonus victoryBonus = new VictoryBonus(bonus, 5);
		victoryBonus.apply(player);
		
		assertEquals(5, player.getVictoryPoints());
		
	}
	
	@Test
	public void testApplyVicoryBonusWithMultiplicity16OnPlayerWith5PointsShouldHave21Points() throws Exception {
		NobilityCell cell = NobilityCell.createNobilityCell(1, null, new EmptyBonus());
		Player player = new Player(1, "Marco", cell, 2, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());
		player.addVictoryPoints(5);
		VictoryBonus victoryBonus = new VictoryBonus(bonus, 16);
		victoryBonus.apply(player);
		
		assertEquals(21, player.getVictoryPoints());
		
	}

	@Test
	public void testGetState(){
		VictoryBonus victoryBonus = new VictoryBonus(bonus, 7);
		BonusDTO bonusDTO = victoryBonus.getState();
		
		assertEquals("7 Victory Points", bonusDTO.toString());
	}
	
	@Test
	public void testToString(){
		VictoryBonus victoryBonus = new VictoryBonus(bonus, 3);
		
		assertEquals("\nVictoryBonus{multiplicity=3}", victoryBonus.toString());
	}
	
	@Test
	public void testHashCodeEquals(){
		Bonus b1 = new VictoryBonus(new EmptyBonus(), 3);
		Bonus b2 = new VictoryBonus(new EmptyBonus(), 3);
		
		assertFalse(b1.equals(new VictoryBonus(new EmptyBonus(), 5)));
		assertTrue(b1.equals(new VictoryBonus(new EmptyBonus(), 3)));
		assertEquals(b1.hashCode(), b2.hashCode());
	}
}
