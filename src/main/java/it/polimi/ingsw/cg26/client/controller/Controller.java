package it.polimi.ingsw.cg26.client.controller;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.update.Update;
import it.polimi.ingsw.cg26.common.observer.Observer;

/**
 *
 */
public class Controller implements Observer<Update>, Runnable {

    private final Model model;

    public Controller(Model model) {
        this.model = model;
    }

    @Override
    public void update(Update u) {
        u.apply(model);
        model.notifyObservers(model);
    }

    @Override
    public void run() {

    }
}
