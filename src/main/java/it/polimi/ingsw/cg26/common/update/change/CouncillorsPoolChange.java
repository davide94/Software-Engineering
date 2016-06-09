package it.polimi.ingsw.cg26.common.update.change;

import java.util.Collection;

import it.polimi.ingsw.cg26.common.ClientModel;
import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCityException;
import it.polimi.ingsw.cg26.server.exceptions.InvalidRegionException;
import it.polimi.ingsw.cg26.server.exceptions.PlayerNotFoundException;

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
	public void apply(ClientModel model) throws InvalidRegionException, InvalidCityException, PlayerNotFoundException {
		super.apply(model);
		model.setCouncillorsPool(councillorsPoolState);
	}

}
