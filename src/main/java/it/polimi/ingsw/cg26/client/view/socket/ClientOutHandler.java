package it.polimi.ingsw.cg26.client.view.socket;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 */
public class ClientOutHandler {

    private final ObjectOutputStream outputStream;

    public ClientOutHandler(ObjectOutputStream outputStream) {
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
