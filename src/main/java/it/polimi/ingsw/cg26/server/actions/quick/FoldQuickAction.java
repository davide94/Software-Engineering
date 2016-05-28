package it.polimi.ingsw.cg26.server.actions.quick;

import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingActionsException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;

/**
 *
 */
public class FoldQuickAction extends Action {

    /**
     * Build a simple action
     *
     * @param token
     */
    public FoldQuickAction(long token) {
        super(token);
    }

    @Override
    public void apply(GameBoard gameBoard) {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        if (!currentPlayer.canPerformQuickAction())
            throw new NoRemainingActionsException();
        currentPlayer.performQuickAction();
    }

}
