package it.polimi.ingsw.cg26.client.view.rmi;

import it.polimi.ingsw.cg26.common.rmi.ServerRMIViewInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 */
public class ClientRMI {

    private final static String DEFAULT_IP = "localhost";

    private final static int RMI_PORT = 52365;

    private final static String INTERFACE_NAME = "NOME_DA_DECIDERE";

    public ClientRMI() throws RemoteException, NotBoundException {
        startRMIClient();
    }

    private void startRMIClient() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(DEFAULT_IP, RMI_PORT);
        ServerRMIViewInterface serverStub = (ServerRMIViewInterface) registry.lookup(INTERFACE_NAME);

        ClientRMIView rmiView = new ClientRMIView();
        long token = serverStub.registerClient(rmiView);
    }

}
