package it.polimi.ingsw.cg26.server.actions.market;

import it.polimi.ingsw.cg26.common.state.PoliticCardState;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.market.Sellable;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class BuyPoliticCard extends Buy {

	private PoliticCardState politicCardState;
	
	public BuyPoliticCard(PoliticCardState politicCardState) {
		this.politicCardState=politicCardState;
	}

	@Override
	public void apply(GameBoard gameBoard) {
		Player currentPlayer = gameBoard.getCurrentPlayer();
		Sellable politicCard = gameBoard.getMarket().getRealSellable(this.politicCardState);
		Sellable buyedSellable = super.buy(gameBoard, currentPlayer, politicCard);
		if(buyedSellable.getClass() == politicCard.getClass()){
			PoliticCard buyedPoliticCard = (PoliticCard) buyedSellable;
			buyedPoliticCard.setPrice(0);
			currentPlayer.addPoliticCard(buyedPoliticCard);
		}

	}

}
