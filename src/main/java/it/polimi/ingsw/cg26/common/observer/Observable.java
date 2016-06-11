package it.polimi.ingsw.cg26.common.observer;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 */
public abstract class Observable<C> {

    private List<Observer<C>> observers;

    public Observable() {
        this.observers = new LinkedList<>();
    }

    public void registerObserver(Observer<C> o) {
        if (o == null)
            throw new NullPointerException();
        this.observers.add(o);
    }

    public void deregisterObserver(Observer<C> o) {
        if (o == null)
            throw new NullPointerException();
        this.observers.remove(o);
    }

    public void notifyObservers(C c) {
        for (Observer<C> o: this.observers)
            o.update(c);
    }
}