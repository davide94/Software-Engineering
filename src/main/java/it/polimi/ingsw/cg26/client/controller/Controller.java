package it.polimi.ingsw.cg26.client.controller;

import it.polimi.ingsw.cg26.common.change.Change;
import it.polimi.ingsw.cg26.common.observer.Observer;
import it.polimi.ingsw.cg26.common.state.BoardState;

/**
 *
 */
public class Controller implements Observer<Change> {

    private final BoardState model;

    public Controller(BoardState model) {
        this.model = model;
    }

    @Override
    public void update(Change change) {
        change.apply(model);
    }

}
