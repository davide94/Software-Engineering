package it.polimi.ingsw.cg26.client.view.socket;

import it.polimi.ingsw.cg26.client.view.OutView;
import it.polimi.ingsw.cg26.common.commands.Command;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 */
public class ClientSocketOutView implements OutView {

    private final ObjectOutputStream outputStream;

    public ClientSocketOutView(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void writeObject(Command c) {
        try {
            outputStream.writeObject(c);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
