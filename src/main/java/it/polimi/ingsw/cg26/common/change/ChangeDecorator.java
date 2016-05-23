package it.polimi.ingsw.cg26.common.change;

import it.polimi.ingsw.cg26.common.state.BoardState;

public abstract class ChangeDecorator implements Change {

	private Change decoratedChange;
	
	public ChangeDecorator(Change decoratedChange) {
		this.decoratedChange = decoratedChange;
	}
	
	@Override
	public void apply(BoardState gameBoardState) {
		decoratedChange.apply(gameBoardState);
	}

}
