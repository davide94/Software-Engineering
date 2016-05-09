package it.polimi.ingsw.cg26.observer;

import java.util.Collection;
import java.util.LinkedList;

/**
 * 
 */
public class Observable {

    /**
     *
     */
    private Collection<Observer> observers;

    /**
     * Default constructor
     */
    public Observable() {
        this.observers = new LinkedList<>();
    }


    /**
     * 
     */
    public void notifyObservers() {
        for (Observer o: this.observers)
            o.update();
    }

    /**
     * @param
     */
    public void addObserver(Observer o) {
        if (o == null)
            throw new NullPointerException();
    }

}