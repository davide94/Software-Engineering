package it.polimi.ingsw.cg26.actions.market;

import it.polimi.ingsw.cg26.model.board.GameBoard;
import it.polimi.ingsw.cg26.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.model.market.Sellable;
import it.polimi.ingsw.cg26.model.player.Player;

public class BuyBPT extends Buy {

	private BusinessPermissionTile bpTile;
	
	public BuyBPT(String token,  BusinessPermissionTile bpTile) {
		super(token);
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
