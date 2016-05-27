package it.polimi.ingsw.cg26.client;

import it.polimi.ingsw.cg26.client.controller.Controller;
import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.client.view.CLI;
import it.polimi.ingsw.cg26.client.view.socket.ClientInHandler;
import it.polimi.ingsw.cg26.client.view.socket.ClientOutHandler;
import it.polimi.ingsw.cg26.common.change.FullStateChange;
import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class Client {

    private final static int PORT = 29999;

    private final static String IP = "127.0.0.1";

    ExecutorService executor = Executors.newCachedThreadPool();

    public void startSocketClient() throws IOException, InterruptedException, ClassNotFoundException {

        Socket socket = new Socket(IP, PORT);

        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("Connection created, which name do you want? ");
        Scanner scanner = new Scanner(System.in);
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

        CLI cli = new CLI(outView);
        model.registerObserver(cli);

        executor.submit(cli);
        executor.submit(inView);

    }

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        Client client = new Client();
        client.startSocketClient();
    }
}
