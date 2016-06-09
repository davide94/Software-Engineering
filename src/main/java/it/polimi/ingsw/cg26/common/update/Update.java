package it.polimi.ingsw.cg26.common.update;

import it.polimi.ingsw.cg26.common.ClientModel;
import it.polimi.ingsw.cg26.common.rmi.ClientRMIViewInterface;
import it.polimi.ingsw.cg26.server.exceptions.InvalidCityException;
import it.polimi.ingsw.cg26.server.exceptions.InvalidRegionException;
import it.polimi.ingsw.cg26.server.exceptions.PlayerNotFoundException;

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
    void apply(ClientModel model) throws InvalidRegionException, InvalidCityException, PlayerNotFoundException;

    default void sendSocket(ObjectOutputStream socketOut, long token) throws IOException {
        socketOut.writeObject(this);
    }

    default void sendRMI(ClientRMIViewInterface client, long token) throws RemoteException {
        client.updateClient(this);
    }
}
