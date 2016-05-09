package it.polimi.ingsw.cg26.controller;

import it.polimi.ingsw.cg26.model.GameLogic;
import it.polimi.ingsw.cg26.observer.Observer;

/**
 * 
 */
public class Controller implements Observer {

    /**
     *
     */
    private final GameLogic gameLogic;

    /**
     * @param
     */
    public Controller(GameLogic gameLogic) {
        if (gameLogic == null)
            throw new NullPointerException();
        this.gameLogic = gameLogic;
    }

    /**
     *
     */
    public void update() {
        // TODO implement here
    }

}