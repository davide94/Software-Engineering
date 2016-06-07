package it.polimi.ingsw.cg26.client.view.socket;

import it.polimi.ingsw.cg26.common.change.Change;
import it.polimi.ingsw.cg26.common.observer.Observable;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 */
public class SocketInHandler extends Observable<Change> implements Runnable {

    private ObjectInputStream socketIn;

    public SocketInHandler(ObjectInputStream socketIn) {
        this.socketIn = socketIn;
    }

    @Override
    public void run() {
        while (true) {
            try {

                Object object = this.socketIn.readObject();
                //System.out.println("SocketInHandler: " + object);

                if (object instanceof Change) {
                    notifyObservers((Change) object);
                }

            } catch (EOFException e) {
                System.out.println("Server disconnected");
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
