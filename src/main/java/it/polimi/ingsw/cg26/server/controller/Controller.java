package it.polimi.ingsw.cg26.server.controller;

import it.polimi.ingsw.cg26.common.change.BasicChange;
import it.polimi.ingsw.cg26.common.change.Change;
import it.polimi.ingsw.cg26.common.change.FullStateChange;
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
            // TODO check if current playerry {
            action.apply(gameBoard);
            gameBoard.actionPerformed();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            // TODO notify the view that the action doesn't succeeded
        }
    }

    @Override
    public void run() {
        System.out.println("Partita cominciata");
        Change decoratedChange = new BasicChange();
        gameBoard.notifyObservers(new FullStateChange(decoratedChange, gameBoard.getState()));
        // TODO start the game
    }
}