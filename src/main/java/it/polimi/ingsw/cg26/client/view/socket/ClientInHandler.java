package it.polimi.ingsw.cg26.client.view.socket;

import it.polimi.ingsw.cg26.common.change.Change;
import it.polimi.ingsw.cg26.common.observer.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 */
public class ClientInHandler extends Observable<Change> implements Runnable {

    private ObjectInputStream socketIn;


    public ClientInHandler(ObjectInputStream socketIn) {
        this.socketIn = socketIn;
    }

    @Override
    public void run() {
        while (true) {
            try {

                Object object = this.socketIn.readObject();
                //System.out.println("ClientInHandler: " + object);

                if (object instanceof Change)
                    notifyObservers((Change) object);

            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}