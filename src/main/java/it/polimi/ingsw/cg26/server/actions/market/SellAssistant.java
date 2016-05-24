package it.polimi.ingsw.cg26.server.actions.market;

import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class SellAssistant extends Sell {

	/**
	 * Construct an action to sell an Assistant
	 * @param price the price to set to the Assistant
	 * @throws IllegalArgumentException if the price is less than 1
	 */
	public SellAssistant(int price) {
		super(price);
	}

	@Override
	public void apply(GameBoard gameBoard) {
		Player currentPlayer = gameBoard.getCurrentPlayer();
		currentPlayer.takeAssistants(1);
		Assistant assistantToAdd = new Assistant();
		super.sell(gameBoard, assistantToAdd);
	}

}
