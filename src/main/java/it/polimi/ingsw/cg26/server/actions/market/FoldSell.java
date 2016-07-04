package it.polimi.ingsw.cg26.server.actions.market;

import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.IllegalMarketStateException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;

public class FoldSell extends Action {

	
	/**
	 * Finish to sell
	 * @param token is the token of the player
	 */
	public FoldSell(long token) {
		super(token);
	}

	
	/**
	 * Apply the choice to fold the sale
	 */
	@Override
	public void apply(GameBoard gameBoard) throws IllegalMarketStateException {
		if(!gameBoard.getScheduler().canSell(getToken()))
			throw new IllegalMarketStateException("is not your turn.");
		gameBoard.getScheduler().foldSell();
	}

	@Override
	public void notifyChange(GameBoard gameBoard) {
		//nothing to do here
	}
}
