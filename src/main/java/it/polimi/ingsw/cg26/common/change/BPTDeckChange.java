package it.polimi.ingsw.cg26.common.change;

import it.polimi.ingsw.cg26.common.state.BoardState;
import it.polimi.ingsw.cg26.common.state.BusinessPermissionTileDeckState;
import it.polimi.ingsw.cg26.common.state.RegionState;

public class BPTDeckChange extends ChangeDecorator {

	private BusinessPermissionTileDeckState bPTDeckState;
	
	private RegionState regionState;
	
	public BPTDeckChange(Change decoratedChange, BusinessPermissionTileDeckState bPTDeckState, RegionState regionState) {
		super(decoratedChange);
		this.bPTDeckState = bPTDeckState;
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
		region.setDeck(bPTDeckState);
	}

}
