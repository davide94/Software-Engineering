package it.polimi.ingsw.cg26.client.model;

import it.polimi.ingsw.cg26.common.dto.*;
import it.polimi.ingsw.cg26.common.observer.Observable;

import java.util.LinkedList;

/**
 *
 */
public class Model extends Observable<GameBoardDTO> {

    private GameBoardDTO gameBoard;

    public Model() {
        this.gameBoard = new GameBoardDTO(new LinkedList<>(), new PlayerDTO("a", 0, 0, 0, 0, 0, 0, 0,
                new LinkedList<>(), new LinkedList<>(), new LinkedList<>()), new PoliticDeckDTO(), new LinkedList<>(),
                new BalconyDTO(new LinkedList<>()), new LinkedList<>(), new NobilityTrackDTO(new LinkedList<>()),
                new KingDTO("0"), new MarketDTO(new LinkedList<>()), new KingDeckDTO(new LinkedList<>()));
    }

    public void setGameBoard(GameBoardDTO gameBoard) {
        this.gameBoard = gameBoard;
    }

    public GameBoardDTO getGameBoard() {
        return gameBoard;
    }

}
