package it.polimi.ingsw.cg26.actions.market;

import it.polimi.ingsw.cg26.model.board.GameBoard;
import it.polimi.ingsw.cg26.model.player.Assistant;
import it.polimi.ingsw.cg26.model.player.Player;

public class SellAssistant extends Sell {

	public SellAssistant(String token, int price) {
		super(token, price);
	}

	@Override
	public void apply(GameBoard gameBoard, Player currentPlayer) {
		
		currentPlayer.takeAssistants(1);
		super.sell(gameBoard, currentPlayer, new Assistant());
	}

}
