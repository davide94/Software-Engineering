package it.polimi.ingsw.cg26.client.socket;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 */
public class ClientInHandler implements Runnable {

    private ObjectInputStream socketIn;

    public ClientInHandler(ObjectInputStream socketIn) {
        this.socketIn = socketIn;
    }

    @Override
    public void run() {
        /*while (true) {
            try {

                Object object = this.socketIn.readObject();
                System.out.println(object);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }*/
    }
}
