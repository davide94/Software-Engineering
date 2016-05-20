package it.polimi.ingsw.cg26.server.actions;

import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.io.Serializable;

/**
 *
 */
public abstract class Action implements Serializable {

    private static final long serialVersionUID = 3409393218063708249L;

    public Action() {

    }

    public abstract void apply(GameBoard gameBoard, Player currentPlayer);

}
