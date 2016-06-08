package it.polimi.ingsw.cg26.server.actions.market;

import it.polimi.ingsw.cg26.common.update.PrivateUpdate;
import it.polimi.ingsw.cg26.common.update.change.BasicChange;
import it.polimi.ingsw.cg26.common.update.change.Change;
import it.polimi.ingsw.cg26.common.update.change.LocalPlayerChange;
import it.polimi.ingsw.cg26.common.update.change.MarketChange;
import it.polimi.ingsw.cg26.common.update.change.PlayersChange;
import it.polimi.ingsw.cg26.common.dto.SellableDTO;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.NotEnoughMoneyException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.market.Sellable;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class Buy extends Action {

	private SellableDTO sellable;
	
	private Player oldOwner;
	
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
	 * @throws NotEnoughMoneyException if the player hasn't got enough money to buy the sellable
	 */
	@Override
	public void apply(GameBoard gameBoard){
		Player currentPlayer = gameBoard.getCurrentPlayer();
		Sellable realSellable = gameBoard.getMarket().getRealSellable(sellable);
		if(currentPlayer.getCoinsNumber()<realSellable.getPrice())
			throw new NotEnoughMoneyException();
		oldOwner = realSellable.getOwner();
		oldOwner.addCoins(realSellable.getPrice());
		currentPlayer.removeCoins(realSellable.getPrice());
		gameBoard.getMarket().removeFromMarket(realSellable);
		realSellable.setPrice(0);
		realSellable.giveToNewOwner(currentPlayer);
		notifyChange(gameBoard);
	}

	@Override
	public void notifyChange(GameBoard gameBoard) {
		Change change = new PlayersChange(new MarketChange(new BasicChange(), gameBoard.getMarket().getState()), oldOwner.getState());
		notifyDecoratingPlayersChange(gameBoard, change);
		Change privateOldOwnerChange = new LocalPlayerChange(new BasicChange(), oldOwner.getFullState());
		gameBoard.notifyObservers(new PrivateUpdate(privateOldOwnerChange, oldOwner.getToken()));
	}

}
