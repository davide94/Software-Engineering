package it.polimi.ingsw.cg26.server.actions.market;

import it.polimi.ingsw.cg26.common.state.BusinessPermissionTileState;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class SellBPT extends Sell {

	private BusinessPermissionTileState bpTileState;
	
	public SellBPT(int price, BusinessPermissionTileState bpTileState) {
		super(price);
		this.bpTileState = bpTileState;
	}

	@Override
	public void apply(GameBoard gameBoard) {
		Player currentPlayer = gameBoard.getCurrentPlayer();
		BusinessPermissionTile bpTile = currentPlayer.getRealBPT(bpTileState);
		super.sell(gameBoard, bpTile);
	}

}
