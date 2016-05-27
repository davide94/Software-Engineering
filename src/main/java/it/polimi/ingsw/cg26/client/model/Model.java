package it.polimi.ingsw.cg26.client.model;

import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;

/**
 *
 */
public class Model {

    private GameBoardDTO gameBoard;

    private boolean isYourTurn;

    public Model(GameBoardDTO gameBoard) {
        this.gameBoard = gameBoard;
        isYourTurn = false;
    }

    public void setGameBoard(GameBoardDTO gameBoard) {
        this.gameBoard = gameBoard;
    }

    public GameBoardDTO getGameBoard() {
        return gameBoard;
    }

    public boolean isYourTurn() {
        return isYourTurn;
    }

    public void startTurn() {
        isYourTurn = true;
    }

    public void endTurn() {
        isYourTurn = false;
    }

}
