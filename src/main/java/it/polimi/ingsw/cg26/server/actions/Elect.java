package it.polimi.ingsw.cg26.server.actions;

import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.server.exceptions.NotExistingCouncillorException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.board.Councillor;

/**
 *
 */
public abstract class Elect extends Action {

	/**
	 * The Region where the player wants to elect the councillor
	 */
	private final RegionDTO region;

	/**
	 * The Councillor that the player wants to elect
	 */
    private final CouncillorDTO councillor;

    /**
     * Construct an elect action
     * @param region is the region where the player wants to elect the councillor
     * @param councillor is the councillor that the player wants to elect
     * @throws NullPointerException if one or more arguments are null
     */
    public Elect(RegionDTO region, CouncillorDTO councillor, long token) {
		super(token);
        if (region == null || councillor == null)
            throw new NullPointerException();
        this.region = region;
        this.councillor = councillor;
    }

    /**
     * @throws NotExistingCouncillorException if the councillor selected by the user doesn't exist in the councillor pool
     */
    @Override
    public void apply(GameBoard gameBoard) {
		Councillor realCouncillor = null;
		for (Councillor c: gameBoard.getCouncillorsPool()) {
			if (c.getState().equals(councillor)) {
				realCouncillor = c;
				break;
			}
		}
		if (realCouncillor == null)
			throw new NotExistingCouncillorException();
		// TODO if king
    	Councillor droppedCouncillor = gameBoard.getRegion(region).getBalcony().elect(realCouncillor);
		gameBoard.getCouncillorsPool().remove(realCouncillor);
		gameBoard.getCouncillorsPool().add(droppedCouncillor);
    }

}
