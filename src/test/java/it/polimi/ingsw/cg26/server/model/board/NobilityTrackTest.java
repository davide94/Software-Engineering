package it.polimi.ingsw.cg26.server.model.board;

import static org.junit.Assert.*;


import java.util.LinkedList;
import java.util.List;

import it.polimi.ingsw.cg26.server.model.cards.RewardTile;
import org.junit.Test;
import it.polimi.ingsw.cg26.common.dto.BonusDTO;
import it.polimi.ingsw.cg26.common.dto.NobilityCellDTO;
import it.polimi.ingsw.cg26.common.dto.NobilityTrackDTO;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;

public class NobilityTrackTest {
	
	
	
	

	@Test (expected=NullPointerException.class)
	public void testShouldNotCreateNobilityTrack() {
	    NobilityCell cell1=null;
	    NobilityTrack.createNobilityTrack(cell1);
	    
	}
	
	
	@Test 
	public void testShouldCreateNobilityTrack() {
		List<Bonus> bonuses1;
		bonuses1= new LinkedList<>();
	    NobilityCell cell1=NobilityCell.createNobilityCell(1, null, new RewardTile(bonuses1));
	    
	    assertNotNull(NobilityTrack.createNobilityTrack(cell1));
	    
	}
	

	
	@Test
	public void testGetState() {
		List<Bonus> bonuses1;
		bonuses1= new LinkedList<>();
	    NobilityCell cell1=NobilityCell.createNobilityCell(1, null, new RewardTile(bonuses1));
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
		
		List<Bonus> bonuses1;
		bonuses1= new LinkedList<>();
	    NobilityCell cell1=NobilityCell.createNobilityCell(1, null, new RewardTile(bonuses1));
	    NobilityTrack track= NobilityTrack.createNobilityTrack(cell1);
	    
	    
	    assertEquals(track.getFirstCell(), cell1);
		
	}

}
