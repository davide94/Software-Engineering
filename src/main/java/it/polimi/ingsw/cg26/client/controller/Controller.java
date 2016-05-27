package it.polimi.ingsw.cg26.client.controller;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.change.Change;
import it.polimi.ingsw.cg26.common.observer.Observer;

/**
 *
 */
public class Controller implements Observer<Change>, Runnable {

    private final Model model;

    public Controller(Model model) {
        this.model = model;
    }

    @Override
    public void update(Change change) {
        change.apply(model.getGameBoard());
        model.notifyObservers(model.getGameBoard());
    }

    @Override
    public void run() {

    }
}
