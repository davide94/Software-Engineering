package it.polimi.ingsw.cg26.client.view.socket;

import it.polimi.ingsw.cg26.client.view.OutView;
import it.polimi.ingsw.cg26.common.commands.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 */
public class ClientSocketOutView implements OutView {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ObjectOutputStream outputStream;

    public ClientSocketOutView(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void writeObject(Command c) {
        try {
            outputStream.writeObject(c);
        } catch (IOException e) {
            log.error("Error writing to socket.", e);
        }
    }
}
