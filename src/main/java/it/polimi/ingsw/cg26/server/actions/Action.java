package it.polimi.ingsw.cg26.server.actions;

import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.io.Serializable;

/**
 *
 */
public abstract class Action {

    public Action() {

    }

    public abstract void apply(GameBoard gameBoard, Player currentPlayer);

}
