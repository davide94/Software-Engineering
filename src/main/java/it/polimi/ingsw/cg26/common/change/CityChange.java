package it.polimi.ingsw.cg26.common.change;

import it.polimi.ingsw.cg26.common.state.BoardState;
import it.polimi.ingsw.cg26.common.state.CityState;
import it.polimi.ingsw.cg26.common.state.RegionState;

public class CityChange implements Change {

	private CityState cityState;
	
	public CityChange(CityState cityState) {
		this.cityState = cityState;
	}
	
	@Override
	public void applyChange(BoardState gameBoardState) {
		CityState city = null;
		for(RegionState iterRegionState : gameBoardState.getRegions()){
			for(CityState iterCityState : iterRegionState.getCities()){
				if(iterCityState.getName().equals(cityState.getName())){
					city = iterCityState;
					break;
				}
			}
			if(city != null)
				break;
		}
		if(city == null)
			throw new NullPointerException();
		city.setEmporiums(this.cityState.getEmporiums());
	}

}
