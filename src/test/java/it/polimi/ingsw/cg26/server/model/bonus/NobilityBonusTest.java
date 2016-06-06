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

public class NobilityBonusTest {

	private Bonus bonus;
	
	@Before
	public void setUp(){
		this.bonus = new EmptyBonus();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithNegativeMultiplicity() {
		new NobilityBonus(bonus, -1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreationShouldFailWithZeroMultiplicity() {
		new NobilityBonus(bonus, 0);
	}
	
	@Test
	public void testApplyBonusWithMultiplicity2ToAPlayerOnCell1ShouldPutOnCell3(){
		NobilityCell cell3 = NobilityCell.createNobilityCell(3, null, new EmptyBonus());
		NobilityCell cell2 = NobilityCell.createNobilityCell(2, cell3, new EmptyBonus());
		NobilityCell cell1 = NobilityCell.createNobilityCell(1, cell2, new EmptyBonus());
		Player player = new Player(1, "Marco", cell1, 2, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());
		NobilityBonus nobilityBonus = new NobilityBonus(bonus, 2);
		nobilityBonus.apply(player);
		
		assertEquals(cell3, player.getNobilityCell());
	}
	
	@Test
	public void testApplyBonusWithMultiplicity1ToAPlayerOnCell1ShouldPutOnCell2(){
		NobilityCell cell2 = NobilityCell.createNobilityCell(2, null, new EmptyBonus());
		NobilityCell cell1 = NobilityCell.createNobilityCell(1, cell2, new EmptyBonus());
		Player player = new Player(1, "Marco", cell1, 2, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());
		NobilityBonus nobilityBonus = new NobilityBonus(bonus, 1);
		nobilityBonus.apply(player);
		
		assertEquals(cell2, player.getNobilityCell());
	}
	
	@Test
	public void testApplyBonusWithMultiplicity4ToAPlayerOnCell1ShouldPutOnCell4MaximumCell(){
		NobilityCell cell4 = NobilityCell.createNobilityCell(4, null, new EmptyBonus());
		NobilityCell cell3 = NobilityCell.createNobilityCell(3, cell4, new EmptyBonus());
		NobilityCell cell2 = NobilityCell.createNobilityCell(2, cell3, new EmptyBonus());
		NobilityCell cell1 = NobilityCell.createNobilityCell(1, cell2, new EmptyBonus());
		Player player = new Player(1, "Marco", cell1, 2, new ArrayList<PoliticCard>(), new LinkedList<Assistant>());
		NobilityBonus nobilityBonus = new NobilityBonus(bonus, 4);
		nobilityBonus.apply(player);
		
		assertEquals(cell4, player.getNobilityCell());
	}
	
	@Test
	public void testGetState(){
		NobilityBonus nobilityBonus = new NobilityBonus(bonus, 4);
		BonusDTO bonusDTO = nobilityBonus.getState();
		
		assertEquals("4 Nobility", bonusDTO.toString());
	}
	
	@Test
	public void testToString(){
		NobilityBonus nobilityBonus =  new NobilityBonus(bonus, 6);
		
		assertEquals("\nNobilityBonus{multiplicity=6}", nobilityBonus.toString());
	}
	
	@Test
	public void testHashCodeEquals(){
		NobilityBonus nobilityBonus1 =  new NobilityBonus(bonus, 6);
		NobilityBonus nobilityBonus2 =  new NobilityBonus(bonus, 6);
		
		assertTrue(nobilityBonus1.equals(nobilityBonus2));
		assertEquals(nobilityBonus1.hashCode(), nobilityBonus2.hashCode());
	}

}
