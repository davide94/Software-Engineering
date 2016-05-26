package it.polimi.ingsw.cg26.server;

import it.polimi.ingsw.cg26.server.controller.Controller;
import it.polimi.ingsw.cg26.server.creator.Creator;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;
import it.polimi.ingsw.cg26.server.view.ServerSocketView;
import it.polimi.ingsw.cg26.server.view.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.jar.Attributes;

/**
 *
 */
public class Server {

    private final static int PORT = 29999;

    private static final int INITIAL_CARDS_NUMBER = 6;

    private Controller controller;

    private GameBoard model;

    private int playersNumber;

    private final ExecutorService executor;

    public Server() throws IOException {
        playersNumber = 0;
        this.executor = Executors.newCachedThreadPool();
    }

    private void newGame() {
        model = Creator.createGame("src/main/resources/config.xml");
        this.controller = new Controller(model);
    }

    private void start() {
        this.playersNumber = 0;
        this.executor.submit(this.controller);
        newGame();
    }

    private void startSocket() throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(PORT);

        System.out.println("SERVER SOCKET READY ON PORT " + PORT);

        while (true) {
            Socket socket = serverSocket.accept();
            registerPlayer(socket);
        }
    }

    public void registerPlayer(Socket socket) throws IOException, ClassNotFoundException {
        ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        Object o = socketIn.readObject();
        if (!(o instanceof String))
            throw new ClassNotFoundException();

        String name = (String)o;

        Player player = newPlayer(playersNumber, name);
        playersNumber++;
        if (playersNumber == 2) {
            new java.util.Timer().schedule(new java.util.TimerTask() {
                @Override
                public void run() {
                    start();
                }
            }, 5000);
        }

        model.registerPlayer(player);
        View view = new ServerSocketView(socketIn, socketOut, player.getToken());
        view.registerObserver(this.controller);
        model.registerObserver(view);
        executor.submit(view);

    }

    private Player newPlayer(int playerNumber, String name) {
        if (name.equals(""))
            name = "Player_" + playerNumber;
        LinkedList<Assistant> assistants = new LinkedList<>();
        for (int i = 0; i <= playerNumber; i++)
            assistants.add(new Assistant());
        LinkedList<PoliticCard> cards = new LinkedList<>();
        for (int i = 0; i < INITIAL_CARDS_NUMBER; i++)
            cards.add(model.getPoliticDeck().draw());
        long token = new BigInteger(64, new SecureRandom()).longValue();
        return new Player(token, name, model.getNobilityTrack().getFirstCell(), playerNumber + 10, cards, assistants);
    }

    public static void main( String[] args ) throws IOException, ClassNotFoundException {
        Server server = new Server();
        server.newGame();
        server.startSocket();
    }

}
