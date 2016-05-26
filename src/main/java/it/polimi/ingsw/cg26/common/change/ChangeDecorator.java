package it.polimi.ingsw.cg26.common.change;

import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;

public abstract class ChangeDecorator implements Change {

	private Change decoratedChange;
	
	public ChangeDecorator(Change decoratedChange) {
		this.decoratedChange = decoratedChange;
	}
	
	@Override
	public void apply(GameBoardDTO gameGameBoardDTO) {
		decoratedChange.apply(gameGameBoardDTO);
	}

}
