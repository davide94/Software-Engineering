package it.polimi.ingsw.cg26.server.model.board;

import it.polimi.ingsw.cg26.common.dto.NobilityCellDTO;
import it.polimi.ingsw.cg26.common.dto.NobilityTrackDTO;

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

	public NobilityTrackDTO getState() {
		List<NobilityCellDTO> cellsState = new LinkedList<>();
		NobilityCell cell = firstCell;
		while (cell.hasNext()) {
			cellsState.add(cell.getState());
			cell=cell.next();
		}
		return new NobilityTrackDTO(cellsState);
	}

	/**
	 *
	 * @return
     */
	public NobilityCell getFirstCell() {
		return firstCell;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) 
			return true;
		if (o == null || getClass() != o.getClass()) 
			return false;

		NobilityTrack that = (NobilityTrack) o;

		return firstCell != null ? firstCell.equals(that.firstCell) : that.firstCell == null;

	}

	@Override
	public int hashCode() {
		return firstCell != null ? firstCell.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "NobilityTrack{" +
				"firstCell=" + firstCell +
				'}';
	}
}