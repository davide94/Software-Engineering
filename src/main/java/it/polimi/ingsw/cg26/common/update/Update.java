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

    /**
     * Send the update on socket
     * @param socketOut the socket in which the update has to be send
     * @param token the token of the destination player
     * @throws IOException
     */
    default void sendSocket(ObjectOutputStream socketOut, long token) throws IOException {
        socketOut.writeObject(this);
    }

    /**
     * Send the update on RMI
     * @param client the destination client
     * @param token the token of the destination player
     * @throws RemoteException
     */
    default void sendRMI(ClientRMIViewInterface client, long token) throws RemoteException {
        client.updateClient(this);
    }
}
