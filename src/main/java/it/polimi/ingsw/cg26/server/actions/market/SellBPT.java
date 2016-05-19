package it.polimi.ingsw.cg26.server.actions.market;

import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class SellBPT extends Sell {

	private BusinessPermissionTile bpTile;
	
	public SellBPT(int price, BusinessPermissionTile bpTile) {
		super(price);
		this.bpTile = bpTile;
	}

	@Override
	public void apply(GameBoard gameBoard, Player currentPlayer) {
		
		currentPlayer.useBPT(this.bpTile);
		super.sell(gameBoard, this.bpTile);
	}

}
