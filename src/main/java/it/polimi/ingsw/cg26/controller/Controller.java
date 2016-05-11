package it.polimi.ingsw.cg26.controller;

import it.polimi.ingsw.cg26.actions.Action;
import it.polimi.ingsw.cg26.model.GameLogic;
import it.polimi.ingsw.cg26.observer.Observer;
import it.polimi.ingsw.cg26.update.ActionNotPermitted;
import it.polimi.ingsw.cg26.update.Update;

/**
 * 
 */
public class Controller implements Observer<Action> {

    private final GameLogic gameLogic;

    public Controller(GameLogic gameLogic) {
        if (gameLogic == null)
            throw new NullPointerException();
        this.gameLogic = gameLogic;
    }

    public void update(Action action) {
        try {
            action.apply(gameLogic);
        } catch (RuntimeException e) {
            e.printStackTrace();
            //update(new ActionNotPermitted(e.toString()));
        }
    }

    public void start() {

    }
}