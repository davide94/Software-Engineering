package it.polimi.ingsw.cg26.common.rmi;

import it.polimi.ingsw.cg26.common.commands.Command;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 */
public interface ServerRMIViewInterface extends Remote {

    void performAction(Command c) throws RemoteException;

}
