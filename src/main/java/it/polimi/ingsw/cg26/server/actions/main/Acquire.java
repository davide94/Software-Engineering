package it.polimi.ingsw.cg26.server.actions.main;

import it.polimi.ingsw.cg26.server.actions.Corrupt;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCardsException;
import it.polimi.ingsw.cg26.server.exceptions.NotEnoughMoneyException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.util.Collection;

/**
 *
 */
public class Acquire extends Corrupt {

	private final String region;

    private final int position;

    public Acquire(String region, Collection<PoliticColor> politicCardsColors, int position) {
		super(politicCardsColors);
        if (region == null)
            throw new NullPointerException();
        this.region = region;
        this.position = position;
    }

    /**
     * 
     */
    @Override
    public void apply(GameBoard gameBoard, Player currentPlayer) {
		int usedCoins = super.necessaryCoins(politicCardsColors);
		if (currentPlayer.getCoinsNumber() < usedCoins)
			throw new NotEnoughMoneyException();
		if (!gameBoard.getRegion(this.region).getBalcony().checkPoliticCards(this.politicCardsColors))
    		throw new InvalidCardsException();
    	BusinessPermissionTile addedBPT = gameBoard.getRegion(this.region).getBPTDeck().draw(this.position);
    	currentPlayer.addPermissionTile(addedBPT);
    	Collection<PoliticCard> discarded = currentPlayer.takeCards(this.politicCardsColors);
		gameBoard.getPoliticDeck().discardAll(discarded);
		currentPlayer.takeCards(super.politicCardsColors);
		currentPlayer.removeCoins(usedCoins);
    	currentPlayer.performMainAction();
    }

}
