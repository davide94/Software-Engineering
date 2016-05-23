package it.polimi.ingsw.cg26.common.change;

import it.polimi.ingsw.cg26.common.state.BoardState;
import it.polimi.ingsw.cg26.common.state.CityState;
import it.polimi.ingsw.cg26.common.state.RegionState;

public class CityChange extends ChangeDecorator {

	private CityState cityState;
	
	public CityChange(Change decoratedChange, CityState cityState) {
		super(decoratedChange);
		this.cityState = cityState;
	}
	
	@Override
	public void apply(BoardState gameBoardState) {
		super.apply(gameBoardState);
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
