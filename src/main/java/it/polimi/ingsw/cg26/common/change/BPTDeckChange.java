package it.polimi.ingsw.cg26.common.change;

import it.polimi.ingsw.cg26.common.state.BoardState;
import it.polimi.ingsw.cg26.common.state.BusinessPermissionTileDeckState;
import it.polimi.ingsw.cg26.common.state.RegionState;

public class BPTDeckChange implements Change {

	private BusinessPermissionTileDeckState bPTDeckState;
	
	private RegionState regionState;
	
	public BPTDeckChange(BusinessPermissionTileDeckState bPTDeckState, RegionState regionState) {
		this.bPTDeckState = bPTDeckState;
		this.regionState = regionState;
	}
	
	@Override
	public void applyChange(BoardState gameBoardState) {
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
