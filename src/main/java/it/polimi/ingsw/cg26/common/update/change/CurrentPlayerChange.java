package it.polimi.ingsw.cg26.common.update.change;

import it.polimi.ingsw.cg26.common.ClientModel;
import it.polimi.ingsw.cg26.common.dto.PlayerDTO;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCityException;
import it.polimi.ingsw.cg26.server.exceptions.InvalidRegionException;
import it.polimi.ingsw.cg26.server.exceptions.PlayerNotFoundException;

public class CurrentPlayerChange extends ChangeDecorator {

	private static final long serialVersionUID = -7382652511433362486L;

	private PlayerDTO currentPlayer;
	
	/**
	 * Construct a change for the Current Player
	 * @param decoratedChange the change to decorate
	 * @param currentPlayer the new current player to set
	 */
	public CurrentPlayerChange(Change decoratedChange, PlayerDTO currentPlayer) {
		super(decoratedChange);
		if(currentPlayer == null)
			throw new NullPointerException();
		this.currentPlayer = currentPlayer;
	}
	
	@Override
	public void apply(ClientModel model) throws InvalidCityException, InvalidRegionException, PlayerNotFoundException {
		super.apply(model);
		model.setCurrentPlayer(currentPlayer);
	}

}
