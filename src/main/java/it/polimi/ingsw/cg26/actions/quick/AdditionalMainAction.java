package it.polimi.ingsw.cg26.actions.quick;

import it.polimi.ingsw.cg26.actions.Action;
import it.polimi.ingsw.cg26.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.model.board.GameBoard;
import it.polimi.ingsw.cg26.model.player.Player;

/**
 *
 */
public class AdditionalMainAction extends Action {

	public AdditionalMainAction(String token) {
		super(token);
	}

	/**
	 * 
	 */
    @Override
    public void apply(GameBoard gameBoard, Player currentPlayer) {
		if (!currentPlayer.canPerformQuickAction())
    		throw new NoRemainingActionsException();
    	currentPlayer.takeAssistants(3);
    	currentPlayer.addRemainingMainActions(1);
    	currentPlayer.performQuickAction();
    }

}
