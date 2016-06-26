package it.polimi.ingsw.cg26.server.view;

import it.polimi.ingsw.cg26.common.commands.Command;
import it.polimi.ingsw.cg26.common.update.Update;
import it.polimi.ingsw.cg26.common.visitor.Visitable;
import it.polimi.ingsw.cg26.server.actions.Staccah;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 */
public class ServerSocketView extends View {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Socket socket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private ActionVisitor actionVisitor;
    private final long token;
    private boolean connectionActive;
    private boolean matchStarted;

    public ServerSocketView(Socket socket, ObjectInputStream socketIn, ObjectOutputStream socketOut, long token) throws IOException {
        this.socket = socket;
        this.socketIn = socketIn;
        this.socketOut = socketOut;
        this.actionVisitor = new ActionVisitor(this, token);
        this.token = token;
        this.connectionActive = true;
        this.matchStarted = false;
    }

    @Override
    public void update(Update u) {
        try {
            u.sendSocket(socketOut, token);
            matchStarted = true;
        } catch (IOException e) {
            log.error("Error sending update with socket.", e);
        }
    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            try {
                Object object = socketIn.readObject();

                if (object instanceof Command) {
                    Visitable visitable = (Visitable) object;
                    visitable.accept(this.actionVisitor);
                }

            } catch (EOFException e) {
                log.error("CLIClient disconnected", e);
                connectionActive = false;
                if (matchStarted)
                    notifyObservers(new Staccah(token, this));
                break;
            } catch (Exception e) {
                log.error("Error sending update with Socket.", e);
            }
        }

    }

    @Override
    public boolean isConnectionAlive() {
        return connectionActive;
    }
}
