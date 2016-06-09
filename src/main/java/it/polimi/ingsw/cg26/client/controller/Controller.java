package it.polimi.ingsw.cg26.client.controller;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.observer.Observer;
import it.polimi.ingsw.cg26.common.update.Update;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCityException;
import it.polimi.ingsw.cg26.server.exceptions.InvalidRegionException;
import it.polimi.ingsw.cg26.server.exceptions.PlayerNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class Controller implements Observer<Update>, Runnable {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final Model model;

    public Controller(Model model) {
        this.model = model;
    }

    @Override
    public void update(Update u) {
        try {
            u.apply(model);
        } catch (Exception e) {
            log.error("Error applying update.", e);
        }
        model.notifyObservers(u);
    }

    @Override
    public void run() {

    }
}
