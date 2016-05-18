package it.polimi.ingsw.cg26.server.view;

import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.update.Update;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 */
public class ServerSocketView extends View implements Runnable {

    private Socket socket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private GameBoard model;

    public ServerSocketView(Socket socket, GameBoard model) throws IOException {
        this.socket = socket;
        this.socketIn = new ObjectInputStream(socket.getInputStream());
        this.socketOut = new ObjectOutputStream(socket.getOutputStream());
        this.model=model;
    }

    @Override
    public void update(Update o) {
        System.out.println("Sending to the client " + o);

        try {
            this.socketOut.writeObject(o);
            this.socketOut.flush();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {

            try {

                Object object = socketIn.readObject();
                if (object instanceof Action) {
                    Action action = (Action) object;
                    System.out.println("VIEW: received the action " + action);

                    this.notifyObservers(action);
                }

                    /*if (object instanceof Query) {
                        System.out.println("SERVER VIEW: received query: " + object);
                        Query query = (Query) object;
                        this.socketOut.writeObject(query.perform(model));
                        this.socketOut.flush();
                    }*/


            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
