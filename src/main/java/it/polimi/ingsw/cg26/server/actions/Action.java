package it.polimi.ingsw.cg26.server.actions;

import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;

/**
 *
 */
public abstract class Action {

    private final String token;

    public Action(String token) {
        if (token == null)
            throw new NullPointerException();
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public abstract void apply(GameBoard gameBoard, Player currentPlayer);

}
