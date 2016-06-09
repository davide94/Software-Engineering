package it.polimi.ingsw.cg26.client.view.rmi;

import it.polimi.ingsw.cg26.client.view.OutView;
import it.polimi.ingsw.cg26.common.commands.Command;
import it.polimi.ingsw.cg26.common.rmi.ServerRMIViewInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;

/**
 *
 */
public class ClientRMIOutView implements OutView {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private ServerRMIViewInterface server;

    public ClientRMIOutView(ServerRMIViewInterface server) {
        this.server = server;
    }

    @Override
    public void writeObject(Command c) {
        try {
            server.performAction(c);
        } catch (RemoteException e) {
            log.error("Error forwarding action to server with RMI.", e);
        }
    }
}
