package it.polimi.ingsw.cg26.server.model.board;

import it.polimi.ingsw.cg26.server.model.state.NobilityCellState;
import it.polimi.ingsw.cg26.server.model.state.NobilityTrackState;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 */
public class NobilityTrack {

	/**
	 *
	 */
	private NobilityCell firstCell;

	/**
	 *
	 */
	private NobilityTrack(NobilityCell firstCell) {
		if (firstCell == null)
			throw new NullPointerException();
		this.firstCell = firstCell;
	}

	public static NobilityTrack createNobilityTrack(NobilityCell firstCell) {
		return new NobilityTrack(firstCell);
	}

	public NobilityTrackState getState() {
		List<NobilityCellState> cellsState = new LinkedList<>();
		NobilityCell cell = firstCell;
		while (cell.hasNext()) {
			cellsState.add(cell.getState());
			cell=cell.next();
		}
		return new NobilityTrackState(cellsState);
	}

	/**
	 *
	 * @return
     */
	public NobilityCell getFirstCell() {
		return firstCell;
	}
}