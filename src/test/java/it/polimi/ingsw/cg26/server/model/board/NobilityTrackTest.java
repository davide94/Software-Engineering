package it.polimi.ingsw.cg26.server.model.board;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.cg26.server.model.bonus.EmptyBonus;

public class NobilityTrackTest {
	
	
	
	

	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateNobilityTrack() {
	    NobilityCell cell1=null;
	    NobilityTrack.createNobilityTrack(cell1);
	    
	}
	
	
	@Test 
	public void testShouldCreateNobilityTrack() {
	    NobilityCell cell1=NobilityCell.createNobilityCell(1, null, new EmptyBonus());
	    
	    assertNotNull(NobilityTrack.createNobilityTrack(cell1));
	    
	}
	

	
	@Test
	public void testGetState() {
	    NobilityCell cell1=NobilityCell.createNobilityCell(1, null, new EmptyBonus());
	    NobilityTrack track= NobilityTrack.createNobilityTrack(cell1);
	   
	    
	    assertNotNull(track.getState());
	    
	    //DA RIVEDERE
	    /*
	    List<NobilityCellDTO> cells= new LinkedList<>();
	    cells.add(cell1.getState());
	    
	    assertEquals(track.getState(), cells);
	    */
		
	}
	

	@Test
	public void testGetFirstCell() {
	    NobilityCell cell1=NobilityCell.createNobilityCell(1, null, new EmptyBonus());
	    NobilityTrack track= NobilityTrack.createNobilityTrack(cell1);
	    
	    
	    assertEquals(track.getFirstCell(), cell1);
		
	}

}
