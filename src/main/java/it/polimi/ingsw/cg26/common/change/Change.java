package it.polimi.ingsw.cg26.common.change;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;

import it.polimi.ingsw.cg26.common.ClientModel;
import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;
import it.polimi.ingsw.cg26.common.rmi.ClientRMIViewInterface;

/**
 *
 */
@FunctionalInterface
public interface Change extends Serializable {

	default boolean isFor(long token) {
		return true;
	}

	/**
	 * Applies the change to the GameBoard DTO
	 * @param model the model of the client
	 */
	void apply(ClientModel model);
	
	default void sendSocket(ObjectOutputStream socketOut, long token){
		try {
			socketOut.writeObject(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	default void sendRMI(ClientRMIViewInterface client, long token){
		try {
			client.updateClient(this);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
