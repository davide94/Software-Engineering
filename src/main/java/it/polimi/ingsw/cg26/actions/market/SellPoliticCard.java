package it.polimi.ingsw.cg26.actions.market;

import it.polimi.ingsw.cg26.model.board.GameBoard;
import it.polimi.ingsw.cg26.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.model.player.Player;

public class SellPoliticCard extends Sell {

	private PoliticCard politicCard;
	
	public SellPoliticCard(String token, PoliticCard politicCard, int price) {
		super(token, price);
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
