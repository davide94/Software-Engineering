package it.polimi.ingsw.cg26.common.change;

import java.util.List;

import it.polimi.ingsw.cg26.common.state.BoardState;
import it.polimi.ingsw.cg26.common.state.CouncillorState;

public class CouncillorsPoolChange extends ChangeDecorator {

	private  List<CouncillorState> councillorsPoolState;
	
	public CouncillorsPoolChange(Change decoratedChange, List<CouncillorState> councillorsPoolState) {
		super(decoratedChange);
		this.councillorsPoolState = councillorsPoolState;
	}
	
	@Override
	public void applyChange(BoardState gameBoardState) {
		super.applyChange(gameBoardState);
		gameBoardState.setCouncillorsPool(councillorsPoolState);
	}

}
