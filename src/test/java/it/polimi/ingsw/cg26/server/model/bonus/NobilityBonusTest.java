package it.polimi.ingsw.cg26.server.model.bonus;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;

import it.polimi.ingsw.cg26.server.model.cards.RewardTile;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.BonusDTO;
import it.polimi.ingsw.cg26.server.model.board.NobilityCell;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class NobilityBonusTest {

	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithNegativeMultiplicity() {
		new NobilityBonus(-1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithZeroMultiplicity() {
		new NobilityBonus(0);
	}
	
	@Test
	public void testApplyBonusWithMultiplicity2ToAPlayerOnCell1ShouldPutOnCell3(){
		NobilityCell cell3 = NobilityCell.createNobilityCell(3, null, new RewardTile(new LinkedList<Bonus>()));
		NobilityCell cell2 = NobilityCell.createNobilityCell(2, cell3, new RewardTile(new LinkedList<Bonus>()));
		NobilityCell cell1 = NobilityCell.createNobilityCell(1, cell2, new RewardTile(new LinkedList<Bonus>()));
		Player player = new Player(1, "Marco", cell1, 2, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());
		NobilityBonus bonus = new NobilityBonus(2);
		bonus.apply(player);
		
		assertEquals(cell3, player.getNobilityCell());
	}
	
	@Test
	public void testApplyBonusWithMultiplicity1ToAPlayerOnCell1ShouldPutOnCell2(){
		NobilityCell cell2 = NobilityCell.createNobilityCell(2, null, new RewardTile(new LinkedList<Bonus>()));
		NobilityCell cell1 = NobilityCell.createNobilityCell(1, cell2, new RewardTile(new LinkedList<Bonus>()));
		Player player = new Player(1, "Marco", cell1, 2, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());
		NobilityBonus bonus = new NobilityBonus(1);
		bonus.apply(player);
		
		assertEquals(cell2, player.getNobilityCell());
	}
	
	@Test
	public void testApplyBonusWithMultiplicity4ToAPlayerOnCell1ShouldPutOnCell4MaximumCell(){
		NobilityCell cell4 = NobilityCell.createNobilityCell(4, null, new RewardTile(new LinkedList<Bonus>()));
		NobilityCell cell3 = NobilityCell.createNobilityCell(3, cell4, new RewardTile(new LinkedList<Bonus>()));
		NobilityCell cell2 = NobilityCell.createNobilityCell(2, cell3, new RewardTile(new LinkedList<Bonus>()));
		NobilityCell cell1 = NobilityCell.createNobilityCell(1, cell2, new RewardTile(new LinkedList<Bonus>()));
		Player player = new Player(1, "Marco", cell1, 2, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());
		NobilityBonus bonus = new NobilityBonus(4);
		bonus.apply(player);
		
		assertEquals(cell4, player.getNobilityCell());
	}
	
	@Test
	public void testGetState(){
		NobilityBonus bonus = new NobilityBonus(4);
		BonusDTO bonusDTO = bonus.getState();
		
		assertEquals("Nobility points", bonusDTO.getKind());
		assertEquals(4, bonusDTO.getMultiplicity());
	}
	
	@Test
	public void testToString(){
		NobilityBonus bonus =  new NobilityBonus(6);
		
		assertEquals("NobilityBonus{multiplicity=6}", bonus.toString());
	}

}
