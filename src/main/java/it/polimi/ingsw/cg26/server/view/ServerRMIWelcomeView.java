package it.polimi.ingsw.cg26.server.view;

import it.polimi.ingsw.cg26.common.rmi.ClientRMIViewInterface;
import it.polimi.ingsw.cg26.common.rmi.ServerRMIWelcomeViewInterface;
import it.polimi.ingsw.cg26.common.rmi.ServerRMIViewInterface;
import it.polimi.ingsw.cg26.server.Server;

import java.rmi.RemoteException;

/**
 *
 */
public class ServerRMIWelcomeView implements ServerRMIWelcomeViewInterface {

    private final Server server;

    public ServerRMIWelcomeView(Server server) {
        if (server == null)
            throw new NullPointerException();
        this.server = server;
    }

    @Override
    public ServerRMIViewInterface registerClient(ClientRMIViewInterface clientStub, String name) throws RemoteException {
        return server.registerRMIPlayer(clientStub, name);
    }
}
