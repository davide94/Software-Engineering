package it.polimi.ingsw.cg26.actions.market;

import it.polimi.ingsw.cg26.model.board.GameBoard;
import it.polimi.ingsw.cg26.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.model.player.Player;

public class SellPoliticCard extends Sell {

	private PoliticColor politicCardColor;
	
	public SellPoliticCard(String token, PoliticColor politicCardColor, int price) {
		super(token, price);
		if(politicCardColor == null)
				throw new NullPointerException();
		this.politicCardColor = politicCardColor;
	}

	@Override
	public void apply(GameBoard gameBoard, Player currentPlayer) {
		
		PoliticCard cardToSell = currentPlayer.takeCard(politicCardColor);
		super.sell(gameBoard, currentPlayer, cardToSell);
	}
	
	

}
