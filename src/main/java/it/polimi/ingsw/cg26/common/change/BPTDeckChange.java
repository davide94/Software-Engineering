package it.polimi.ingsw.cg26.common.change;

import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;
import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDeckDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;

public class BPTDeckChange extends ChangeDecorator {

	private static final long serialVersionUID = 4492908129334448776L;

	private BusinessPermissionTileDeckDTO bPTDeckState;
	
	private RegionDTO regionDTO;
	
	public BPTDeckChange(Change decoratedChange, BusinessPermissionTileDeckDTO bPTDeckState, RegionDTO regionDTO) {
		super(decoratedChange);
		this.bPTDeckState = bPTDeckState;
		this.regionDTO = regionDTO;
	}
	
	@Override
	public void apply(GameBoardDTO gameGameBoardDTO) {
		super.apply(gameGameBoardDTO);
		RegionDTO region = null;
		for(RegionDTO iterRegionDTO : gameGameBoardDTO.getRegions()){
			if(iterRegionDTO.equals(this.regionDTO)){
				region = iterRegionDTO;
				break;
			}
		}
		if(region == null)
			throw new NullPointerException();
		region.setDeck(bPTDeckState);
	}

}
