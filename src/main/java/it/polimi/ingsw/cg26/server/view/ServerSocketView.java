package it.polimi.ingsw.cg26.server.view;

import it.polimi.ingsw.cg26.common.commands.Staccah;
import it.polimi.ingsw.cg26.common.update.Update;
import it.polimi.ingsw.cg26.common.visitor.Visitable;
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

    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());

    private Socket socket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private ActionVisitor actionVisitor;
    private final long token;

    public ServerSocketView(Socket socket, ObjectInputStream socketIn, ObjectOutputStream socketOut, long token) throws IOException {
        this.socket = socket;
        this.socketIn = socketIn;
        this.socketOut = socketOut;
        this.actionVisitor = new ActionVisitor(this, token);
        this.token = token;
    }

    @Override
    public void update(Update u) {
        try {
            u.sendSocket(socketOut, token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        while (true) {
            try {
                Object object = socketIn.readObject();

                if (object instanceof Staccah) {
                    System.out.println("STACCAH STACCAH STACCAH");
                    break;
                }

                Visitable visitable = (Visitable) object;
                visitable.accept(this.actionVisitor);

            } catch (EOFException e) {
                log.info("Client disconnected");
                break;
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
