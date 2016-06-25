package it.polimi.ingsw.cg26.common.update.change;

import java.util.Map;

import it.polimi.ingsw.cg26.common.ClientModel;
import it.polimi.ingsw.cg26.common.dto.CityColorDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCityException;
import it.polimi.ingsw.cg26.server.exceptions.InvalidRegionException;
import it.polimi.ingsw.cg26.server.exceptions.PlayerNotFoundException;

public class ColorBonusChange extends ChangeDecorator {

	private static final long serialVersionUID = -4828350583775148820L;
	
	/**
	 * The new colorBonuses to set
	 */
	private Map<CityColorDTO, BonusDTO> colorBonuses;
	
	/**
	 * Constructs a change of the color of the cities bonuses State
	 * @param decoratedChange the change to decorate
	 * @param colorBonuses the new map with new states of the colorBonuses
	 * @throws NullPointerException if one or more arguments are null
	 */
	public ColorBonusChange(Change decoratedChange, Map<CityColorDTO, BonusDTO> colorBonuses) {
		super(decoratedChange);
		if(colorBonuses == null)
			throw new NullPointerException();
		this.colorBonuses = colorBonuses;
	}

	@Override
	public void apply(ClientModel model) throws InvalidRegionException, InvalidCityException, PlayerNotFoundException {
		super.apply(model);
		model.setColorBonuses(colorBonuses);
	}
}
