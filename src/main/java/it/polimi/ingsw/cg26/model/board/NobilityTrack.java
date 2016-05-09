package it.polimi.ingsw.cg26.model.board;

import java.util.List;
import java.util.ArrayList;

/**
 * 
 */
public class NobilityTrack {
	
	private int len;
	private List<NobilityCell> cells;
	
	
	

    /**
     *
     */
	public NobilityTrack(int len, List<NobilityCell> cells) {
		this.len=len;
		//this.cells= new ArrayList<NobilityCell>();
		this.cells=cells;
	}
	

	public NobilityCell getFirstCell() {
		return cells.get(0);
	}
}