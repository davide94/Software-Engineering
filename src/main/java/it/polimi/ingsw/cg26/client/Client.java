package it.polimi.ingsw.cg26.client;

import it.polimi.ingsw.cg26.client.controller.Controller;
import it.polimi.ingsw.cg26.client.view.CLI;
import it.polimi.ingsw.cg26.client.view.socket.ClientInHandler;
import it.polimi.ingsw.cg26.common.change.FullStateChange;
import it.polimi.ingsw.cg26.common.dto.GameBoardDTO;
import it.polimi.ingsw.cg26.common.dto.PlayerDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class Client {

    private final static int PORT = 29999;

    private final static String IP = "127.0.0.1";

    public void startSocketClient() throws IOException, InterruptedException, ClassNotFoundException {

        Socket socket = new Socket(IP, PORT);

        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("Connection created, waiting to start...");

        Object object = inputStream.readObject();
        if (!(object instanceof FullStateChange))
            throw new ClassNotFoundException();

        GameBoardDTO model = ((FullStateChange)object).getState();
        PlayerDTO me = ((FullStateChange)object).getMe();

        Controller controller = new Controller(model);
        ClientInHandler inView = new ClientInHandler(inputStream);
        inView.registerObserver(controller);

        CLI outView = new CLI(outputStream, model, me);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(inView);
        executor.submit(outView);

    }

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        Client client = new Client();
        client.startSocketClient();
    }
}
