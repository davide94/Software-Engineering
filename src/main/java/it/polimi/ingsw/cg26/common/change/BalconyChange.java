package it.polimi.ingsw.cg26.common.change;

import it.polimi.ingsw.cg26.common.state.BalconyState;
import it.polimi.ingsw.cg26.common.state.BoardState;
import it.polimi.ingsw.cg26.common.state.RegionState;

public class BalconyChange extends ChangeDecorator {

	private BalconyState balconyState;
	
	private RegionState regionState;
	
	public BalconyChange(Change decoratedChange, BalconyState balconyState, RegionState regionState){
		super(decoratedChange);
		this.balconyState = balconyState;
		this.regionState = regionState;
	}

	@Override
	public void apply(BoardState gameBoardState) {
		super.apply(gameBoardState);
		RegionState region = null;
		for(RegionState iterRegionState : gameBoardState.getRegions()){
			if(iterRegionState.equals(this.regionState)){
				region = iterRegionState;
				break;
			}
		}
		if(region == null)
			throw new NullPointerException();
		region.setBalcony(this.balconyState);
	}
	
}
