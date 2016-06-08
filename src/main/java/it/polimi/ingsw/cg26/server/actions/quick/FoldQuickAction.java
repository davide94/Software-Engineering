package it.polimi.ingsw.cg26.server.actions.quick;

import it.polimi.ingsw.cg26.common.change.BasicChange;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;

/**
 *
 */
public class FoldQuickAction extends Action {

    /**
     * Construct an action that represent the decision of the player to not perform the quick action
     * @param token the token of the player
     */
    public FoldQuickAction(long token) {
        super(token);
    }

    /**
     * @throws NoRemainingActionsException if the player has no more remaining actions to do
     */
    @Override
    public void apply(GameBoard gameBoard) {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        if (!currentPlayer.canPerformQuickAction())
            throw new NoRemainingActionsException();
        currentPlayer.performQuickAction();
        notifyChange(gameBoard);
    }

	@Override
	public void notifyChange(GameBoard gameBoard) {
		notifyDecoratingPlayersChange(gameBoard, new BasicChange());
	}

}
