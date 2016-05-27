package it.polimi.ingsw.cg26.client.controller;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.change.Change;
import it.polimi.ingsw.cg26.common.observer.Observable;
import it.polimi.ingsw.cg26.common.observer.Observer;

/**
 *
 */
public class Controller extends Observable<Change> implements Observer<Change>, Runnable {

    private final Model model;

    public Controller(Model model) {
        this.model = model;
    }

    @Override
    public void update(Change change) {
        change.apply(model.getGameBoard());
        if (model.getGameBoard().getLocalPlayer().getName().equals(model.getGameBoard().getCurrentPlayer().getName()))
            model.startTurn();
        else
            model.endTurn();
        notifyObservers(change);
    }

    @Override
    public void run() {

    }
}
