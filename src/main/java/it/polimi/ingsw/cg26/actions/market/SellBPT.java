package it.polimi.ingsw.cg26.actions.market;

import it.polimi.ingsw.cg26.model.board.GameBoard;
import it.polimi.ingsw.cg26.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.model.player.Player;

public class SellBPT extends Sell {

	private BusinessPermissionTile bpTile;
	
	public SellBPT(String token, int price, BusinessPermissionTile bpTile) {
		super(token, price);
		this.bpTile = bpTile;
	}

	@Override
	public void apply(GameBoard gameBoard, Player currentPlayer) {
		
		currentPlayer.useBPT(this.bpTile);
		super.sell(gameBoard, this.bpTile);
	}

}
