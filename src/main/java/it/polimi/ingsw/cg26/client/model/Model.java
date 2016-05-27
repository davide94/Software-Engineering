package it.polimi.ingsw.cg26.client.model;

import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;
import it.polimi.ingsw.cg26.common.observer.Observable;

/**
 *
 */
public class Model extends Observable<GameBoardDTO> {

    private GameBoardDTO gameBoard;

    public Model(GameBoardDTO gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void setGameBoard(GameBoardDTO gameBoard) {
        this.gameBoard = gameBoard;
    }

    public GameBoardDTO getGameBoard() {
        return gameBoard;
    }

}
