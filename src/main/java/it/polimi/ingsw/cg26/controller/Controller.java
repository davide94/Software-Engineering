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
    private GameLogic gameLogic;

    /**
     * @param
     */
    public void Controller(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    /**
     *
     */
    public void update() {
        // TODO implement here
    }

}