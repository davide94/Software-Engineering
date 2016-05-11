package it.polimi.ingsw.cg26.view;

import it.polimi.ingsw.cg26.actions.Action;
import it.polimi.ingsw.cg26.actions.main.Acquire;
import it.polimi.ingsw.cg26.actions.main.Build;
import it.polimi.ingsw.cg26.actions.main.BuildKing;
import it.polimi.ingsw.cg26.actions.main.Elect;
import it.polimi.ingsw.cg26.actions.quick.AdditionalMainAction;
import it.polimi.ingsw.cg26.actions.quick.ChangeBPT;
import it.polimi.ingsw.cg26.actions.quick.ElectAsQuickAction;
import it.polimi.ingsw.cg26.actions.quick.EngageAssistant;
import it.polimi.ingsw.cg26.observer.Observable;
import it.polimi.ingsw.cg26.observer.Observer;
import it.polimi.ingsw.cg26.update.Update;

import java.util.Scanner;

/**
 * 
 */
public class View extends Observable<Action> implements Observer<Update>, Runnable {

    private Scanner scanner = new Scanner(System.in);

    public View() {

    }

    @Override
    public void run() {
        waitInput();
    }

    @Override
    public void update(Update update) {
        // TODO
    }

    private void waitInput() {
        System.out.println("Main actions:" +
                "\n(1) Elect a Councillor" +
                "\n(2) Acquire a Business Permit Tile" +
                "\n(3) Build an emporium using a Permit Tile" +
                "\n(4) Build an emporium with the help of the King" +
                "\n\nQuick actions:" +
                "\n(5) Engage an Assistant" +
                "\n(6) Change Building Permit Tiles" +
                "\n(7) Send an Assistant to elect a Councillor" +
                "\n(8) Perform an additional Main Action" +
                "\n\nWhat do you want to do?");
        String newCommand = scanner.nextLine();
        parse(newCommand);
    }

    private void parse(String command) {
        switch (command) {
            case "1":
                elect();
                break;
            case "2":
                acquire();
                break;
            case "3":
                build();
                break;
            case "4":
                buildKing();
                break;
            case "5":
                engageAssistant();
                break;
            case "6":
                changeBPT();
                break;
            case "7":
                electAsQuickAction();
                break;
            case "8":
                additionalMainAction();
                break;
            default:
                System.out.println("Commando non valido");
                break;
        }
        System.out.println("\n--------------------------------\n");
        waitInput();
    }

    private void elect() {

        notifyObservers(new Elect());
    }

    private void acquire() {

        notifyObservers(new Acquire());
    }

    private void build() {

        notifyObservers(new Build());
    }

    private void buildKing() {

        notifyObservers(new BuildKing());
    }

    private void engageAssistant() {

        notifyObservers(new EngageAssistant());
    }

    private void changeBPT() {

        notifyObservers(new ChangeBPT());
    }

    private void electAsQuickAction() {

        notifyObservers(new ElectAsQuickAction());
    }

    private void additionalMainAction() {

        notifyObservers(new AdditionalMainAction());
    }

}