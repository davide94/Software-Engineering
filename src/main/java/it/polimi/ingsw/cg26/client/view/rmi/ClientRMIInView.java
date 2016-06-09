package it.polimi.ingsw.cg26.client.view.rmi;

import it.polimi.ingsw.cg26.common.observer.Observer;
import it.polimi.ingsw.cg26.common.rmi.ClientRMIViewInterface;
import it.polimi.ingsw.cg26.common.update.Update;

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

    private List<Observer<Update>> observers;

    public ClientRMIInView() throws RemoteException {
        super();
        observers = new LinkedList<>();
    }

    @Override
    public void updateClient(Update u) throws RemoteException {
        notifyObservers(u);
    }

    @Override
    public void run() {

    }

    public void registerObserver(Observer<Update> o) {
        if (o == null)
            throw new NullPointerException();
        this.observers.add(o);
    }

    public void notifyObservers(Update u) {
        for (Observer<Update> o: this.observers)
            o.update(u);
    }
}
