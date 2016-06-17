package it.polimi.ingsw.cg26.server.actions;

import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.server.exceptions.CouncillorNotFoundException;
import it.polimi.ingsw.cg26.server.model.board.Councillor;

import java.util.Collection;

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
	 * Search the real councillor in the pool that has to match with the councillor DTO
	 * @param councillorsPool is the pool of councillors that are not in any balcony
	 * @return the real councillor
	 * @throws CouncillorNotFoundException if the real councillor is not present in pool councillors
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
