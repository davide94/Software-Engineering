package it.polimi.ingsw.cg26.controller;

import it.polimi.ingsw.cg26.actions.Action;
import it.polimi.ingsw.cg26.model.GameLogic;
import it.polimi.ingsw.cg26.model.player.Player;
import it.polimi.ingsw.cg26.observer.Observer;

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

    @Override
    public void update(Action action) {
        action.apply(gameLogic);
    }

    public void start() {

    }
}