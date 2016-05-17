package it.polimi.ingsw.cg26.actions.market;

import it.polimi.ingsw.cg26.model.board.GameBoard;
import it.polimi.ingsw.cg26.model.market.Sellable;
import it.polimi.ingsw.cg26.model.player.Assistant;
import it.polimi.ingsw.cg26.model.player.Player;

public class BuyAssistant extends Buy {

	public BuyAssistant(String token) {
		super(token);
	}

	@Override
	public void apply(GameBoard gameBoard, Player currentPlayer) {
		
		Sellable buyedSellable = super.buy(gameBoard, currentPlayer, new Assistant());
		if(buyedSellable.getClass() == Assistant.class){
			Assistant buyedAssistant = (Assistant) buyedSellable;
			buyedAssistant.setPrice(0);
			currentPlayer.addAssistant(buyedAssistant);
		}

	}

}
