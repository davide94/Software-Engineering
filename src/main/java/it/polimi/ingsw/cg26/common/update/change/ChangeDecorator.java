package it.polimi.ingsw.cg26.common.update.change;

import it.polimi.ingsw.cg26.common.ClientModel;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCityException;
import it.polimi.ingsw.cg26.server.exceptions.InvalidRegionException;
import it.polimi.ingsw.cg26.server.exceptions.PlayerNotFoundException;

public abstract class ChangeDecorator implements Change {

	private static final long serialVersionUID = 3146183584551937856L;

	/**
	 * The change that has to be decorated
	 */
	private Change decoratedChange;
	
	public ChangeDecorator(Change decoratedChange) {
		if(decoratedChange == null)
			throw new NullPointerException();
		this.decoratedChange = decoratedChange;
	}
	
	@Override
	public void apply(ClientModel model) throws InvalidRegionException, InvalidCityException, PlayerNotFoundException {
		decoratedChange.apply(model);
	}
}
