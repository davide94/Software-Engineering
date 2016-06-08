package it.polimi.ingsw.cg26.common.change;

import it.polimi.ingsw.cg26.common.ClientModel;
import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;
import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCityException;

public class CityChange extends ChangeDecorator {

	private static final long serialVersionUID = 5382493602096067408L;

	private CityDTO cityDTO;
	
	/**
	 * Constructs a change for a city
	 * @param decoratedChange the change to decorate
	 * @param cityDTO the city to change
	 * @throws NullPointerException if one or more arguments are null
	 */
	public CityChange(Change decoratedChange, CityDTO cityDTO) {
		super(decoratedChange);
		if(cityDTO == null)
			throw new NullPointerException();
		this.cityDTO = cityDTO;
	}
	
	@Override
	public void apply(ClientModel model) {
		super.apply(model);
		CityDTO city = null;
		for(RegionDTO iterRegionDTO : model.getRegions()){
			for(CityDTO iterCityDTO : iterRegionDTO.getCities()){
				if(iterCityDTO.getName().equals(cityDTO.getName())){
					city = iterCityDTO;
					break;
				}
			}
			if(city != null)
				break;
		}
		if(city == null)
			throw new InvalidCityException();
		city.setEmporiums(this.cityDTO.getEmporiums());
	}

}
