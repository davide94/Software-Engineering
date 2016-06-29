package it.polimi.ingsw.cg26.server.actions.main;

import it.polimi.ingsw.cg26.common.dto.PoliticCardDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.common.update.change.BPTDeckChange;
import it.polimi.ingsw.cg26.common.update.change.BasicChange;
import it.polimi.ingsw.cg26.common.update.change.Change;
import it.polimi.ingsw.cg26.common.update.event.MessageUpdate;
import it.polimi.ingsw.cg26.server.actions.Corrupt;
import it.polimi.ingsw.cg26.server.exceptions.*;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.board.Region;
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
    public Acquire(RegionDTO region, Collection<PoliticCardDTO> politicCards, int position, long token) {
		super(politicCards, token);
        if (region == null)
            throw new NullPointerException();
        if(position < 0)
        	throw new IllegalArgumentException();
        this.region = region;
        this.position = position;
    }

    /**
     * @throws NoRemainingActionsException if the player has no more remaining actions to do
     * @throws InvalidCardsException if the cards given by the user doesn't match with the real cards of the player
     * @throws NotEnoughMoneyException if the player hasn't got enough money to do the action
     * @throws InvalidCardsException if the cards given by the user don't match the colors of the councillors in the balcony
     */
    @Override
    public void apply(GameBoard gameBoard) throws NotEnoughMoneyException, InvalidCardsException, NoRemainingActionsException, NoRemainingAssistantsException, ExistingEmporiumException, CityNotFoundException, NoRemainingCardsException {
		Player currentPlayer = gameBoard.getCurrentPlayer();
		super.apply(gameBoard);
		int usedCoins = super.necessaryCoins(politicCards);
		if (currentPlayer.getCoinsNumber() < usedCoins)
			throw new NotEnoughMoneyException();
		if (!gameBoard.getRegion(this.region).getBalcony().checkPoliticCards(this.politicCards))
    		throw new InvalidCardsException();
    	BusinessPermissionTile addedBPT = gameBoard.getRegion(this.region).getBPTDeck().draw(this.position);
    	currentPlayer.addPermissionTile(addedBPT);
		addedBPT.getReward(currentPlayer);
    	Collection<PoliticCard> discarded = currentPlayer.takeCards(this.politicCards);
		gameBoard.getPoliticDeck().discardAll(discarded);
		currentPlayer.removeCoins(usedCoins);
    	currentPlayer.performMainAction();
    	notifyChange(gameBoard);
    	checkPendingRequest(gameBoard);
		gameBoard.notifyObservers(new MessageUpdate(currentPlayer.getName(), "[Acquired a Building Permit Tile in " + region.getName() + "]"));
	}
    
    @Override
    public void notifyChange(GameBoard gameBoard){
    	Region realRegion = gameBoard.getRegion(this.region);
    	Change bPTChange = new BPTDeckChange(new BasicChange(), realRegion.getBPTDeck().getState(), realRegion.getState());
    	notifyDecoratingPlayersChange(gameBoard, bPTChange);
    }
}
