package it.polimi.ingsw.cg26.server.actions.market;

import it.polimi.ingsw.cg26.server.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class SellAssistant extends Sell {

	/**
	 * Construct an action to sell an Assistant
	 * @param price the price to set to the Assistant
	 * @throws IllegalArgumentException if the price is less than 1
	 */
	public SellAssistant(int price, long token) {
		super(price, token);
	}

	
	/**
	 * Apply the sale
	 */
	@Override
	public void apply(GameBoard gameBoard) throws NoRemainingAssistantsException {
		if(!gameBoard.getScheduler().canSell(getToken()))
			throw new IllegalStateException();
		Player currentPlayer = gameBoard.getCurrentPlayer();
		currentPlayer.takeAssistants(1);
		Assistant assistantToAdd = new Assistant();
		assistantToAdd.setOwner(currentPlayer);
		sell(gameBoard, assistantToAdd);
		notifyChange(gameBoard);
	}
}
