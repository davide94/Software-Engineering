package it.polimi.ingsw.cg26.client.socket;

import it.polimi.ingsw.cg26.common.change.FullStateChange;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 */
public class ClientInHandler implements Runnable {

    private ObjectInputStream socketIn;

    private ClientSocket clientSocket;

    public ClientInHandler(ObjectInputStream socketIn, ClientSocket client) {
        this.socketIn = socketIn;
        this.clientSocket = client;
    }

    @Override
    public void run() {
        while (true) {
            try {

                Object object = this.socketIn.readObject();
                //System.out.println("ClientInHandler: " + object);

                if (object instanceof FullStateChange) {
                    FullStateChange change = (FullStateChange) object;
                    this.clientSocket.setState(change.getState());
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
