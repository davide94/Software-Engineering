package it.polimi.ingsw.cg26.server.actions;

import java.util.Collection;

import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.server.exceptions.CouncillorNotFoundException;
import it.polimi.ingsw.cg26.server.model.board.Councillor;

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
	
	/**
	 * 
	 * @return
	 */
	public Councillor getRealCouncillorFromPool(Collection<Councillor> councillorsPool) throws CouncillorNotFoundException {
		for (Councillor c: councillorsPool) {
			if (c.getState().equals(councillor)) {
				return c;
			}
		}
		throw new CouncillorNotFoundException();
	}
}
