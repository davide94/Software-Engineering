package it.polimi.ingsw.cg26.server.actions.market;

import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.NotEnoughMoneyException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.market.Sellable;
import it.polimi.ingsw.cg26.server.model.player.Player;

public abstract class Buy extends Action {

	public Buy() {

	}

	@Override
	public abstract void apply(GameBoard gameBoard);
	
	public Sellable buy(GameBoard gameBoard, Player currentPlayer, Sellable sellable){
		if(currentPlayer.getCoinsNumber()<sellable.getPrice())
			throw new NotEnoughMoneyException();
		sellable.getOwner().addCoins(sellable.getPrice());
		currentPlayer.removeCoins(sellable.getPrice());
		return gameBoard.getMarket().removeFromMarket(sellable);	
	}

}
