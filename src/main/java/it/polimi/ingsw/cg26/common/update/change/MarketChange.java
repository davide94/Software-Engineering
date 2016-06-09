package it.polimi.ingsw.cg26.common.update.change;

import it.polimi.ingsw.cg26.common.ClientModel;
import it.polimi.ingsw.cg26.common.dto.MarketDTO;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCityException;
import it.polimi.ingsw.cg26.server.exceptions.InvalidRegionException;
import it.polimi.ingsw.cg26.server.exceptions.PlayerNotFoundException;

public class MarketChange extends ChangeDecorator {

	private static final long serialVersionUID = 6096169655114721979L;
	
	private MarketDTO marketDTO;

	/**
	 * Construct a change for the market
	 * @param decoratedChange the change to decorate
	 */
	public MarketChange(Change decoratedChange, MarketDTO marketDTO) {
		super(decoratedChange);
		this.marketDTO = marketDTO;
	}
	
	@Override
	public void apply(ClientModel model) throws InvalidRegionException, InvalidCityException, PlayerNotFoundException {
		super.apply(model);
		model.setMarket(marketDTO);
	}

}
