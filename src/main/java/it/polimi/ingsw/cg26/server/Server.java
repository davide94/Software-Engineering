package it.polimi.ingsw.cg26.server;

import it.polimi.ingsw.cg26.server.controller.Controller;
import it.polimi.ingsw.cg26.server.creator.Creator;
import it.polimi.ingsw.cg26.server.exceptions.ParserErrorException;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.view.ServerSocketView;

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

    private final static String HOST_NAME = "127.0.0.1";

    private GameBoard game;

    private Controller controller;

    public Server() {
        this.game = Creator.createGame("src/main/resources/config.xml");
        this.controller = new Controller(game);
    }

    private void startSocket() throws IOException {

        ExecutorService executor = Executors.newCachedThreadPool();

        ServerSocket serverSocket = new ServerSocket(PORT);

        System.out.println("SERVER SOCKET READY ON PORT" + PORT);

        while (true) {
            Socket socket = serverSocket.accept();

            ServerSocketView view = new ServerSocketView(socket, this.game);
            this.game.registerObserver(view);
            view.registerObserver(this.controller);
            executor.submit(view);
        }
    }

    public static void main( String[] args ) throws IOException {
        Server server = new Server();
        server.startSocket();
    }
}
