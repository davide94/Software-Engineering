package it.polimi.ingsw.cg26.client.view.socket;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.change.Change;
import it.polimi.ingsw.cg26.common.observer.Observable;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 */
public class ClientInHandler extends Observable<Change> implements Runnable {

    private final Model model;

    private ObjectInputStream socketIn;

    public ClientInHandler(ObjectInputStream socketIn, Model model) {
        this.socketIn = socketIn;
        this.model = model;
    }

    @Override
    public void run() {
        while (true) {
            try {

                Object object = this.socketIn.readObject();
                //System.out.println("ClientInHandler: " + object);

                if (object instanceof Change) {
                    ((Change) object).apply(model.getGameBoard());
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
