package it.polimi.ingsw.cg26.client.controller;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.observer.Observer;
import it.polimi.ingsw.cg26.common.update.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Controller implements Observer<Update>, Runnable {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * This is the model observed by the controller
     */
    private final Model model;

    
    /**
     * Default constructor
     * @param model is the model that the controller should update after an action
     */
    public Controller(Model model) {
        this.model = model;
    }

    
    /**
     * The controller applies the update to the model and the model after this change 
     * has to notify all the observer about the latest update 
     */
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
