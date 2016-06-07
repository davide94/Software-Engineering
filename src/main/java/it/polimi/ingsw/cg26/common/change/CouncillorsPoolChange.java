package it.polimi.ingsw.cg26.common.change;

import java.util.Collection;

import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;
import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;

public class CouncillorsPoolChange extends ChangeDecorator {

	private static final long serialVersionUID = 2109057430783266087L;

	private  Collection<CouncillorDTO> councillorsPoolState;
	
	/**
	 * Constructs a change for the councillor's pool
	 * @param decoratedChange the change to decorate
	 * @param councillorsPoolState the pool to change
	 * @throws NullPointerException if one or more arguments are null
	 */
	public CouncillorsPoolChange(Change decoratedChange, Collection<CouncillorDTO> councillorsPoolState) {
		super(decoratedChange);
		if(councillorsPoolState == null)
			throw new NullPointerException();
		this.councillorsPoolState = councillorsPoolState;
	}
	
	@Override
	public void apply(GameBoardDTO gameGameBoardDTO) {
		super.apply(gameGameBoardDTO);
		gameGameBoardDTO.setCouncillorsPool(councillorsPoolState);
	}

}
