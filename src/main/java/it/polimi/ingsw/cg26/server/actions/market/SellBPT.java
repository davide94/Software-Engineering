package it.polimi.ingsw.cg26.server.actions.market;

import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.common.update.event.MessageUpdate;
import it.polimi.ingsw.cg26.server.exceptions.IllegalMarketStateException;
import it.polimi.ingsw.cg26.server.exceptions.InvalidTileException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class SellBPT extends Sell {

	/**
	 * The Business Permit Tile the player wants to sell
	 */
	private BusinessPermissionTileDTO bpTileDTO;
	
	/**
	 * Construct an action to sell a Business Permit Tile
	 * @param price the price to set to the Business Permit Tile
	 * @param bpTileState the Tile that the player wants to sell
	 * @throws NullPointerException if one or more parameters are null
	 * @throws IllegalArgumentException if the price is less than 1
	 */
	public SellBPT(int price, BusinessPermissionTileDTO bpTileState, long token) {
		super(price, token);
		if(bpTileState == null)
			throw new NullPointerException();
		this.bpTileDTO = bpTileState;
	}

	
	/**
	 * Apply the sale
	 */
	@Override
	public void apply(GameBoard gameBoard) throws InvalidTileException, IllegalMarketStateException {
		if(!gameBoard.getScheduler().canSell(getToken()))
			throw new IllegalMarketStateException("is not your turn.");
		Player currentPlayer = gameBoard.getCurrentPlayer();
		BusinessPermissionTile bpTile = currentPlayer.removeRealBPT(bpTileDTO);
		bpTile.setOwner(currentPlayer);
		sell(gameBoard, bpTile);
		notifyChange(gameBoard);
		gameBoard.notifyObservers(new MessageUpdate(currentPlayer.getName(), "[Sold a Building Permit Tile for " + price +" coins]"));
	}
}
