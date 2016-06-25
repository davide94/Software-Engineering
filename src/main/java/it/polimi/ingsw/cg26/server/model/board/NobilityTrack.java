package it.polimi.ingsw.cg26.server.model.board;

import it.polimi.ingsw.cg26.common.dto.NobilityCellDTO;
import it.polimi.ingsw.cg26.common.dto.NobilityTrackDTO;

import java.util.LinkedList;
import java.util.List;


public class NobilityTrack {

	/**
	 *the first cell of the nobility track
	 */
	private NobilityCell firstCell;

	/**
	 *Default constructor
	 */
	private NobilityTrack(NobilityCell firstCell) {
		if (firstCell == null)
			throw new NullPointerException();
		this.firstCell = firstCell;
	}

	
	/**
	 * Create a nobility track
	 * @param firstCell is the first nobility cell of the track
	 * @return a new nobility track
	 */
	public static NobilityTrack createNobilityTrack(NobilityCell firstCell) {
		return new NobilityTrack(firstCell);
	}

	
	/**
	 * Create a nobility track DTO
	 * @return the DTO of the nobility track
	 */
	public NobilityTrackDTO getState() {
		List<NobilityCellDTO> cellsState = new LinkedList<>();
		NobilityCell cell = firstCell;
		while (true) {
			cellsState.add(cell.getState());
			if (!cell.hasNext())
				break;
			cell = cell.next();
		}
		return new NobilityTrackDTO(cellsState);
	}

	
	
	/**
	 *Get the first nobility cell of the track
	 * @return the first of the track
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