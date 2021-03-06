package it.polimi.ingsw.cg26.server.view;

import it.polimi.ingsw.cg26.common.commands.Command;
import it.polimi.ingsw.cg26.common.rmi.ClientRMIViewInterface;
import it.polimi.ingsw.cg26.common.rmi.ServerRMIViewInterface;
import it.polimi.ingsw.cg26.common.update.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;

/**
 *
 */
public class ServerRMIView extends View implements ServerRMIViewInterface {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private ClientRMIViewInterface client;

    private ActionVisitor actionVisitor;

    private long token;

    public ServerRMIView(ClientRMIViewInterface client, long token) {
        this.client = client;
        actionVisitor = new ActionVisitor(this, token);
        this.token = token;
    }

    @Override
    public void update(Update u) {
        try {
            u.sendRMI(client, token);
        } catch (RemoteException e) {
            log.error("Error sending update with RMI.", e);
        }
    }

    @Override
    public void performAction(Command c) throws RemoteException {
        c.accept(actionVisitor);
    }

    @Override
    public void run() {

    }

    @Override
    public boolean isConnectionAlive() {
        try {
            return client.checkConnection();
        } catch (RemoteException e) {
            log.error("CLIClient disconnected.", e);
            return false;
        }
    }
}
