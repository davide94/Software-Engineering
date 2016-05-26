package it.polimi.ingsw.cg26.server.view;

import it.polimi.ingsw.cg26.common.change.FullStateChange;
import it.polimi.ingsw.cg26.common.commands.*;
import it.polimi.ingsw.cg26.common.visitor.Visitable;
import it.polimi.ingsw.cg26.common.change.Change;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 */
public class ServerSocketView extends View {

    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private ActionVisitor actionVisitor;
    private final long token;

    public ServerSocketView(ObjectInputStream socketIn, ObjectOutputStream socketOut, long token) throws IOException {
        this.socketIn = socketIn;
        this.socketOut = socketOut;
        this.actionVisitor = new ActionVisitor(this, token);
        this.token = token;
    }

    @Override
    public void update(Change o) {
        //System.out.println("Sending to the client " + o);
        if (!(o instanceof Change) || !((Change)o).isFor(token))
            return;

        try {
            socketOut.writeObject(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        boolean staccah = false;
        while (!staccah) {
            try {
                Object object = socketIn.readObject();

                if (object instanceof Staccah) {
                    System.out.println("STACCAH STACCAH STACCAH");
                    staccah = true;
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
