package it.polimi.ingsw.cg26.server.model.board;

import static org.junit.Assert.*;


import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.server.model.bonus.*;
import it.polimi.ingsw.cg26.server.model.cards.RewardTile;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class NobilityCellTest {
	
	
	private List<Bonus> bonuses2;
	private List<Bonus> bonuses3;
	private NobilityCell cell2;
	private NobilityCell cell3;
	private NobilityCell cell4;
	private Player Luca;
	private Player Davide;
	private RewardTile tile2;
	private RewardTile tile3;
	
	
	@Before
    public void setUp() throws Exception {
		
				
		bonuses2 = new LinkedList<>();
		bonuses3 = new LinkedList<>();
		
		bonuses2.add(new VictoryBonus(4));
		bonuses2.add(new CoinBonus(5));
		
		bonuses3.add(new AssistantBonus(2));
		bonuses3.add(new MainActionBonus(1));
		bonuses3.add(new NobilityBonus(1));
		
		tile2= new RewardTile(bonuses2);
		tile3= new RewardTile(bonuses3);
		
		
		cell4= NobilityCell.createNobilityCell(4, null, tile3);
		cell3= NobilityCell.createNobilityCell(3, cell4, tile3);
		cell2= NobilityCell.createNobilityCell(2, cell3, tile2);
		Luca=new Player(1235, "Luca", cell2 , 10, new LinkedList<>(), new LinkedList<>());
		Davide=new Player(1236, "davide", cell3 , 10, new LinkedList<>(), new LinkedList<>());
		
		
	}
	
	
	
	@Test
	public void testApplyBonusToLuca() {
		cell2.apply(Luca);
		assertEquals(Luca.getCoinsNumber(), 15);
		assertEquals(Luca.getVictoryPoints(), 4);
		
	}
	
	
	@Test
	public void testApplyBonusToDavide() {
		cell3.apply(Davide);
		
		assertTrue(Davide.canPerformMainAction());
		
		
		
		
		assertEquals(Davide.getNobilityCell().getIndex(), 4);
		
	}
	
	
	

	@Test (expected=IllegalArgumentException.class)
	public void testShouldNotCreateNobilityCellWithNegativeIndex() {
		
		NobilityCell.createNobilityCell(-1, null, tile2);
		
	}
	
	
	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateNobilityCellWithNullBonuses() {
		
		List<Bonus> bonusesNull= null;
		RewardTile tileNull= new RewardTile(bonusesNull);
		
		NobilityCell.createNobilityCell(5, null, tileNull);
		
	}
	
	
	
	
	@Test 
	public void testCreateNobilityCell() {
		
		assertNotNull(NobilityCell.createNobilityCell(1, cell2, tile2));
		
	}
	
	@Test
	public void testGetIndex() {
		assertEquals(cell2.getIndex(), 2);
		
	}

	

	@Test
	public void testNext() {
		
	}

	@Test
	public void testHasNextShouldBeTrue() {
		assertTrue(cell3.hasNext());
		
	}
	
	
	@Test
	public void testGetState() {
		
	}

	

	
}
