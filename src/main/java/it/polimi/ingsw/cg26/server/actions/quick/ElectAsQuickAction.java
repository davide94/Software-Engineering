package it.polimi.ingsw.cg26.server.actions.quick;

import it.polimi.ingsw.cg26.common.state.CouncillorState;
import it.polimi.ingsw.cg26.common.state.RegionState;
import it.polimi.ingsw.cg26.server.actions.Elect;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;

/**
 *
 */
public class ElectAsQuickAction extends Elect {

	/**
	 * Construct an action to elect a councillor as quick action
	 * @param region the region where the player wants to elect the councillor
	 * @param councillor the councillor the player wants to elect
	 */
	public ElectAsQuickAction(RegionState region, CouncillorState councillor) {
		super(region, councillor);
	}

	/**
	 * @throws NoRemainingActionsException if the player has no more remaining actions to do
	 * @throws NoRemainingAssistantsException if the player doesn't have enough assistant to perform the action
	 */
	@Override
    public void apply(GameBoard gameBoard) {
		Player currentPlayer = gameBoard.getCurrentPlayer();
		if (!currentPlayer.canPerformQuickAction())
    		throw new NoRemainingActionsException();
    	if(currentPlayer.getAssistantsNumber()<1){
    		throw new NoRemainingAssistantsException();
    	}
    	
    	super.apply(gameBoard);
    	
    	currentPlayer.takeAssistants(1);
    	currentPlayer.performQuickAction();
    }
}
