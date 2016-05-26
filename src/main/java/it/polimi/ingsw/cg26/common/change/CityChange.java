package it.polimi.ingsw.cg26.common.change;

import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;
import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;

public class CityChange extends ChangeDecorator {

	private static final long serialVersionUID = 5382493602096067408L;

	private CityDTO cityDTO;
	
	public CityChange(Change decoratedChange, CityDTO cityDTO) {
		super(decoratedChange);
		this.cityDTO = cityDTO;
	}
	
	@Override
	public void apply(GameBoardDTO gameGameBoardDTO) {
		super.apply(gameGameBoardDTO);
		CityDTO city = null;
		for(RegionDTO iterRegionDTO : gameGameBoardDTO.getRegions()){
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
			throw new NullPointerException();
		city.setEmporiums(this.cityDTO.getEmporiums());
	}

}
