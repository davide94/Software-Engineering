package it.polimi.ingsw.cg26.server.actions.quick;

import it.polimi.ingsw.cg26.common.update.change.BasicChange;
import it.polimi.ingsw.cg26.common.update.event.MessageUpdate;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingAssistantsException;
import it.polimi.ingsw.cg26.server.exceptions.NotYourTurnException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;

/**
 *
 */
public class AdditionalMainAction extends Action {

	/**
	 * Construct an action to give one more main action to the player
	 * @param token the token of the player
	 */
	public AdditionalMainAction(long token) {
		super(token);
		//this action needs only to check parameters of currentPlayer, all is done in apply method, so no elaborated construcor is needed
	}

	/**
	 * @throws NoRemainingActionsException if the player has no more remaining actions to do
	 */
    @Override
    public void apply(GameBoard gameBoard) throws NoRemainingActionsException, NoRemainingAssistantsException, NotYourTurnException {
		Player currentPlayer = gameBoard.getCurrentPlayer();
		if(currentPlayer.getToken() != this.getToken())
			throw new NotYourTurnException("You can not perform an action now.");
		if (!currentPlayer.canPerformQuickAction())
    		throw new NoRemainingActionsException("You can not perform an action now.");
    	currentPlayer.takeAssistants(3);
    	currentPlayer.addRemainingMainActions(1);
    	currentPlayer.performQuickAction();
    	notifyChange(gameBoard);
		gameBoard.notifyObservers(new MessageUpdate(currentPlayer.getName(), "[Will perform an additional Main Action]"));
	}

	@Override
	public void notifyChange(GameBoard gameBoard) {
		notifyDecoratingPlayersChange(gameBoard, new BasicChange());
	}
}
