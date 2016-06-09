package it.polimi.ingsw.cg26.client.view.socket;

import it.polimi.ingsw.cg26.common.observer.Observable;
import it.polimi.ingsw.cg26.common.update.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 */
public class ClientSocketInView extends Observable<Update> implements Runnable {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private ObjectInputStream socketIn;

    public ClientSocketInView(ObjectInputStream socketIn) {
        this.socketIn = socketIn;
    }

    @Override
    public void run() {
        while (true) {
            try {

                Object object = this.socketIn.readObject();
                //System.out.println("ClientSocketInView: " + object);

                if (object instanceof Update) {
                    notifyObservers((Update) object);
                }

            } catch (EOFException e) {
                System.out.println("Server disconnected.");
                log.error("Server disconnected.", e);
                break;
            } catch (Exception e) {
                log.error("Error reading from socket.", e);
            }
        }
    }
}
