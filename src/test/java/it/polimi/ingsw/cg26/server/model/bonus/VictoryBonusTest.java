package it.polimi.ingsw.cg26.server.model.bonus;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.BonusDTO;
import it.polimi.ingsw.cg26.server.model.board.NobilityCell;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class VictoryBonusTest {

	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithNegativeMultiplicity() {
		new VictoryBonus(-1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithZeroMultiplicity() {
		new VictoryBonus(0);
	}
	
	@Test
	public void testApplyVicoryBonusWithMultiplicity5OnPlayerWith0PointsShouldHave5Points(){
		NobilityCell cell = NobilityCell.createNobilityCell(1, null, new ArrayList<>());
		Player player = new Player(1, "Marco", cell, 2, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());
		VictoryBonus bonus = new VictoryBonus(5);
		bonus.apply(player);
		
		assertEquals(5, player.getVictoryPoints());
		
	}
	
	@Test
	public void testApplyVicoryBonusWithMultiplicity16OnPlayerWith5PointsShouldHave21Points(){
		NobilityCell cell = NobilityCell.createNobilityCell(1, null, new ArrayList<>());
		Player player = new Player(1, "Marco", cell, 2, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());
		player.addVictoryPoints(5);
		VictoryBonus bonus = new VictoryBonus(16);
		bonus.apply(player);
		
		assertEquals(21, player.getVictoryPoints());
		
	}

	@Test
	public void testGetState(){
		VictoryBonus bonus = new VictoryBonus(7);
		BonusDTO bonusDTO = bonus.getState();
		
		assertEquals("Victory points", bonusDTO.getKind());
		assertEquals(7, bonusDTO.getMultiplicity());
	}
	
	@Test
	public void testToString(){
		VictoryBonus bonus = new VictoryBonus(3);
		
		assertEquals("VictoryBonus{multiplicity=3}", bonus.toString());
	}
}
