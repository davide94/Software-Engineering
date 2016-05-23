package it.polimi.ingsw.cg26.server.actions.quick;

import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;

/**
 *
 */
public class AdditionalMainAction extends Action {

	public AdditionalMainAction() {

	}

	/**
	 * 
	 */
    @Override
    public void apply(GameBoard gameBoard) {
		Player currentPlayer = gameBoard.getCurrentPlayer();
		if (!currentPlayer.canPerformQuickAction())
    		throw new NoRemainingActionsException();
    	currentPlayer.takeAssistants(3);
    	currentPlayer.addRemainingMainActions(1);
    	currentPlayer.performQuickAction();
    }

}
