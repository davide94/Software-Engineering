package it.polimi.ingsw.cg26.client.view.rmi;

import it.polimi.ingsw.cg26.common.change.Change;
import it.polimi.ingsw.cg26.common.observer.Observable;
import it.polimi.ingsw.cg26.common.rmi.ClientRMIViewInterface;

import java.rmi.RemoteException;

/**
 *
 */
public class ClientRMIInView extends Observable<Change> implements ClientRMIViewInterface, Runnable {


    public ClientRMIInView() throws RemoteException {
        super();
    }

    @Override
    public void updateClient(Change c) throws RemoteException {
        notifyObservers(c);
    }

    @Override
    public void run() {

    }
}
