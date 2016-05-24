package it.polimi.ingsw.cg26.server.actions;

import it.polimi.ingsw.cg26.server.model.board.GameBoard;

/**
 *
 */
public abstract class Action {

	/**
	 * Build a simple action
	 */
    public Action() {

    }

    /**
     * apply the action into the gameBoard specified to the player specified
     * @param gameBoard the gameBoard where the action is applied
     * @throws NullPointerException if the parameter is null
     */
    public abstract void apply(GameBoard gameBoard);

}
