package it.polimi.ingsw.cg26.common.update;

import it.polimi.ingsw.cg26.common.ClientModel;
import it.polimi.ingsw.cg26.common.rmi.ClientRMIViewInterface;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;

/**
 *
 */
public class PrivateUpdate implements Update {

    private static final long serialVersionUID = 8585646987699696361L;

    /**
     * The token of the destination player
     */
    private final long token;

    /**
     * The update to send
     */
    private Update carried;

    /**
     * Construct a private update
     * @param carried the update to make private
     * @param token the token of the destination player
     * @throws NullPointerException if the update carried is null
     */
    public PrivateUpdate(Update carried, long token) {
    	if(carried == null)
    		throw new NullPointerException();
        this.carried = carried;
        this.token = token;
    }

    @Override
    public void apply(ClientModel model) {
    	//nothing to do here
    }

    @Override
    public void sendSocket(ObjectOutputStream socketOut, long token) throws IOException {
        if(this.token == token)
            socketOut.writeObject(carried);
    }

    @Override
    public void sendRMI(ClientRMIViewInterface client, long token) throws RemoteException {
        if(this.token == token)
            client.updateClient(carried);
    }
}
