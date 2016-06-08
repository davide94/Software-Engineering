package it.polimi.ingsw.cg26.client;

import it.polimi.ingsw.cg26.client.controller.Controller;
import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.client.view.OutView;
import it.polimi.ingsw.cg26.client.view.rmi.ClientRMIInView;
import it.polimi.ingsw.cg26.client.view.rmi.ClientRMIOutView;
import it.polimi.ingsw.cg26.client.view.socket.ClientSocketInView;
import it.polimi.ingsw.cg26.client.view.socket.ClientSocketOutView;
import it.polimi.ingsw.cg26.client.view.ui.CLI;import it.polimi.ingsw.cg26.common.rmi.ServerRMIViewInterface;
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

    private Model model;

    private Controller controller;

    public Client() {
        executor = Executors.newCachedThreadPool();
        model = new Model();
        controller = new Controller(model);
    }

    private void startClient() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What's your name?");
        String name = scanner.nextLine();
        System.out.println("Do you want to use sockets(S) or RMI(r)?");
        boolean useSocket = true;
        if (scanner.nextLine().equalsIgnoreCase("r"))
            useSocket = false;

        OutView outView;

        while (true) {
            System.out.println("Insert server ip/host-name: (Default: " + DEFAULT_IP + ")");
            String ip = scanner.nextLine();
            if (ip.isEmpty())
                ip = DEFAULT_IP;

            try {
                if (useSocket)
                    outView = startSocketClient(ip, DEFAULT_SOCKET_PORT, name);
                else
                    outView = startRMIClient(ip, DEFAULT_RMI_PORT, name);
                break;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("ip/host-name may be wrong, try again...");
            }
        }

        CLI cli = new CLI(new Scanner(System.in), new PrintWriter(System.out), outView);
        model.registerObserver(cli);
        executor.submit(cli);
    }

    private OutView startSocketClient(String ip, int port, String name) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(ip, port);
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

        System.out.println("Connection created, waiting to start...");

        outputStream.writeObject(name);

        ClientSocketInView inView = new ClientSocketInView(inputStream);
        inView.registerObserver(controller);
        executor.submit(inView);
        return new ClientSocketOutView(outputStream);
    }

    private OutView startRMIClient(String ip, int port, String name) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(ip, port);
        ServerRMIWelcomeViewInterface welcomeView = (ServerRMIWelcomeViewInterface) registry.lookup(INTERFACE_NAME);

        ClientRMIInView inView = new ClientRMIInView();
        inView.registerObserver(controller);
        executor.submit(inView);

        ServerRMIViewInterface server = welcomeView.registerClient(inView, name);
        System.out.println("Connection created, waiting to start...");

        return new ClientRMIOutView(server);
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.startClient();
    }
}
