package it.polimi.ingsw.cg26.server.actions.market;

import it.polimi.ingsw.cg26.common.dto.SellableDTO;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.NotEnoughMoneyException;
import it.polimi.ingsw.cg26.server.exceptions.InvalidSellableException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.market.Sellable;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class Buy extends Action {

	SellableDTO sellable;
	
	/**
	 * Construct a simple action buy
	 */
	public Buy(long token, SellableDTO sellable) {
		super(token);
		if(sellable == null)
			throw new NullPointerException();
		this.sellable = sellable;
	}
	
	/**
	 * Take the given sellable from the market and gives it to its new owner, also manage the payment
	 * @param gameBoard the game board in which the action is applied
	 * @param currentPlayer the player that wants to buy the sellable
	 * @param sellable the sellable object that the player wants to buy
	 * @return the sellable removed from the market, bought by the player
	 * @throws InvalidSellableException if the sellable given by the player isn't in the market
	 * @throws NotEnoughMoneyException if the player hasn't got enough money to pay the sellable
	 */
	public Sellable buy(GameBoard gameBoard, Player currentPlayer, Sellable sellable){		
		if(sellable == null)
			throw new InvalidSellableException();
		if(currentPlayer.getCoinsNumber()<sellable.getPrice())
			throw new NotEnoughMoneyException();
		sellable.getOwner().addCoins(sellable.getPrice());
		currentPlayer.removeCoins(sellable.getPrice());
		return gameBoard.getMarket().removeFromMarket(sellable);	
	}

	@Override
	public void apply(GameBoard gameBoard){
		Player currentPlayer = gameBoard.getCurrentPlayer();
		Sellable realSellable = gameBoard.getMarket().getRealSellable(sellable);
		Sellable boughtSellable = this.buy(gameBoard, currentPlayer, realSellable);
		boughtSellable.setPrice(0);
		boughtSellable.giveToNewOwner(currentPlayer);
	}

	@Override
	public void notifyChange(GameBoard gameBoard) {
		// TODO Auto-generated method stub
		
	}

}
