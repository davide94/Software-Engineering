package it.polimi.ingsw.cg26.server.actions.market;

import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.market.Sellable;
;

public abstract class Sell extends Action {
	
	/**
	 * The price the player wants to set to the sellable to sell 
	 */
	private int price;
	
	/**
	 * Construct a simple sell action
	 * @param price the price to set to the sellable in the market
	 * @throws IllegalArgumentException if the price is less than 1
	 */
	public Sell(int price, long token) {
		super(token);
		if(price<1)
			throw new IllegalArgumentException();
		this.price = price;
	}
	
	/**
	 * Take the given sellable from the player and puts it in the market, also sets the price 
	 * @param gameBoard the game board in which the action is applied
	 * @param sellable the sellable put in the market by the player
	 */
	public void sell(GameBoard gameBoard, Sellable sellable){
		sellable.setPrice(this.price);
		gameBoard.getMarket().addToMarket(sellable);
	}
	
	@Override
	public abstract void apply(GameBoard gameBoard);
	
	@Override
	public void notifyChange(GameBoard gameBoard){
		// TODO Auto-generated method stub
	}

}
