package it.polimi.ingsw.cg26.client.controller;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.update.Update;
import it.polimi.ingsw.cg26.common.observer.Observer;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCityException;
import it.polimi.ingsw.cg26.server.exceptions.InvalidRegionException;
import it.polimi.ingsw.cg26.server.exceptions.PlayerNotFoundException;

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
        try {
            u.apply(model);
        } catch (InvalidRegionException e) {
            e.printStackTrace();
        } catch (InvalidCityException e) {
            e.printStackTrace();
        } catch (PlayerNotFoundException e) {
            e.printStackTrace();
        }
        model.notifyObservers(model);
    }

    @Override
    public void run() {

    }
}
