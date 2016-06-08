package it.polimi.ingsw.cg26.server.view;

import it.polimi.ingsw.cg26.common.change.FullStateChange;
import it.polimi.ingsw.cg26.common.commands.*;
import it.polimi.ingsw.cg26.common.visitor.Visitable;
import it.polimi.ingsw.cg26.common.change.Change;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 */
public class ServerSocketView extends View {

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
    public void update(Change c) {
        c.sendSocket(socketOut, token);
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
                System.out.println("Client disconnected");
                break;
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }

}
