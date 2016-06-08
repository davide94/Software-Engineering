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

    private long token;

    public ServerRMIView(ClientRMIViewInterface client, long token) {
        this.client = client;
        actionVisitor = new ActionVisitor(this, token);
        this.token = token;
    }

    @Override
    public void update(Change c) {
        //System.out.println("Sending to the client " + o);
        if (!c.isFor(token))
            return;

        try {
            client.updateClient(c);
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
