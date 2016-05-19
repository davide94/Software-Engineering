package it.polimi.ingsw.cg26.server.actions.market;

import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class SellPoliticCard extends Sell {

	private PoliticCard politicCard;
	
	public SellPoliticCard(PoliticCard politicCard, int price) {
		super(price);
		if(politicCard == null)
				throw new NullPointerException();
		this.politicCard = politicCard;
	}

	@Override
	public void apply(GameBoard gameBoard, Player currentPlayer) {
		
		PoliticCard cardToSell = currentPlayer.takeCard(politicCard);
		super.sell(gameBoard, cardToSell);
	}
	
	

}
