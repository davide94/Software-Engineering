package it.polimi.ingsw.cg26.server.controller;

import it.polimi.ingsw.cg26.common.change.*;
import it.polimi.ingsw.cg26.common.dto.PlayerDTO;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.common.observer.Observer;

/**
 * 
 */
public class Controller implements Observer<Action>, Runnable {

    private final GameBoard gameBoard;

    public Controller(GameBoard gameBoard) {
        if (gameBoard == null)
            throw new NullPointerException();
        this.gameBoard = gameBoard;
    }

    @Override
    public synchronized void update(Action action) {
        try {
            if (gameBoard.getCurrentPlayer().getToken() == action.getToken()) {

                action.apply(gameBoard);

                gameBoard.notifyObservers(new FullStateChange(new BasicChange(), gameBoard.getState()));
                for (PlayerDTO player: gameBoard.getFullPlayers())
                    gameBoard.notifyObservers(new PrivateChange(new LocalPlayerChange(new BasicChange(), player), player.getToken()));
                gameBoard.notifyObservers(new PrivateChange(new YourTurnStarts(new BasicChange()), gameBoard.getCurrentPlayer().getToken()));

                gameBoard.actionPerformed();
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            // TODO notify the view that the action doesn't succeeded
        }
    }

    @Override
    public void run() {
        System.out.println("Partita cominciata");

        gameBoard.notifyObservers(new FullStateChange(new BasicChange(), gameBoard.getState()));
        for (PlayerDTO player: gameBoard.getFullPlayers())
            gameBoard.notifyObservers(new PrivateChange(new LocalPlayerChange(new BasicChange(), player), player.getToken()));
        gameBoard.notifyObservers(new PrivateChange(new YourTurnStarts(new BasicChange()), gameBoard.getCurrentPlayer().getToken()));

    }
}