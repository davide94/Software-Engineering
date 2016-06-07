package it.polimi.ingsw.cg26.common.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 */
public interface ServerRMIWelcomeViewInterface extends Remote {

    ServerRMIViewInterface registerClient(ClientRMIViewInterface clientStub, String name) throws RemoteException;
}
