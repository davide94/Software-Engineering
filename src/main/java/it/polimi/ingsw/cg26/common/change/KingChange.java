package it.polimi.ingsw.cg26.common.change;

import it.polimi.ingsw.cg26.common.state.BoardState;
import it.polimi.ingsw.cg26.common.state.KingState;

public class KingChange extends ChangeDecorator {

	private KingState kingState;
	
	public KingChange(Change decoratedChange, KingState kingState) {
		super(decoratedChange);
		this.kingState = kingState;
	}
	
	@Override
	public void apply(BoardState gameBoardState) {
		super.apply(gameBoardState);
		KingState oldKing = gameBoardState.getKing();
		oldKing.setCurrentCity(this.kingState.getCurrentCity());
	}

}
