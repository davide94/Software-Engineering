package it.polimi.ingsw.cg26.common.update;

import it.polimi.ingsw.cg26.common.ClientModel;
import it.polimi.ingsw.cg26.common.rmi.ClientRMIViewInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;

/**
 *
 */
public class PrivateUpdate implements Update {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final long serialVersionUID = 8585646987699696361L;

    private final long token;

    private Update carried;

    public PrivateUpdate(Update carried, long token) {
        this.carried = carried;
        this.token = token;
    }

    @Override
    public void apply(ClientModel model) {
    }

    @Override
    public void sendSocket(ObjectOutputStream socketOut, long token) throws IOException {
        if(this.token == token){
            socketOut.writeObject(carried);
        }
    }

    @Override
    public void sendRMI(ClientRMIViewInterface client, long token) throws RemoteException {
        client.updateClient(carried);
    }
}
