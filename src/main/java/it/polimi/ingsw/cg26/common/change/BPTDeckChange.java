package it.polimi.ingsw.cg26.common.change;

import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;
import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDeckDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.server.exceptions.InvalidRegionException;

public class BPTDeckChange extends ChangeDecorator {

	private static final long serialVersionUID = 4492908129334448776L;

	private BusinessPermissionTileDeckDTO bPTDeckState;
	
	private RegionDTO regionDTO;
	
	/**
	 * Constructs a change of the BPT Deck
	 * @param decoratedChange the decorated change
	 * @param bPTDeckState the new state of the deck to apply
	 * @param regionDTO the region in which the change has to be applied
	 * @throws NullPointerException if one or more arguments are null
	 */
	public BPTDeckChange(Change decoratedChange, BusinessPermissionTileDeckDTO bPTDeckState, RegionDTO regionDTO) {
		super(decoratedChange);
		if(bPTDeckState == null || regionDTO == null)
			throw new NullPointerException();
		this.bPTDeckState = bPTDeckState;
		this.regionDTO = regionDTO;
	}
	
	@Override
	public void apply(GameBoardDTO gameGameBoardDTO) {
		super.apply(gameGameBoardDTO);
		for(RegionDTO iterRegionDTO : gameGameBoardDTO.getRegions()){
			if(iterRegionDTO.equals(this.regionDTO)){
				iterRegionDTO.setDeck(bPTDeckState);
				bPTDeckState = null;
				break;
			}
		}
		if(bPTDeckState != null)
			throw new InvalidRegionException();
	}

}
