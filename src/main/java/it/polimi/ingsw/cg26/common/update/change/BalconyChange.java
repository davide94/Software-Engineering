package it.polimi.ingsw.cg26.common.update.change;

import it.polimi.ingsw.cg26.common.ClientModel;
import it.polimi.ingsw.cg26.common.dto.BalconyDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCityException;
import it.polimi.ingsw.cg26.server.exceptions.InvalidRegionException;
import it.polimi.ingsw.cg26.server.exceptions.PlayerNotFoundException;

public class BalconyChange extends ChangeDecorator {

	private static final long serialVersionUID = 9200814381675839335L;

	/**
	 * The new Balcony to set
	 */
	private BalconyDTO balconyDTO;
	
	/**
	 * The Region of the Balcony to set
	 */
	private RegionDTO regionDTO;
	
	/**
	 * Constructs a change of the balcony of a region
	 * @param decoratedChange the change to decorate
	 * @param balconyDTO the new State of the balcony
	 * @param regionDTO the region in which the change has to be applied
	 * @throws NullPointerException if one or more arguments are null
	 */
	public BalconyChange(Change decoratedChange, BalconyDTO balconyDTO, RegionDTO regionDTO){
		super(decoratedChange);
		if(balconyDTO == null || regionDTO == null)
			throw new NullPointerException();
		this.balconyDTO = balconyDTO;
		this.regionDTO = regionDTO;
	}

	@Override
	public void apply(ClientModel model) throws InvalidRegionException, InvalidCityException, PlayerNotFoundException {
		super.apply(model);
		for(RegionDTO iterRegionDTO : model.getRegions()){
			if(iterRegionDTO.equals(this.regionDTO)){
				iterRegionDTO.setBalcony(balconyDTO);
				balconyDTO = null;
				break;
			}
		}
		if(balconyDTO != null)
			throw new InvalidRegionException();
	}
	
}
