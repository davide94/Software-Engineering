package it.polimi.ingsw.cg26.actions.market;

import it.polimi.ingsw.cg26.actions.Action;
import it.polimi.ingsw.cg26.model.board.GameBoard;
import it.polimi.ingsw.cg26.model.market.Sellable;
import it.polimi.ingsw.cg26.model.player.Player;;

public abstract class Sell extends Action {
	
	private int price;
	
	public Sell(String token, int price){
		super(token);
		this.price = price;
	}
	
	@Override
	public abstract void apply(GameBoard gameBoard, Player currentPlayer);
	
	public void sell(GameBoard gameBoard, Player currentPlayer, Sellable sellable){
		
		sellable.setOwner(currentPlayer);
		sellable.setPrice(this.price);
		gameBoard.getMarket().addToMarket(sellable);
	}

}
