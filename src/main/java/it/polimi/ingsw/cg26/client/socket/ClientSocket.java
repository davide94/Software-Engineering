package it.polimi.ingsw.cg26.client.socket;

import it.polimi.ingsw.cg26.client.CLI;
import it.polimi.ingsw.cg26.common.state.BoardState;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class ClientSocket {

    private final static int PORT = 29999;

    private final static String IP = "127.0.0.1";

    private BoardState model;

    public void startClient() throws IOException, InterruptedException, ClassNotFoundException {

        Socket socket = new Socket(IP, PORT);
        System.out.println("Connection created");

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new CLI(new ObjectOutputStream(socket.getOutputStream()), this));
        executor.submit(new ClientInHandler(new ObjectInputStream(socket.getInputStream()), this));

        executor.shutdown();
        executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
        socket.close();
    }

    public void setState(BoardState model) {
        this.model = model;
    }

    public BoardState getState() {
        return model;
    }

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        ClientSocket client=new ClientSocket();
        client.startClient();
    }
}