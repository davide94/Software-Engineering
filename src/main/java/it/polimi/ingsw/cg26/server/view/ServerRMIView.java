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

    private Map<ClientRMIViewInterface, Long> clients;
    private ActionVisitor actionVisitor;

    public ServerRMIView() {
        clients = new HashMap<>();
        actionVisitor = new ActionVisitor(this);
    }

    @Override
    public void update(Change o) {

    }

    @Override
    public long registerClient(ClientRMIViewInterface clientStub) throws RemoteException {
        // TODO: registerNewPlayerAction
        return 0L;
    }

    @Override
    public void performAction(Command command, long token) throws RemoteException {
        command.accept(actionVisitor, token);
    }

    @Override
    public void run() {

    }
}
