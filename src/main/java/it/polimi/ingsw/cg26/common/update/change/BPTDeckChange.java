package it.polimi.ingsw.cg26.common.update.change;

import it.polimi.ingsw.cg26.common.ClientModel;
import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDeckDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCityException;
import it.polimi.ingsw.cg26.server.exceptions.InvalidRegionException;
import it.polimi.ingsw.cg26.server.exceptions.PlayerNotFoundException;

public class BPTDeckChange extends ChangeDecorator {

	private static final long serialVersionUID = 4492908129334448776L;

	/**
	 * The new BPT deck to set
	 */
	private BusinessPermissionTileDeckDTO bPTDeckState;
	
	/**
	 * The Region of the BPT deck to set
	 */
	private RegionDTO regionDTO;
	
	/**
	 * Constructs a change of the BPT Deck
	 * @param decoratedChange the decorated change
	 * @param bPTDeckState the new State of the deck to apply
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
	public void apply(ClientModel model) throws InvalidRegionException, InvalidCityException, PlayerNotFoundException {
		super.apply(model);
		for(RegionDTO iterRegionDTO : model.getRegions()){
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
