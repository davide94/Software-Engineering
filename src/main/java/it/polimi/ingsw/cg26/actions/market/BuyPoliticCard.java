package it.polimi.ingsw.cg26.actions.market;

import it.polimi.ingsw.cg26.model.board.GameBoard;
import it.polimi.ingsw.cg26.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.model.market.Sellable;
import it.polimi.ingsw.cg26.model.player.Player;

public class BuyPoliticCard extends Buy {

	private PoliticCard politicCard;
	
	public BuyPoliticCard(String token, PoliticCard politicCard) {
		super(token);
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
