package it.polimi.ingsw.cg26.common.update.change;

import it.polimi.ingsw.cg26.common.ClientModel;
import it.polimi.ingsw.cg26.common.dto.KingDTO;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCityException;
import it.polimi.ingsw.cg26.server.exceptions.InvalidRegionException;
import it.polimi.ingsw.cg26.server.exceptions.PlayerNotFoundException;

public class KingChange extends ChangeDecorator {

	private static final long serialVersionUID = 5707210211297999062L;

	/**
	 * The new King to set
	 */
	private KingDTO kingDTO;
	
	/**
	 * Constructs a change of the king's State
	 * @param decoratedChange the change to decorate
	 * @param kingDTO the new State of the king
	 * @throws NullPointerException if one or more arguments are null
	 */
	public KingChange(Change decoratedChange, KingDTO kingDTO) {
		super(decoratedChange);
		if(kingDTO == null)
			throw new NullPointerException();
		this.kingDTO = kingDTO;
	}
	
	@Override
	public void apply(ClientModel model) throws InvalidRegionException, InvalidCityException, PlayerNotFoundException {
		super.apply(model);
		model.setKing(kingDTO);
	}

}
