package it.polimi.ingsw.cg26.client;

import it.polimi.ingsw.cg26.client.controller.Controller;
import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.client.view.rmi.ClientRMIView;
import it.polimi.ingsw.cg26.client.view.socket.SocketInHandler;
import it.polimi.ingsw.cg26.client.view.socket.SocketOutHandler;
import it.polimi.ingsw.cg26.client.view.ui.CLI;
import it.polimi.ingsw.cg26.common.change.Change;
import it.polimi.ingsw.cg26.common.change.FullStateChange;
import it.polimi.ingsw.cg26.common.change.PrivateChange;
import it.polimi.ingsw.cg26.common.rmi.ServerRMIViewInterface;
import it.polimi.ingsw.cg26.common.rmi.ServerRMIWelcomeViewInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class Client {

    private final static String DEFAULT_IP = "localhost";

    private final static int DEFAULT_SOCKET_PORT = 29999;

    private final static int DEFAULT_RMI_PORT = 52365;

    private final static String INTERFACE_NAME = "WELCOME_VIEW";

    private ExecutorService executor;

    public Client() {
        executor = Executors.newCachedThreadPool();
        startClient();
    }

    private void startClient() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What's your name?");
        String name = scanner.nextLine();
        System.out.println("Do you want to use sockets(S) or RMI(r)?");
        boolean useSocket = true;
        if (scanner.nextLine().equalsIgnoreCase("r"))
            useSocket = false;

        while (true) {
            System.out.println("Insert server ip/host-name: (Default: " + DEFAULT_IP + ")");
            String ip = scanner.nextLine();
            if (ip.isEmpty())
                ip = DEFAULT_IP;

            try {
                if (useSocket)
                    startSocketClient(new Socket(ip, DEFAULT_SOCKET_PORT), name);
                else
                    startRMIClient(ip, DEFAULT_RMI_PORT);
                break;
            } catch (Exception e) {
                System.out.println("ip/host-name may be wrong, try again...");
            }
        }

    }

    private void startSocketClient(Socket socket, String name) throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

        System.out.println("Connection created, waiting to start...");

        outputStream.writeObject(name);

        Object object = inputStream.readObject();
        if (!(object instanceof FullStateChange)) {
            System.out.println("Connection failed.");
            return;
        }

        Model model = new Model(((FullStateChange)object).getState());

        Controller controller = new Controller(model);

        SocketInHandler inView = new SocketInHandler(inputStream);
        inView.registerObserver(controller);

        SocketOutHandler outView = new SocketOutHandler(outputStream);

        CLI cli = new CLI(new Scanner(System.in), new PrintWriter(System.out), outView);
        model.registerObserver(cli);

        object = inputStream.readObject();
        if (!(object instanceof PrivateChange)) {
            System.out.println("Connection failed.");
            return;
        }

        inView.notifyObservers((Change)object);

        executor.submit(cli);
        executor.submit(inView);
    }

    private void startRMIClient(String ip, int port) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(ip, port);
        ServerRMIWelcomeViewInterface welcomeView = (ServerRMIWelcomeViewInterface) registry.lookup(INTERFACE_NAME);

        //ServerRMIViewInterface i = welcomeView.registerClient()

    }

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {

    }
}
