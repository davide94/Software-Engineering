package it.polimi.ingsw.cg26.server.actions;

import it.polimi.ingsw.cg26.common.state.CouncillorState;
import it.polimi.ingsw.cg26.common.state.RegionState;
import it.polimi.ingsw.cg26.server.exceptions.NotExistingCouncillorException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.board.Councillor;

/**
 *
 */
public class Elect extends Action {

	private final RegionState region;

    private final CouncillorState councillor;

    /**
     * 
     * @param region
     * @param councillor
     * @throws NullPointerException if one or more arguments are null
     */
    public Elect(RegionState region, CouncillorState councillor) {
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
