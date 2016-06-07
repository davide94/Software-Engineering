package it.polimi.ingsw.cg26.server.actions.quick;

import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;

/**
 *
 */
public class EngageAssistant extends Action {

	/**
	 * Construct an action to engage an assistant 
	 * @param token the token of the player
	 */
	public EngageAssistant(long token) {
		super(token);
		//the assistant is an immutable object so is created when needed and not received in input
	}

	/**
	 * @throws NoRemainingActionsException if the player has no more remaining actions to do
	 */
    @Override
    public void apply(GameBoard gameBoard) {
		Player currentPlayer = gameBoard.getCurrentPlayer();
		if (!currentPlayer.canPerformQuickAction())
    		throw new NoRemainingActionsException();
    	currentPlayer.removeCoins(3);
    	currentPlayer.addAssistant(new Assistant());
    	currentPlayer.performQuickAction();
    }

	@Override
	public void notifyChange(GameBoard gameBoard) {
		// TODO Auto-generated method stub
		
	}

}
