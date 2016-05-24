package it.polimi.ingsw.cg26.server.actions.market;

import it.polimi.ingsw.cg26.common.state.BusinessPermissionTileState;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.server.model.market.Sellable;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class BuyBPT extends Buy {

	/**
	 * The Business Permit Tile the player wants to buy
	 */
	private BusinessPermissionTileState bpTileState;
	
	/**
	 * Create the action to buy a Business permit tile
	 * @param bpTile
	 */
	public BuyBPT(BusinessPermissionTileState bpTile) {
		this.bpTileState = bpTile;
	}

	@Override
	public void apply(GameBoard gameBoard) {
		Player currentPlayer = gameBoard.getCurrentPlayer();
		Sellable bpTile = gameBoard.getMarket().getRealSellable(bpTileState);
		Sellable buyedSellable = super.buy(gameBoard, currentPlayer, bpTile);
		if(buyedSellable.getClass() == bpTile.getClass()){
			BusinessPermissionTile buyedBPT = (BusinessPermissionTile) buyedSellable;
			buyedBPT.setPrice(0);
			currentPlayer.addPermissionTile(buyedBPT);
		}
	}
}
