package it.polimi.ingsw.cg26.server.model.board;

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

	/**
	 *
	 * @return
     */
	public NobilityCell getFirstCell() {
		return firstCell;
	}
}