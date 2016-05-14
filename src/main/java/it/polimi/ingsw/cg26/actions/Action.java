package it.polimi.ingsw.cg26.actions;

import it.polimi.ingsw.cg26.model.board.GameBoard;
import it.polimi.ingsw.cg26.model.player.Player;

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
