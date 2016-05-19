package it.polimi.ingsw.cg26.server.view;

import it.polimi.ingsw.cg26.client.commands.Command;
import it.polimi.ingsw.cg26.client.commands.Staccah;
import it.polimi.ingsw.cg26.server.change.Change;

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

    public ServerSocketView(Socket socket) throws IOException {
        this.socket = socket;
        this.socketIn = new ObjectInputStream(socket.getInputStream());
        this.socketOut = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public void update(Change o) {
        System.out.println("Sending to the client " + o);

        /*try {
            this.socketOut.writeObject(o);
            this.socketOut.flush();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }*/
    }

    @Override
    public void run() {
        boolean staccah = false;
        while (!staccah) {
            try {
                Object object = socketIn.readObject();

                if (object instanceof Staccah) {
                    System.out.println("STACCAH STACCAH STACCAH");
                    // TODO agire di conseguenza;
                    staccah = true;
                }

                if (object instanceof Command) {
                    Command command = (Command) object;
                    this.notifyObservers(command.generateAction());
                }


            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
