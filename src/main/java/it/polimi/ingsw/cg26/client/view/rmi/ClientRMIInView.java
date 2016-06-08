package it.polimi.ingsw.cg26.client.view.rmi;

import it.polimi.ingsw.cg26.common.change.Change;
import it.polimi.ingsw.cg26.common.observer.Observable;
import it.polimi.ingsw.cg26.common.observer.Observer;
import it.polimi.ingsw.cg26.common.rmi.ClientRMIViewInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class ClientRMIInView extends UnicastRemoteObject implements ClientRMIViewInterface, Runnable, Serializable {

    private static final long serialVersionUID = 1988554106153678366L;

    private List<Observer<Change>> observers;

    public ClientRMIInView() throws RemoteException {
        super();
        observers = new LinkedList<>();
    }

    @Override
    public void updateClient(Change c) throws RemoteException {
        notifyObservers(c);
    }

    @Override
    public void run() {

    }

    public void registerObserver(Observer<Change> o) {
        if (o == null)
            throw new NullPointerException();
        this.observers.add(o);
    }

    public void notifyObservers(Change c) {
        for (Observer<Change> o: this.observers)
            o.update(c);
    }
}
