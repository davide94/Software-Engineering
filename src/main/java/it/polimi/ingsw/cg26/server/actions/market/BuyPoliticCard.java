package it.polimi.ingsw.cg26.server.actions.market;

import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.market.Sellable;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class BuyPoliticCard extends Buy {

	private PoliticCard politicCard;
	
	public BuyPoliticCard(PoliticCard politicCard) {
		this.politicCard=politicCard;
	}

	@Override
	public void apply(GameBoard gameBoard, Player currentPlayer) {
		
		Sellable buyedSellable = super.buy(gameBoard, currentPlayer, politicCard);
		if(buyedSellable.getClass() == this.politicCard.getClass()){
			PoliticCard buyedPoliticCard = (PoliticCard) buyedSellable;
			buyedPoliticCard.setPrice(0);
			currentPlayer.addPoliticCard(buyedPoliticCard);
		}

	}

}
