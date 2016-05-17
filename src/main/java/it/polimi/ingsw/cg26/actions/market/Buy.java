package it.polimi.ingsw.cg26.actions.market;

import it.polimi.ingsw.cg26.actions.Action;
import it.polimi.ingsw.cg26.exceptions.NotEnoughMoneyException;
import it.polimi.ingsw.cg26.model.board.GameBoard;
import it.polimi.ingsw.cg26.model.market.Sellable;
import it.polimi.ingsw.cg26.model.player.Player;

public abstract class Buy extends Action {

	public Buy(String token) {
		super(token);
	}

	@Override
	public abstract void apply(GameBoard gameBoard, Player currentPlayer);
	
	public Sellable buy(GameBoard gameBoard, Player currentPlayer, Sellable sellable){
		
		if(currentPlayer.getCoinsNumber()<sellable.getPrice())
			throw new NotEnoughMoneyException();
		sellable.getOwner().addCoins(sellable.getPrice());
		currentPlayer.removeCoins(sellable.getPrice());
		return gameBoard.getMarket().removeFromMarket(sellable);	
	}

}
