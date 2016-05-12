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
import it.polimi.ingsw.cg26.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.observer.Observable;
import it.polimi.ingsw.cg26.observer.Observer;
import it.polimi.ingsw.cg26.update.Update;

import java.util.LinkedList;
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
        System.out.println("In which region? ");
        String region = scanner.nextLine();
        System.out.println("Assistant color? ");
        String assistantColor = scanner.nextLine();
        notifyObservers(new Elect(region, assistantColor));
    }

    private void acquire() {
        System.out.println("In which region? ");
        String region = scanner.nextLine();
        LinkedList<PoliticColor> cardsColors = new LinkedList<>();
        for (int i = 1; i <= 4; i++) {
            System.out.println(i + "° Card color? ");
            String colorName = scanner.nextLine();
            cardsColors.add(new PoliticColor(colorName));
            if (i == 4)
                break;
            System.out.println("Do you have any more? (y, N) ");
            String response = scanner.nextLine();
            if (!response.equalsIgnoreCase("y") && !response.equalsIgnoreCase("yes"))
                break;
        }
        System.out.println("Do you want the left(l) or the right(R) one? ");
        String response = scanner.nextLine();
        int position = 0;
        if (!response.equalsIgnoreCase("l") && !response.equalsIgnoreCase("left"))
            position = 1;
        notifyObservers(new Acquire(region, cardsColors, position));
    }

    private void build() {
        System.out.println("In which city? ");
        String city = scanner.nextLine();
        notifyObservers(new Build(city));
    }

    private void buildKing() {
        System.out.println("In which city? ");
        String city = scanner.nextLine();
        LinkedList<String> cards = new LinkedList<>();
        for (int i = 1; i <= 4; i++) {
            System.out.println(i + "° Card color? ");
            cards.add(scanner.nextLine());
            if (i == 4)
                break;
            System.out.println("Do you have any more? (y, N) ");
            String response = scanner.nextLine();
            if (!response.equalsIgnoreCase("y") && !response.equalsIgnoreCase("yes"))
                break;
        }
        notifyObservers(new BuildKing(city, cards));
    }

    private void engageAssistant() {
        notifyObservers(new EngageAssistant());
    }

    private void changeBPT() {
        System.out.println("In which region? ");
        String region = scanner.nextLine();
        notifyObservers(new ChangeBPT(region));
    }

    private void electAsQuickAction() {
        System.out.println("In which region? ");
        String region = scanner.nextLine();
        System.out.println("Assistant color? ");
        String assistantColor = scanner.nextLine();
        notifyObservers(new ElectAsQuickAction(region, assistantColor));
    }

    private void additionalMainAction() {
        notifyObservers(new AdditionalMainAction());
    }

}