package it.polimi.ingsw.cg26.client.view.socket;

import it.polimi.ingsw.cg26.client.view.OutView;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 */
public class SocketOutHandler implements OutView {

    private final ObjectOutputStream outputStream;

    public SocketOutHandler(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void writeObject(Object o) {
        try {
            outputStream.writeObject(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
