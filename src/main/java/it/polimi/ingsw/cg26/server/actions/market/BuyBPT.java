package it.polimi.ingsw.cg26.server.actions.market;

import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.server.model.market.Sellable;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class BuyBPT extends Buy {

	private BusinessPermissionTile bpTile;
	
	public BuyBPT(BusinessPermissionTile bpTile) {
		this.bpTile = bpTile;
	}

	@Override
	public void apply(GameBoard gameBoard, Player currentPlayer) {
		
		Sellable buyedSellable = super.buy(gameBoard, currentPlayer, bpTile);
		if(buyedSellable.getClass() == this.bpTile.getClass()){
			BusinessPermissionTile buyedBPT = (BusinessPermissionTile) buyedSellable;
			buyedBPT.setPrice(0);
			currentPlayer.addPermissionTile(buyedBPT);
		}
	}
}
