package it.polimi.ingsw.cg26.client.view.rmi;

import it.polimi.ingsw.cg26.common.change.Change;
import it.polimi.ingsw.cg26.common.rmi.ClientRMIViewInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 */
public class ClientRMIView extends UnicastRemoteObject implements ClientRMIViewInterface {


    protected ClientRMIView() throws RemoteException {
        super();
    }

    @Override
    public void updateClient(Change c) throws RemoteException {

    }
}
