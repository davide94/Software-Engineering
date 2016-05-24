package it.polimi.ingsw.cg26.server.actions.market;

import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class SellAssistant extends Sell {

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
