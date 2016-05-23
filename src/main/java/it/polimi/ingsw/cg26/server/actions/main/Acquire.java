package it.polimi.ingsw.cg26.server.actions.main;

import it.polimi.ingsw.cg26.common.state.PoliticCardState;
import it.polimi.ingsw.cg26.common.state.RegionState;
import it.polimi.ingsw.cg26.server.actions.Corrupt;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCardsException;
import it.polimi.ingsw.cg26.server.exceptions.NotEnoughMoneyException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.util.Collection;
import java.util.List;

/**
 *
 */
public class Acquire extends Corrupt {

	private final RegionState region;

    private final int position;

    public Acquire(RegionState region, Collection<PoliticCardState> politicCards, int position) {
		super(politicCards);
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
		int usedCoins = super.necessaryCoins(politicCards);
		if (currentPlayer.getCoinsNumber() < usedCoins)
			throw new NotEnoughMoneyException();
		if (!gameBoard.getRegion(this.region).getBalcony().checkPoliticCards(this.politicCards))
    		throw new InvalidCardsException();
    	BusinessPermissionTile addedBPT = gameBoard.getRegion(this.region).getBPTDeck().draw(this.position);
    	currentPlayer.addPermissionTile(addedBPT);
    	Collection<PoliticCard> discarded = currentPlayer.takeCards(this.politicCards);
		gameBoard.getPoliticDeck().discardAll(discarded);
		currentPlayer.removeCoins(usedCoins);
    	currentPlayer.performMainAction();
    }

}
