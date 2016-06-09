package it.polimi.ingsw.cg26.server.actions.market;

import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;

public class FoldBuy extends Action {

	public FoldBuy(long token) {
		super(token);
	}

	@Override
	public void apply(GameBoard gameBoard) {
		gameBoard.foldBuy();
	}

	@Override
	public void notifyChange(GameBoard gameBoard) {
		//nothing to do here
	}

}
