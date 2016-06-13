package it.polimi.ingsw.cg26.common.update.change;

import it.polimi.ingsw.cg26.common.ClientModel;
import it.polimi.ingsw.cg26.common.dto.BalconyDTO;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCityException;
import it.polimi.ingsw.cg26.server.exceptions.InvalidRegionException;
import it.polimi.ingsw.cg26.server.exceptions.PlayerNotFoundException;

public class KingBalconyChange extends ChangeDecorator {

	private static final long serialVersionUID = -2091081956592374355L;

	private BalconyDTO kingBalcony;
	
	/**
	 * Construct a change of the king's balcony
	 * @param decoratedChange the change to decorate
	 * @param kingBalcony the new State of the balcony
	 */
	public KingBalconyChange(Change decoratedChange, BalconyDTO kingBalcony) {
		super(decoratedChange);
		if(kingBalcony == null)
			throw new NullPointerException();
		this.kingBalcony = kingBalcony;
	}
	
	@Override
	public void apply(ClientModel model) throws InvalidRegionException, InvalidCityException, PlayerNotFoundException {
		super.apply(model);
		model.setKingBalcony(kingBalcony);
	}

}
