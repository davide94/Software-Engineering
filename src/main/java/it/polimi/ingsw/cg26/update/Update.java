package it.polimi.ingsw.cg26.update;

import it.polimi.ingsw.cg26.model.board.GameBoard;

/**
 *
 */
public class Update {

    private GameBoard gameBoard;

    public Update(GameBoard gameBoard) {
        if (gameBoard == null)
            throw new NullPointerException();
        this.gameBoard = gameBoard;
    }

    public String getState() {
        return this.gameBoard.getState();
    }
}
