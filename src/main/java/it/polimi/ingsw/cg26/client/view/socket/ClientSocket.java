package it.polimi.ingsw.cg26.client.view.socket;

import it.polimi.ingsw.cg26.client.controller.Controller;
import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.client.view.ui.CLI;
import it.polimi.ingsw.cg26.common.change.Change;
import it.polimi.ingsw.cg26.common.change.FullStateChange;
import it.polimi.ingsw.cg26.common.change.PrivateChange;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class ClientSocket {

    private final static String DEFAULT_IP = "localhost";

    private final static int SOCKET_PORT = 29999;

    ExecutorService executor = Executors.newCachedThreadPool();

    public ClientSocket() throws IOException, ClassNotFoundException {
        startSocketClient();
    }

    public void startSocketClient() throws IOException, ClassNotFoundException {

        Scanner scanner = new Scanner(System.in);
        Socket socket;

        while (true) {

            System.out.println("Insert server ip/host-name: (Default " + DEFAULT_IP + ")");
            String ip = scanner.nextLine();
            if (ip.isEmpty())
                ip = DEFAULT_IP;

            try {
                socket = new Socket(ip, SOCKET_PORT);
                break;

            } catch (Exception e) {
                System.out.println("ip/host-name may be wrong, try again...");
            }

        }

        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

        System.out.println("Connection created, which name do you want? ");
        String name = scanner.nextLine();
        System.out.println("Waiting to start...");

        outputStream.writeObject(name);

        Object object = inputStream.readObject();
        if (!(object instanceof FullStateChange)) {
            System.out.println("Connection failed.");
            return;
        }

        Model model = new Model(((FullStateChange)object).getState());

        Controller controller = new Controller(model);

        ClientInHandler inView = new ClientInHandler(inputStream);
        inView.registerObserver(controller);

        ClientOutHandler outView = new ClientOutHandler(outputStream);

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

}
