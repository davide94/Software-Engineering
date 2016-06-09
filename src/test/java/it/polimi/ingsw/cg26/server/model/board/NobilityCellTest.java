package it.polimi.ingsw.cg26.server.model.board;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg26.common.dto.NobilityCellDTO;
import it.polimi.ingsw.cg26.server.model.bonus.*;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class NobilityCellTest {
	
	
	private Bonus bonuses2;
	private Bonus bonuses3;
	private NobilityCell cell2;
	private NobilityCell cell3;
	private NobilityCell cell4;
	private NobilityCell cellA;
	private NobilityCell cellB;
	private Player Luca;
	private Player Davide;

	
	
	
	@Before
    public void setUp() throws Exception {
		
				
		bonuses2 = new CoinBonus(new VictoryBonus(new EmptyBonus(), 4), 5);
		bonuses3 = new NobilityBonus(new MainActionBonus(new AssistantBonus(new EmptyBonus(), 2), 1), 1);
		
		cell4= NobilityCell.createNobilityCell(4, null, bonuses3);
		cell3= NobilityCell.createNobilityCell(3, cell4, bonuses3);
		cell2= NobilityCell.createNobilityCell(2, cell3, bonuses2);
		
		cellA = NobilityCell.createNobilityCell(2, null, bonuses2);
        cellB = NobilityCell.createNobilityCell(2, cell3, new EmptyBonus());
        
		
		Luca=new Player(1235, "Luca", cell2 , 10, new LinkedList<>(), new LinkedList<>());
		Davide=new Player(1236, "davide", cell3 , 10, new LinkedList<>(), new LinkedList<>());
		
		
	}
	
	
	
	@Test
	public void testApplyBonusToLuca() throws Throwable {
		cell2.apply(Luca);
		assertEquals(Luca.getCoinsNumber(), 15);
		assertEquals(Luca.getVictoryPoints(), 4);
		
	}
	
	
	@Test
	public void testApplyBonusToDavide() throws Throwable {
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
		assertEquals(cell3.getIndex(), 3);
		assertEquals(cell4.getIndex(), 4);
		
	}

	


	@Test
	public void testHasNextShouldBeTrue() {
		assertTrue(cell3.hasNext());
		assertTrue(cell2.hasNext());
		assertFalse(cell4.hasNext());
		
	}
	
	
	@Test
	public void testGetState() {
		
		NobilityCellDTO cell2DTO= new NobilityCellDTO(2, bonuses2.getState());
		
		assertEquals((cell2.getState()).getIndex(), cell2DTO.getIndex());
		assertEquals((cell2.getState()).getBonuses(), cell2DTO.getBonuses());
		
	}
	
	
	
	
	@Test
	public void testNext() {
		
		assertEquals(cell2.next(), cell3);
		assertEquals(cell3.next(), cell4);
		assertEquals(cell4.next(), null);
		
	}
	
	
	
	@Test
	public void testEquals() {
		
        
        
		
		
		assertTrue(cell2.equals(cell2));
		assertFalse(cell2.equals(null));
		assertFalse(cell2.equals(Luca));
		assertFalse(cell2.equals(cell3));
		assertFalse(cell2.equals(cell4));
		assertFalse(cell2.equals(cellA));
		assertFalse(cell2.equals(cellB));
		
		
		
	}
	
	
	@Test
	public void testToString() {
		
		assertEquals(cell2.toString(), "NobilityCell{" +
                "index=" + 2 +
                ", bonuses=" + new CoinBonus(new VictoryBonus(new EmptyBonus(), 4), 5) +
                ", next=" + cell3 +
                '}');
		
		assertEquals(cell3.toString(), "NobilityCell{" +
                "index=" + 3 +
                ", bonuses=" + bonuses3 +
                ", next=" + cell4 +
                '}');
		
		
		assertEquals(cell4.toString(), "NobilityCell{" +
                "index=" + 4 +
                ", bonuses=" + bonuses3 +
                ", next=" + null +
                '}');
		
	}
	
	
	@Test
	public void testHashCode() {
		
		assertEquals(cell2.hashCode(), cell2.hashCode() );
		assertEquals(cell3.hashCode(), cell3.hashCode() );
		assertEquals(cell4.hashCode(), cell4.hashCode() );
		
		
		assertNotEquals(cell2.hashCode(), cell3.hashCode() );
		assertNotEquals(cell2.hashCode(), cell4.hashCode() );
		assertNotEquals(cell3.hashCode(), cell4.hashCode() );
		
		
	}

	

	
}
