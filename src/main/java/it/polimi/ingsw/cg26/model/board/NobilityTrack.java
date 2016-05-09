package it.polimi.ingsw.cg26.model.board;

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
	public NobilityTrack(NobilityCell firstCell) {
		if (firstCell == null)
			throw new NullPointerException();
		this.firstCell = firstCell;
	}

	/**
	 *
	 * @return
     */
	public NobilityCell getFirstCell() {
		return firstCell;
	}
}