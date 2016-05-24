package it.polimi.ingsw.cg26.server.view;

import it.polimi.ingsw.cg26.common.commands.*;
import it.polimi.ingsw.cg26.common.change.Change;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.actions.main.Acquire;
import it.polimi.ingsw.cg26.server.actions.main.Build;
import it.polimi.ingsw.cg26.server.actions.main.BuildKing;
import it.polimi.ingsw.cg26.server.actions.main.ElectAsMainAction;
import it.polimi.ingsw.cg26.server.actions.quick.AdditionalMainAction;
import it.polimi.ingsw.cg26.server.actions.quick.ChangeBPT;
import it.polimi.ingsw.cg26.server.actions.quick.ElectAsQuickAction;
import it.polimi.ingsw.cg26.server.actions.quick.EngageAssistant;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 */
public class ServerSocketView extends View {

    private Socket socket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;

    public ServerSocketView(Socket socket) throws IOException {
        this.socket = socket;
        this.socketOut = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public void update(Change o) {
        System.out.println("Sending to the client " + o);
        try {
            socketOut.writeObject(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            socketIn = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean staccah = false;
        while (!staccah) {
            try {
                Object object = socketIn.readObject();

                if (object instanceof Staccah) {
                    System.out.println("STACCAH STACCAH STACCAH");
                    staccah = true;
                }

                if (object instanceof ElectAsMainActionCommand) {
                    ElectAsMainActionCommand command = (ElectAsMainActionCommand) object;
                    Action action = new ElectAsMainAction(command.getRegion(), command.getCouncillor());
                    notifyObservers(action);
                }
                if (object instanceof AcquireCommand) {
                    AcquireCommand command = (AcquireCommand) object;
                    Action action = new Acquire(command.getRegion(), command.getCards(), command.getPosition());
                    notifyObservers(action);
                }
                if (object instanceof BuildCommand) {
                    BuildCommand command = (BuildCommand) object;
                    Action action = new Build(command.getCity());
                    notifyObservers(action);
                }
                if (object instanceof BuildKingCommand) {
                    BuildKingCommand command = (BuildKingCommand) object;
                    Action action = new BuildKing(command.getCity(), command.getCards());
                    notifyObservers(action);
                }
                if (object instanceof EngageAssistantCommand) {
                    EngageAssistantCommand command = (EngageAssistantCommand) object;
                    Action action = new EngageAssistant();
                    notifyObservers(action);
                }
                if (object instanceof ChangeBPTCommand) {
                    ChangeBPTCommand command = (ChangeBPTCommand) object;
                    Action action = new ChangeBPT(command.getRegion());
                    notifyObservers(action);
                }
                if (object instanceof ElectAsQuickActionCommand) {
                    ElectAsQuickActionCommand command = (ElectAsQuickActionCommand) object;
                    Action action = new ElectAsQuickAction(command.getRegion(), command.getCouncillor());
                    notifyObservers(action);
                }
                if (object instanceof AdditionalMainActionCommand) {
                    AdditionalMainActionCommand command = (AdditionalMainActionCommand) object;
                    Action action = new AdditionalMainAction();
                    notifyObservers(action);
                }


            } catch (EOFException e) {
                System.out.println("Client disconnected");
                break;
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
