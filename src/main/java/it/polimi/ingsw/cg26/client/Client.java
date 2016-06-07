package it.polimi.ingsw.cg26.client;

import it.polimi.ingsw.cg26.client.view.rmi.ClientRMI;
import it.polimi.ingsw.cg26.client.view.socket.ClientSocket;

import java.io.IOException;

/**
 *
 */
public class Client {

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        ClientSocket client = new ClientSocket();
        //ClientRMI client = new ClientRMI();
    }
}
