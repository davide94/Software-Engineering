package it.polimi.ingsw.cg26.actions.quick;

import it.polimi.ingsw.cg26.actions.Action;
import it.polimi.ingsw.cg26.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.model.board.GameBoard;
import it.polimi.ingsw.cg26.model.player.Assistant;
import it.polimi.ingsw.cg26.model.player.Player;

/**
 *
 */
public class EngageAssistant extends Action {

	public EngageAssistant(String token) {
		super(token);
	}

	/**
	 * 
	 */
    @Override
    public void apply(GameBoard gameBoard, Player currentPlayer) {
		if (!currentPlayer.canPerformQuickAction())
    		throw new NoRemainingActionsException();
    	currentPlayer.removeCoins(3);
    	currentPlayer.addAssistant(new Assistant());
    	currentPlayer.performQuickAction();
    }

}
