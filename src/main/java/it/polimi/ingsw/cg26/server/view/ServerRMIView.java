package it.polimi.ingsw.cg26.server.view;

import it.polimi.ingsw.cg26.common.change.Change;
import it.polimi.ingsw.cg26.common.commands.Command;
import it.polimi.ingsw.cg26.common.rmi.ClientRMIViewInterface;
import it.polimi.ingsw.cg26.common.rmi.ServerRMIViewInterface;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ServerRMIView extends View implements ServerRMIViewInterface {

    private Map<ClientRMIViewInterface, ActionVisitor> clients;

    public ServerRMIView() {
        clients = new HashMap<>();

    }

    @Override
    public void update(Change o) {

    }

    @Override
    public void registerClient(ClientRMIViewInterface clientStub) throws RemoteException {

    }

    @Override
    public void eseguiAzione(Command command) throws RemoteException {

    }

    @Override
    public void run() {

    }
}
