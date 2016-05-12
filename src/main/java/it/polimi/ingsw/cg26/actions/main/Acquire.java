package it.polimi.ingsw.cg26.actions.main;

import it.polimi.ingsw.cg26.actions.Corrupt;
import it.polimi.ingsw.cg26.exceptions.InvalidCardsException;
import it.polimi.ingsw.cg26.exceptions.NotEnoughMoneyException;
import it.polimi.ingsw.cg26.model.board.GameBoard;
import it.polimi.ingsw.cg26.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.model.player.Player;

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
    public void apply(GameBoard gameBoard) {
		Player currentPlayer = gameBoard.getCurrentPlayer();
		int usedCoins = this.necessaryCoins(politicCardsColors, gameBoard);
		if (currentPlayer.getCoinsNumber() < usedCoins)
			throw new NotEnoughMoneyException();
		if (!gameBoard.getRegion(this.region).getBalcony().checkPoliticCards(this.politicCardsColors))
    		throw new InvalidCardsException();
    	BusinessPermissionTile addedBPT = gameBoard.getRegion(this.region).getBPTDeck().draw(this.position);
    	currentPlayer.addPermissionTile(addedBPT);
    	Collection<PoliticCard> discarded = currentPlayer.useCards(this.politicCardsColors);
		gameBoard.getPoliticDeck().discardAll(discarded);
    	currentPlayer.removeCoins(usedCoins);
    	currentPlayer.performMainAction();
    }

}
