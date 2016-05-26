package it.polimi.ingsw.cg26.client.controller;

import it.polimi.ingsw.cg26.common.change.Change;
import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;
import it.polimi.ingsw.cg26.common.observer.Observer;

/**
 *
 */
public class Controller implements Observer<Change> {

    private final GameBoardDTO model;

    public Controller(GameBoardDTO model) {
        this.model = model;
    }

    @Override
    public void update(Change change) {
        change.apply(model);
    }

}
