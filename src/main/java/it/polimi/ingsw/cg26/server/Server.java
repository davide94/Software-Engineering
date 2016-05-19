package it.polimi.ingsw.cg26.server;

import it.polimi.ingsw.cg26.server.controller.Controller;
import it.polimi.ingsw.cg26.server.creator.Creator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class Server {

    private final static int PORT = 29999;

    private Controller controller;

    private int playersNumber;

    private final ExecutorService executor;

    public Server() throws IOException {
        playersNumber = 0;
        this.executor = Executors.newCachedThreadPool();
    }

    private void newGame() {
        this.controller = new Controller(Creator.createGame("src/main/resources/config.xml"));
    }

    private void newPlayerRegistered() {
        this.playersNumber++;
        if (playersNumber == 2) {
            new java.util.Timer().schedule( new java.util.TimerTask() {
                @Override
                public void run() {
                    start();
                }
            }, 20000);
        }

    }

    private void start() {
        this.playersNumber = 0;
        this.executor.submit(this.controller);
        newGame();
    }

    private void startSocket() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);

        System.out.println("SERVER SOCKET READY ON PORT " + PORT);

        while (true) {
            Socket socket = serverSocket.accept();
            this.controller.registerPlayer(socket, "");
            newPlayerRegistered();
        }
        //serverSocket.close();
    }

    public static void main( String[] args ) throws IOException {
        Server server = new Server();
        server.newGame();
        server.startSocket();
    }
}
