package it.polimi.ingsw.cg26.common.update;

import it.polimi.ingsw.cg26.common.ClientModel;
import it.polimi.ingsw.cg26.common.rmi.ClientRMIViewInterface;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 *
 */
@FunctionalInterface
public interface Update extends Serializable {

    /**
     * Applies the update to the GameBoard DTO
     * @param model the model of the client
     */
    void apply(ClientModel model);

    default void sendSocket(ObjectOutputStream socketOut, long token) throws IOException {
        socketOut.writeObject(this);
    }

    default void sendRMI(ClientRMIViewInterface client, long token) throws RemoteException {
        client.updateClient(this);
    }
}
