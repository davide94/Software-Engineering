package it.polimi.ingsw.cg26.common.rmi;

import it.polimi.ingsw.cg26.common.update.Update;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 */
public interface ClientRMIViewInterface extends Remote {

    default boolean checkConnection() throws RemoteException { return true; }

    void updateClient(Update c) throws RemoteException;
}
