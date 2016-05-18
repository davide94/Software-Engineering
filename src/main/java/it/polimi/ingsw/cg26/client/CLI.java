package it.polimi.ingsw.cg26.client;

import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;
import it.polimi.ingsw.cg26.server.view.View;

import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 */
/*public class CLI implements UserInterface {

    private Scanner scanner;

    private View view;


    public CLI(View view) {
        if (view == null)
            throw new NullPointerException();
        this.view = view;
        this.scanner = new Scanner(System.in);
        this.output("Welcome " + this.view.getName());
    }

    @Override
    public void start() {
        this.waitInput();
    }

    private void output(String msg) {
        System.out.printf(msg);
    }

    private String readLine() {
        return scanner.nextLine();
    }

    private void waitInput()  {
        this.output("\nMain actions:" +
                "\n(1) Elect a Councillor" +
                "\n(2) Acquire a Business Permit Tile" +
                "\n(3) Build an emporium using a Permit Tile" +
                "\n(4) Build an emporium with the help of the King" +
                "\n\nQuick actions:" +
                "\n(5) Engage an Assistant" +
                "\n(6) Change Building Permit Tiles" +
                "\n(7) Send an Assistant to elect a Councillor" +
                "\n(8) Perform an additional Main Action" +
                "\n\nWhat do you want to do? ");
        parse(this.readLine());
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
                parse(this.readLine());
                break;
        }
        waitInput();
    }

    private void elect() {
        this.output("In which region? ");
        String region = this.readLine();
        this.output("Assistant color? ");
        String colorString = this.readLine();
        PoliticColor politicColor = new PoliticColor(colorString);
        this.view.electAsMainAction(region, politicColor);
    }

    private void acquire() {
        this.output("In which region? ");
        String region = this.readLine();
        LinkedList<PoliticColor> cardsColors = this.askForCards();
        this.output("Do you want the left(l) or the right(R) one? ");
        String response = this.readLine();
        int position = 0;
        if ("l".equalsIgnoreCase(response) || "left".equalsIgnoreCase(response))
            position = 1;
        this.view.acquire(region, cardsColors, position);
    }

    private void build() {
        this.output("In which city? ");
        String city = this.readLine();
        this.view.build(city);
    }

    private void buildKing() {
        this.output("In which city? ");
        String city = this.readLine();
        LinkedList<PoliticColor> cards = this.askForCards();
        this.view.buildKing(city, cards);
    }

    private void engageAssistant() {
        this.view.engageAssistant();
    }

    private void changeBPT() {
        this.output("In which region? ");
        String region = this.readLine();
        this.view.changeBPT(region);
    }

    private void electAsQuickAction() {
        this.output("In which region? ");
        String region = this.readLine();
        this.output("Assistant color? ");
        String colorString = this.readLine();
        PoliticColor politicColor = new PoliticColor(colorString);
        this.view.electAsQuickAction(region, politicColor);
    }

    private void additionalMainAction() {
        this.view.additionalMainAction();
    }

    private LinkedList<PoliticColor> askForCards() {
        LinkedList<PoliticColor> cardsColors = new LinkedList<>();
        for (int i = 1; i <= 4; i++) {
            if (i == 1)
                this.output(i + "° Card color? ");
            else
                this.output(i + "° Card color? (press RETURN to end)");
            String colorName = this.readLine();
            if (i > 1 && colorName.isEmpty())
                break;
            cardsColors.add(new PoliticColor(colorName));
        }
        return cardsColors;
    }

}
*/