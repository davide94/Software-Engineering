package it.polimi.ingsw.cg26.server.model.board;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.server.model.bonus.*;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class NobilityCellTest {
	
	
	private Bonus bonuses2;
	private Bonus bonuses3;
	private NobilityCell cell2;
	private NobilityCell cell3;
	private NobilityCell cell4;
	private Player Luca;
	private Player Davide;
	
	
	@Before
    public void setUp() throws Exception {
		
				
		bonuses2 = new CoinBonus(new VictoryBonus(new EmptyBonus(), 4), 5);
		bonuses3 = new NobilityBonus(new MainActionBonus(new AssistantBonus(new EmptyBonus(), 2), 1), 1);
		
		cell4= NobilityCell.createNobilityCell(4, null, bonuses3);
		cell3= NobilityCell.createNobilityCell(3, cell4, bonuses3);
		cell2= NobilityCell.createNobilityCell(2, cell3, bonuses2);
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
		
		NobilityCell.createNobilityCell(-1, null, bonuses2);
		
	}
	
	
	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateNobilityCellWithNullBonuses() {
		
		NobilityCell.createNobilityCell(5, null, null);
		
	}
	
	
	
	
	@Test 
	public void testCreateNobilityCell() {
		
		assertNotNull(NobilityCell.createNobilityCell(1, cell2, bonuses2));
		
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
