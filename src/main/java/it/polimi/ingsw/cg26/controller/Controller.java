package it.polimi.ingsw.cg26.controller;

import it.polimi.ingsw.cg26.actions.Action;
import it.polimi.ingsw.cg26.model.GameLogic;
import it.polimi.ingsw.cg26.model.board.GameBoard;
import it.polimi.ingsw.cg26.observer.Observer;
import it.polimi.ingsw.cg26.update.ActionNotPermitted;
import it.polimi.ingsw.cg26.update.Update;

/**
 * 
 */
public class Controller implements Observer<Action> {

    private final GameBoard gameBoard;

    public Controller(GameBoard gameBoard) {
        if (gameBoard == null)
            throw new NullPointerException();
        this.gameBoard = gameBoard;
    }

    public void update(Action action) {
        try {
            action.apply(gameBoard);
        } catch (RuntimeException e) {
            e.printStackTrace();
            //update(new ActionNotPermitted(e.toString()));
        }
        int a = 0;
    }

    public void start() {

    }
}