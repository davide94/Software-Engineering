package it.polimi.ingsw.cg26.server.actions;

import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.server.exceptions.NotExistingCouncillorException;
import it.polimi.ingsw.cg26.server.model.board.Councillor;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;

public abstract class Elect extends Action {

	/**
	 * The Councillor that the player wants to elect
	 */
    protected final CouncillorDTO councillor;
	
    /**
     * Construct a generic elect action
     * @param councillor is the councillor the player wants to elect 
     * @param token
     * @throws NullPointerException if the councillor is null
     */
	public Elect(CouncillorDTO councillor, long token) {
		super(token);
		if(councillor == null)
			throw new NullPointerException();
		this.councillor = councillor;
	}
	
	public Councillor getRealCouncillorFromPool(GameBoard gameBoard){
		for (Councillor c: gameBoard.getCouncillorsPool()) {
			if (c.getState().equals(councillor)) {
				return c;
			}
		}
		throw new NotExistingCouncillorException();
	}
}
