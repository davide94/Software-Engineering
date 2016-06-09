package it.polimi.ingsw.cg26.common.update;

import it.polimi.ingsw.cg26.common.ClientModel;
import it.polimi.ingsw.cg26.common.rmi.ClientRMIViewInterface;
import it.polimi.ingsw.cg26.common.update.change.Change;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;

/**
 *
 */
public class PrivateUpdate implements Update {

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
    public void sendSocket(ObjectOutputStream socketOut, long token){
        if(this.token == token){
            try {
                socketOut.writeObject(carried);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sendRMI(ClientRMIViewInterface client, long token){
        if(this.token == token){
            try {
                client.updateClient(carried);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
