package it.polimi.ingsw.cg26.server.actions.market;

import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;

public class FoldBuy extends Action {

	
	/**
	 * Finish to buy items
	 * @param token is the token of the player
	 */
	public FoldBuy(long token) {
		super(token);
	}

	
	/**
	 * Apply the choice to fold purchase
	 */
	@Override
	public void apply(GameBoard gameBoard) {
		if(!gameBoard.getScheduler().canBuy(getToken()))
			throw new IllegalStateException("FoldBuyAction");
		gameBoard.getScheduler().foldedBuy();
	}

	@Override
	public void notifyChange(GameBoard gameBoard) {
		//nothing to do here
	}
}
