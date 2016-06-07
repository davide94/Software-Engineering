package it.polimi.ingsw.cg26.server;

import it.polimi.ingsw.cg26.common.rmi.ServerRMIViewInterface;
import it.polimi.ingsw.cg26.server.controller.Controller;
import it.polimi.ingsw.cg26.server.creator.Creator;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;
import it.polimi.ingsw.cg26.server.view.ServerRMIView;
import it.polimi.ingsw.cg26.server.view.ServerSocketView;
import it.polimi.ingsw.cg26.server.view.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class Server {

    private final static int START_DELAY = 1000;

    private final static int SOCKET_PORT = 29999;

    private final static int RMI_PORT = 52365;

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
        ServerSocket serverSocket = new ServerSocket(SOCKET_PORT);

        System.out.println("SERVER SOCKET READY ON PORT " + SOCKET_PORT);

        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(() -> {
                try {
                    registerSocketPlayer(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void registerSocketPlayer(Socket socket) throws IOException, ClassNotFoundException {
        ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        Object o = socketIn.readObject();
        if (!(o instanceof String))
            throw new ClassNotFoundException();

        String name = (String)o;

        playersNumber++;

        long token = model.registerPlayer(name);
        View view = new ServerSocketView(socket, socketIn, socketOut, token);
        view.registerObserver(this.controller);
        model.registerObserver(view);
        executor.submit(view);

        if (playersNumber == 2) {
            new java.util.Timer().schedule(new java.util.TimerTask() {
                @Override
                public void run() {
                    start();
                }
            }, START_DELAY);
        }
    }

    private void startRMI() throws RemoteException, AlreadyBoundException {
        Registry registry = LocateRegistry.createRegistry(RMI_PORT);
        System.out.println("Constructing RMI registry");

        ServerRMIView rmiView = new ServerRMIView();
        rmiView.registerObserver(this.controller);
        model.registerObserver(rmiView);

        ServerRMIViewInterface viewRemote = (ServerRMIViewInterface) UnicastRemoteObject.exportObject(rmiView, 0);

        registry.bind("NOME_DA_DECIDERE", rmiView);
    }

    public static void main( String[] args ) throws IOException, ClassNotFoundException, AlreadyBoundException {
        Server server = new Server();
        server.newGame();
        server.startSocket();
        server.startRMI();
    }

}
