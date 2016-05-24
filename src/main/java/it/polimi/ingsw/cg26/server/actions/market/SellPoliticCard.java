package it.polimi.ingsw.cg26.server.actions.market;

import it.polimi.ingsw.cg26.common.state.PoliticCardState;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class SellPoliticCard extends Sell {

	private PoliticCardState politicCard;
	
	public SellPoliticCard(PoliticCardState politicCard, int price) {
		super(price);
		if(politicCard == null)
				throw new NullPointerException();
		this.politicCard = politicCard;
	}

	@Override
	public void apply(GameBoard gameBoard) {
		Player currentPlayer = gameBoard.getCurrentPlayer();
		PoliticCard cardToSell = currentPlayer.takeCard(this.politicCard);
		super.sell(gameBoard, cardToSell);
	}
}
