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

/**
 *
 */
public class Acquire extends Corrupt {

	private final RegionState region;

    private final int position;

    /**
     *
     * @param region
     * @param politicCards
     * @param position
     * @throws NullPointerException if one or more arguments are null
     */
    public Acquire(RegionState region, Collection<PoliticCardState> politicCards, int position) {
		super(politicCards);
        if (region == null)
            throw new NullPointerException();
        this.region = region;
        this.position = position;
    }

    /**
     * @throws NotEnoughMoneyException if the player hasn't got enough money to do the action
     * @throws InvalidCardsException if the cards given by the user don't match the colors of the councillors in the balcony
     */
    @Override
    public void apply(GameBoard gameBoard) {
		Player currentPlayer = gameBoard.getCurrentPlayer();
		super.apply(gameBoard);
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
