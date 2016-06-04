package it.polimi.ingsw.cg26.common.change;

import it.polimi.ingsw.cg26.common.dto.BalconyDTO;
import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.server.exceptions.InvalidRegionException;

public class BalconyChange extends ChangeDecorator {

	private static final long serialVersionUID = 9200814381675839335L;

	private BalconyDTO balconyDTO;
	
	private RegionDTO regionDTO;
	
	/**
	 * Constructs a change of the balcony of a region
	 * @param decoratedChange the change to decorate
	 * @param balconyDTO the new state of the balcony 
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
	public void apply(GameBoardDTO gameGameBoardDTO) {
		super.apply(gameGameBoardDTO);
		for(RegionDTO iterRegionDTO : gameGameBoardDTO.getRegions()){
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
