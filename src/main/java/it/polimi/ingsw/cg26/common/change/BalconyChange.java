package it.polimi.ingsw.cg26.common.change;

import it.polimi.ingsw.cg26.common.dto.BalconyDTO;
import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;

public class BalconyChange extends ChangeDecorator {

	private BalconyDTO balconyDTO;
	
	private RegionDTO regionDTO;
	
	public BalconyChange(Change decoratedChange, BalconyDTO balconyDTO, RegionDTO regionDTO){
		super(decoratedChange);
		this.balconyDTO = balconyDTO;
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
		region.setBalcony(this.balconyDTO);
	}
	
}
