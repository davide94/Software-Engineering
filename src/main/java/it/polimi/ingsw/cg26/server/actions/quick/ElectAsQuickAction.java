package it.polimi.ingsw.cg26.server.actions.quick;

import it.polimi.ingsw.cg26.common.dto.CouncillorDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.common.update.event.MessageUpdate;
import it.polimi.ingsw.cg26.server.actions.ElectRegion;
import it.polimi.ingsw.cg26.server.exceptions.CouncillorNotFoundException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.server.exceptions.NotYourTurnException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;

/**
 *
 */
public class ElectAsQuickAction extends ElectRegion {

	/**
	 * Construct an action to elect a councillor as quick action
	 * @param region the region where the player wants to elect the councillor
	 * @param councillor the councillor the player wants to elect
	 * @param token the token of the player
	 * @throws NullPointerException if one or more arguments are null
	 */
	public ElectAsQuickAction(RegionDTO region, CouncillorDTO councillor, long token) {
		super(region, councillor, token);
	}

	/**
	 * @throws NoRemainingActionsException if the player has no more remaining actions to do
	 * @throws NoRemainingAssistantsException if the player doesn't have enough assistant to perform the action
	 */
	@Override
    public void apply(GameBoard gameBoard) throws NoRemainingActionsException, NoRemainingAssistantsException, CouncillorNotFoundException, NotYourTurnException {
		Player currentPlayer = gameBoard.getCurrentPlayer();
		if(currentPlayer.getToken() != this.getToken())
			throw new NotYourTurnException();
		if (!currentPlayer.canPerformQuickAction())
    		throw new NoRemainingActionsException();
    	if(currentPlayer.getAssistantsNumber()<1){
    		throw new NoRemainingAssistantsException();
    	}
    	super.apply(gameBoard);
    	currentPlayer.takeAssistants(1);
    	currentPlayer.performQuickAction();
    	notifyChange(gameBoard);
		gameBoard.notifyObservers(new MessageUpdate(currentPlayer.getName(), "[Elected a " + councillor.getColor().getColor() + " Councillor in " + region.getName() + " as Quick Action]"));
	}
}
