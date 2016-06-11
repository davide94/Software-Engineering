package it.polimi.ingsw.cg26.server;

import it.polimi.ingsw.cg26.common.rmi.ClientRMIViewInterface;
import it.polimi.ingsw.cg26.common.rmi.ServerRMIViewInterface;
import it.polimi.ingsw.cg26.common.rmi.ServerRMIWelcomeViewInterface;
import it.polimi.ingsw.cg26.server.controller.Controller;
import it.polimi.ingsw.cg26.server.creator.Creator;
import it.polimi.ingsw.cg26.server.exceptions.BadInputFileException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import it.polimi.ingsw.cg26.server.exceptions.ParserErrorException;
import it.polimi.ingsw.cg26.server.model.Scheduler;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.view.ServerRMIView;
import it.polimi.ingsw.cg26.server.view.ServerRMIWelcomeView;
import it.polimi.ingsw.cg26.server.view.ServerSocketView;
import it.polimi.ingsw.cg26.server.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class Server {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final static int START_DELAY = 5000;

    private final static int SOCKET_PORT = 29999;

    private final static int RMI_PORT = 52365;

    private Controller controller;

    private GameBoard model;

    private Map<Long, View> clients;

    private Scheduler scheduler;

    private final ExecutorService executor;

    public Server() throws IOException {
        clients = new LinkedHashMap<>();
        this.executor = Executors.newCachedThreadPool();
    }

    private void newGame() throws BadInputFileException, ParserErrorException {
        clients = new LinkedHashMap<>();
        model = Creator.createGame("src/main/resources/config.xml");
        scheduler = model.getScheduler();
        this.controller = new Controller(model);
        log.info("New match created.");
    }

    private void start() {
        log.info("It's time to start the match.");
        for (Map.Entry e: clients.entrySet()) {
            View view = (View) e.getValue();
            long token = (Long) e.getKey();
            if (!view.isConnectionAlive()) {
                log.info(token + " is offline.");
                clients.remove(token);
                scheduler.killPlayer(token);
            }
        }

        if (clients.size() < 2) {
            log.info("Not enough players.");
            return;
        }

        for (Map.Entry e: clients.entrySet()) {
            View view = (View) e.getValue();
            long token = (Long) e.getKey();
            log.info("Submitting " + token + "'s view");
            view.registerObserver(this.controller);
            model.registerObserver(view);
        }

        this.executor.submit(this.controller);
        try {
            newGame();
        } catch (BadInputFileException e) {
            log.error("Error creating game, bad input file.", e);
        } catch (ParserErrorException e) {
            log.error("Error creating game, parser error.", e);
        }
    }

    private void startSocket() throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(SOCKET_PORT);

        log.info("SERVER SOCKET READY ON PORT " + SOCKET_PORT);

        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(() -> {
                try {
                    registerSocketPlayer(socket);
                } catch (Exception e) {
                    log.error("Error registering player connected on socket.", e);
                }
            }).start();
        }
    }

    private void startRMI() throws RemoteException, AlreadyBoundException {
        Registry registry = LocateRegistry.createRegistry(RMI_PORT);

        ServerRMIWelcomeView rmiWelcomeView = new ServerRMIWelcomeView(this);

        ServerRMIWelcomeViewInterface viewRemote = (ServerRMIWelcomeViewInterface) UnicastRemoteObject.exportObject(rmiWelcomeView, 0); // Why 0?

        registry.bind("WELCOME_VIEW", viewRemote);

        log.info("SERVER RMI READY ON PORT " + RMI_PORT);
    }

    private void registerSocketPlayer(Socket socket) throws IOException, ClassNotFoundException {
        ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        Object o = socketIn.readObject();
        if (!(o instanceof String))
            throw new ClassNotFoundException();

        String name = (String)o;

        long token = 0;
        try {
            token = model.registerPlayer(name);
        } catch (NoRemainingCardsException e) {
            log.error("Cannot create player because there are no politic cards remaining.", e);
            // TODO: notify client that player cannot be created
        }
        ServerSocketView view = new ServerSocketView(socket, socketIn, socketOut, token);
        executor.submit(view);
        clients.put(token, view);
        log.info("New player registered on Socket (" + token + ")");

        if (clients.size() == 2) {
            new java.util.Timer().schedule(new java.util.TimerTask() {
                @Override
                public void run() {
                    start();
                }
            }, START_DELAY);
        }
    }

    public ServerRMIViewInterface registerRMIPlayer(ClientRMIViewInterface client, String name) throws RemoteException {
        long token = 0;
        try {
            token = model.registerPlayer(name);
        } catch (NoRemainingCardsException e) {
            log.error("Cannot create player because there are no politic cards remaining.", e);
            // TODO: notify client that player cannot be created
        }
        ServerRMIView view = new ServerRMIView(client, token);
        executor.submit(view);
        clients.put(token, view);
        log.info("New player registered on RMI (" + token + ")");

        if (clients.size() == 2) {
            new java.util.Timer().schedule(new java.util.TimerTask() {
                @Override
                public void run() {
                    start();
                }
            }, START_DELAY);
        }

        return (ServerRMIViewInterface) UnicastRemoteObject.exportObject(view, 0);
    }

    public static void main( String[] args ) throws IOException, ClassNotFoundException, AlreadyBoundException, BadInputFileException, ParserErrorException {
        Server server = new Server();
        server.newGame();
        server.startRMI();
        server.startSocket();
    }

}
