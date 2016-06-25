package it.polimi.ingsw.cg26.server;

import it.polimi.ingsw.cg26.common.rmi.ClientRMIViewInterface;
import it.polimi.ingsw.cg26.common.rmi.ServerRMIViewInterface;
import it.polimi.ingsw.cg26.common.rmi.ServerRMIWelcomeViewInterface;
import it.polimi.ingsw.cg26.common.update.change.BasicChange;
import it.polimi.ingsw.cg26.common.update.change.FullStateChange;
import it.polimi.ingsw.cg26.common.update.change.LocalPlayerChange;
import it.polimi.ingsw.cg26.server.controller.Controller;
import it.polimi.ingsw.cg26.server.creator.Creator;
import it.polimi.ingsw.cg26.server.exceptions.BadInputFileException;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import it.polimi.ingsw.cg26.server.exceptions.ParserErrorException;
import it.polimi.ingsw.cg26.server.model.Scheduler;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.player.Player;
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
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class Server {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * The starting delay
     */
    private final static int START_DELAY = 5 * 1000;

    /**
     * The number of socket Port
     */
    private final static int SOCKET_PORT = 29999;

    /**
     * The number of RMI Port
     */
    private final static int RMI_PORT = 52365;

    /**
     * The controller
     */
    private Controller controller;

    
    /**
     * The model of the game
     */
    private GameBoard model;

    /**
     * All the clients
     */
    private Map<Long, View> clients;

    /**
     * the scheduler of the gameboard
     */
    private Scheduler scheduler;

    /**
     * The executor
     */
    private final ExecutorService executor;
    
    /**
     * Server constructor 
     * @throws IOException 
     */
    private Server() throws IOException {
        clients = new LinkedHashMap<>();
        this.executor = Executors.newCachedThreadPool();
    }
    
    /**
     * Create a new game
     * @throws BadInputFileException
     * @throws ParserErrorException
     */
    private void newGame() throws BadInputFileException, ParserErrorException {
        clients = new LinkedHashMap<>();
        model = Creator.createGame("maps/1.xml");
        scheduler = model.getScheduler();
        this.controller = new Controller(model);
        log.info("New match created.");
    }

    /**
     * Start the match if there are at least two players
     */
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
    
    /**
     * Start Server Socket connections
     * @throws IOException
     * @throws ClassNotFoundException
     */
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

    /**
     * Start RMI connections
     * @throws RemoteException
     * @throws AlreadyBoundException
     */
    private void startRMI() throws RemoteException, AlreadyBoundException {
        Registry registry = LocateRegistry.createRegistry(RMI_PORT);

        ServerRMIWelcomeView rmiWelcomeView = new ServerRMIWelcomeView(this);

        ServerRMIWelcomeViewInterface viewRemote = (ServerRMIWelcomeViewInterface) UnicastRemoteObject.exportObject(rmiWelcomeView, 0); // Why 0?

        registry.bind("WELCOME_VIEW", viewRemote);

        log.info("SERVER RMI READY ON PORT " + RMI_PORT);
    }
    
    /**
     * Register a new player that is connected through socket
     * @param socket the socket connection of a player
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void registerSocketPlayer(Socket socket) throws IOException, ClassNotFoundException {
        ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        Object o = socketIn.readObject();
        if (!(o instanceof String))
            throw new ClassNotFoundException();

        String name = (String)o;

        Player player;
        try {
            player = model.registerPlayer(name);
        } catch (NoRemainingCardsException e) {
            log.error("Cannot create player because there are no politic cards remaining.", e);
            // TODO: notify client that player cannot be created
            return;
        }
        new FullStateChange(new BasicChange(), model.getState()).sendSocket(socketOut, 0);
        new LocalPlayerChange(new BasicChange(), player.getFullState()).sendSocket(socketOut, 0);

        ServerSocketView view = new ServerSocketView(socket, socketIn, socketOut, player.getToken());
        executor.submit(view);
        clients.put(player.getToken(), view);
        log.info("New player registered on Socket (" + player.getToken() + ")");

        if (clients.size() == 2) {
            new java.util.Timer().schedule(new java.util.TimerTask() {
                @Override
                public void run() {
                    start();
                }
            }, START_DELAY);
        }
    }

    /**
     * Register a new player that is connected through RMI
     * @param client the client RMI View Interface of the new player
     * @param name the name of the player
     * @return
     * @throws RemoteException
     */
    public ServerRMIViewInterface registerRMIPlayer(ClientRMIViewInterface client, String name) throws RemoteException {
        Player player;
        try {
            player = model.registerPlayer(name);
        } catch (NoRemainingCardsException e) {
            log.error("Cannot create player because there are no politic cards remaining.", e);
            // TODO: notify client that player cannot be created
            throw new RemoteException();
        }

        new FullStateChange(new BasicChange(), model.getState()).sendRMI(client, 0);
        new LocalPlayerChange(new BasicChange(), player.getFullState()).sendRMI(client, 0);
        ServerRMIView view = new ServerRMIView(client, player.getToken());
        executor.submit(view);
        clients.put(player.getToken(), view);
        log.info("New player registered on RMI (" + player.getToken() + ")");

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