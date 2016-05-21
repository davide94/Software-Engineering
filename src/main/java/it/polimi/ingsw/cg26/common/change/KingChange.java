package it.polimi.ingsw.cg26.common.change;

import it.polimi.ingsw.cg26.common.state.BoardState;
import it.polimi.ingsw.cg26.common.state.KingState;

public class KingChange implements Change {

	private KingState kingState;
	
	public KingChange(KingState kingState) {
		this.kingState = kingState;
	}
	
	@Override
	public void applyChange(BoardState gameBoardState) {
		KingState oldKing = gameBoardState.getKing();
		oldKing.setCurrentCity(this.kingState.getCurrentCity());
	}

}
