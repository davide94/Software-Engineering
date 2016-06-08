package it.polimi.ingsw.cg26.common.change;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;

import it.polimi.ingsw.cg26.common.ClientModel;
import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;
import it.polimi.ingsw.cg26.common.rmi.ClientRMIViewInterface;

/**
 *
 */
public class PrivateChange implements Change {

    private static final long serialVersionUID = 8585646987699696361L;

    private final long token;
    
    private Change decoratedChange;

    public PrivateChange(Change decoratedChange, long token) {
        this.decoratedChange = decoratedChange;
    	this.token = token;
    }

    @Override
    public void apply(ClientModel model) {
    }
    
    @Override
    public void sendSocket(ObjectOutputStream socketOut, long token){
    	if(this.token == token){
			try {
				socketOut.writeObject(decoratedChange);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
    
    @Override
    public void sendRMI(ClientRMIViewInterface client, long token){
    	if(this.token == token){
    		try {
				client.updateClient(decoratedChange);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
    	}
    }
}
