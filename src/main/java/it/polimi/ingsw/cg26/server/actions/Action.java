package it.polimi.ingsw.cg26.server.actions;

import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;

/**
 *
 */
public abstract class Action {

    public Action() {

    }

    /**
     * apply the action into the gameBoard specified to the player specified
     * @param gameBoard the gameBoard where the action is applied
     */
    public abstract void apply(GameBoard gameBoard);

}
