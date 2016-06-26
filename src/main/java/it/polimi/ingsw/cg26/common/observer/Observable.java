package it.polimi.ingsw.cg26.common.observer;

import java.util.LinkedList;
import java.util.List;


public abstract class Observable<C> {

	/**
	 * A list of observers
	 */
    private List<Observer<C>> observers;

    public Observable() {
        this.observers = new LinkedList<>();
    }

    /**
     * Records a new observer to the list of observers
     * @param o is the new observer
     */
    public void registerObserver(Observer<C> o) {
        if (o == null)
            throw new NullPointerException();
        this.observers.add(o);
    }

    /**
     * Remove an observer from the list of observers
     * @param o is the observer that has to be removed from the list of observers  
     */
    public void deregisterObserver(Observer<C> o) {
        if (o == null)
            throw new NullPointerException();
        this.observers.remove(o);
    }

    /**
     * Send a notify to all the observers about a change
     * @param c is the change that has to be notified to the observers
     */
    public void notifyObservers(C c) {
        for (Observer<C> o: this.observers)
            o.update(c);
    }
}