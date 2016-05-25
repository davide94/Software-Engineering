package it.polimi.ingsw.cg26.server.actions.main;

import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
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

	/**
	 * The region where the player wants to acquire the tile
	 */
	private final RegionDTO region;

	/**
	 * The position of the tile that the player wants to take, 0 is the first, 1 is the second
	 */
    private final int position;

    /**
     * Construct an acquire action
     * @param region the region where the player wants to buy the tile
     * @param politicCards the politic cards the player wants to use to corrupt the balcony
     * @param position represents which card has to be drawn, 0 is the first, 1 is the second
     * @throws NullPointerException if one or more arguments are null
     * @throws IllegalArgumentException if the position is <0
     */
    public Acquire(RegionDTO region, Collection<PoliticCardDTO> politicCards, int position) {
		super(politicCards);
        if (region == null)
            throw new NullPointerException();
        if(position < 0)
        	throw new IllegalArgumentException();
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
