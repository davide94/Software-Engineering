package it.polimi.ingsw.cg26.server.actions.market;

import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.market.Sellable;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class BuyAssistant extends Buy {

	/**
	 * Simple constructor of the action, does nothing
	 */
	public BuyAssistant() {
		//the class only represent the action, the assistant is an immutable object so is created when needed
	}

	@Override
	public void apply(GameBoard gameBoard) {
		Player currentPlayer = gameBoard.getCurrentPlayer();
		Sellable buyedSellable = super.buy(gameBoard, currentPlayer, new Assistant());
		if(buyedSellable.getClass() == Assistant.class){
			Assistant buyedAssistant = (Assistant) buyedSellable;
			buyedAssistant.setPrice(0);
			currentPlayer.addAssistant(buyedAssistant);
		}
	}

}
