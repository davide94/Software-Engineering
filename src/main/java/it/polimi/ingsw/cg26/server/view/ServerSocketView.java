package it.polimi.ingsw.cg26.server.view;

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

    public ServerSocketView(Socket socket) throws IOException {
        this.socket = socket;
        this.socketOut = new ObjectOutputStream(socket.getOutputStream());
        this.actionVisitor = new ActionVisitor(this);
    }

    @Override
    public void update(Change o) {
        System.out.println("Sending to the client " + o);
        try {
            socketOut.writeObject(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            socketIn = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
