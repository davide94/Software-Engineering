package it.polimi.ingsw.cg26.server.actions.market;

import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.market.Sellable;
;

public abstract class Sell extends Action {
	
	private int price;
	
	public Sell(int price){
		if(price<1)
			throw new IllegalArgumentException();
		this.price = price;
	}
	
	@Override
	public abstract void apply(GameBoard gameBoard);
	
	public void sell(GameBoard gameBoard, Sellable sellable){
		sellable.setOwner(gameBoard.getCurrentPlayer());
		sellable.setPrice(this.price);
		gameBoard.getMarket().addToMarket(sellable);
	}

}
