package it.polimi.ingsw.cg26.server.actions.quick;

import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.common.update.change.BPTDeckChange;
import it.polimi.ingsw.cg26.common.update.change.BasicChange;
import it.polimi.ingsw.cg26.common.update.change.Change;
import it.polimi.ingsw.cg26.common.update.event.MessageUpdate;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import it.polimi.ingsw.cg26.server.exceptions.NotYourTurnException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.board.Region;
import it.polimi.ingsw.cg26.server.model.player.Player;

/**
 *
 */
public class ChangeBPT extends Action {

	/**
	 * The region where the player wants to change the tiles faced up
	 */
    private final RegionDTO region;

    /**
     * Construct the action to change the faced up tiles of a selected region
     * @param region the region where the player wants to change the tiles
     * @param token the token of the player
     * @throws NullPointerException if the argument is null
     */
    public ChangeBPT(RegionDTO region, long token) {
        super(token);
        if (region == null)
            throw new NullPointerException();
        this.region = region;
    }

    /**
     * @throws NoRemainingActionsException if the player has no more remaining actions to do
     * @throws NoRemainingAssistantsException if the player doesn't have enough assistant to perform the action
     */
    @Override
    public void apply(GameBoard gameBoard) throws NoRemainingActionsException, NoRemainingAssistantsException, NoRemainingCardsException, NotYourTurnException {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        if(currentPlayer.getToken() != this.getToken())
			throw new NotYourTurnException("You can not perform an action now.");
        if (!currentPlayer.canPerformQuickAction())
    		throw new NoRemainingActionsException("You can not perform an action now.");
    	if(currentPlayer.getAssistantsNumber()<1)
    		throw new NoRemainingAssistantsException("You does not own enough assistants.");
    	
    	gameBoard.getRegion(region).getBPTDeck().change();
    	
    	currentPlayer.takeAssistants(1);
    	currentPlayer.performQuickAction();
    	notifyChange(gameBoard);
		gameBoard.notifyObservers(new MessageUpdate(currentPlayer.getName(), "[Changed the open Building Permit Tiles in " + region.getName() + "]"));
	}

	@Override
	public void notifyChange(GameBoard gameBoard) {
		Region realRegion = gameBoard.getRegion(this.region);
		Change change = new BPTDeckChange(new BasicChange(), realRegion.getBPTDeck().getState(), realRegion.getState());
		notifyDecoratingPlayersChange(gameBoard, change);
	}
}
