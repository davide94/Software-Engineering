package it.polimi.ingsw.cg26.server.actions.market;

import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCardsException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class SellPoliticCard extends Sell {

	/**
	 * The Politic Card the player wants to sell
	 */
	private PoliticCardDTO politicCard;
	
	/**
	 * Construct an action to sell a Politic Card
	 * @param politicCard the Politic Card the player wants to sell
	 * @param price the price to set to the Politic Card
	 * @throws NullPointerException if one or more arguments are null
	 * @throws IllegalArgumentException if the price is less than 1
	 */
	public SellPoliticCard(int price, PoliticCardDTO politicCard, long token) {
		super(price, token);
		if(politicCard == null)
				throw new NullPointerException();
		this.politicCard = politicCard;
	}

	@Override
	public void apply(GameBoard gameBoard) throws InvalidCardsException {
		if(!gameBoard.getScheduler().canSell(getToken()))
			throw new IllegalStateException();
		Player currentPlayer = gameBoard.getCurrentPlayer();
		PoliticCard cardToSell = currentPlayer.takeCard(this.politicCard);
		cardToSell.setOwner(currentPlayer);
		sell(gameBoard, cardToSell);
		notifyChange(gameBoard);
	}
}
