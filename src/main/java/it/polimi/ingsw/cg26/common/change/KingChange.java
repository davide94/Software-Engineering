package it.polimi.ingsw.cg26.common.change;

import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;
import it.polimi.ingsw.cg26.common.dto.KingDTO;

public class KingChange extends ChangeDecorator {

	private KingDTO kingDTO;
	
	public KingChange(Change decoratedChange, KingDTO kingDTO) {
		super(decoratedChange);
		this.kingDTO = kingDTO;
	}
	
	@Override
	public void apply(GameBoardDTO gameGameBoardDTO) {
		super.apply(gameGameBoardDTO);
		KingDTO oldKing = gameGameBoardDTO.getKing();
		oldKing.setCurrentCity(this.kingDTO.getCurrentCity());
	}

}
