package it.polimi.ingsw.cg26.server.view;

import it.polimi.ingsw.cg26.common.change.Change;
import it.polimi.ingsw.cg26.common.commands.Command;
import it.polimi.ingsw.cg26.common.rmi.ClientRMIViewInterface;
import it.polimi.ingsw.cg26.common.rmi.ServerRMIViewInterface;

import java.rmi.RemoteException;

/**
 *
 */
public class ServerRMIView extends View implements ServerRMIViewInterface {

    private ClientRMIViewInterface client;

    private ActionVisitor actionVisitor;

    public ServerRMIView(ClientRMIViewInterface client, long token) {
        this.client = client;
        actionVisitor = new ActionVisitor(this, token);
    }

    @Override
    public void update(Change o) {
        try {
            client.updateClient(o);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void performAction(Command c) throws RemoteException {
        c.accept(actionVisitor);
    }

    @Override
    public void run() {

    }
}
