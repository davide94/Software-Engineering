package it.polimi.ingsw.cg26.client;

import it.polimi.ingsw.cg26.client.socket.ClientSocket;
import it.polimi.ingsw.cg26.common.commands.Staccah;
import it.polimi.ingsw.cg26.common.state.BoardState;
import it.polimi.ingsw.cg26.common.state.BusinessPermissionTileState;
import it.polimi.ingsw.cg26.common.state.CouncillorState;
import it.polimi.ingsw.cg26.common.state.RegionState;
import it.polimi.ingsw.cg26.server.model.board.Region;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class CLI implements Runnable {

    private ObjectOutputStream outputStream;

    private Scanner scanner;

    private ClientSocket client;

    public CLI(ObjectOutputStream outputStream, ClientSocket client) {
        this.outputStream = outputStream;
        this.client = client;
        this.scanner = new Scanner(System.in);
        this.output("Welcome");
    }

    @Override
    public void run() {
        try {
            waitInput();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void waitInput() throws IOException {
        boolean quit = false;
        while (!quit) {
            this.output("\n(0) Print state" +

                        "\n\nMain commands:" +
                        "\n(1) Elect a Councillor" +
                        "\n(2) Acquire a Business Permit Tile" +
                        "\n(3) Build an emporium using a Permit Tile" +
                        "\n(4) Build an emporium with the help of the King" +

                        "\n\nQuick commands:" +
                        "\n(5) Engage an Assistant" +
                        "\n(6) Change Building Permit Tiles" +
                        "\n(7) Send an Assistant to elect a Councillor" +
                        "\n(8) Perform an additional Main Action" +
                        "\n\nWhat do you want to do? ");

            String command = this.scanner.nextLine();

            switch (command) {
                case "0":
                    print();
                    break;
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
                    break;
            }
        }
    }

    private void output(String msg) {
        System.out.println(msg);
    }

    private void print() {
        BoardState model = client.getState();

        System.out.print("The King's balcony has");
        for (CouncillorState c: model.getKingBalcony().getCouncillors())
            System.out.print(" " + c.getColor().getColor());
        System.out.println(" councillors");

        System.out.println("The board has " + model.getRegions().size() + " regions:");

        for (RegionState r: model.getRegions()) {
            System.out.println("\n" + r.getName() + ": ");
            System.out.print("The balcony has");
            for (CouncillorState c: model.getKingBalcony().getCouncillors())
                System.out.print(" " + c.getColor().getColor());
            System.out.println(" councillors");
            System.out.print("the Business Permit Tiles open are: ");
            for (BusinessPermissionTileState b: r.getDeck().getOpenCards())
                System.out.print(b + " ");
            System.out.println("");
            System.out.println("-----------");
        }

    }

    private void quit() throws IOException {
        this.outputStream.writeObject(new Staccah());
    }

    private void elect() throws IOException {
        List<RegionState> regions = client.getState().getRegions();
        this.output("In which region? ");
        int i = 1;
        for (RegionState r: regions) {
            System.out.println("(" + i + ") " + r.getName());
            i++;
        }
        int regionNmber = this.scanner.nextInt();
        RegionState region = null;
        i = 1;
        for (RegionState r: regions) {
            if (i == regionNmber)
                region = r;
            i++;
        }
        // TODO sistemare sta merdata... per qualche motivo .get(i-1) non funziona
        System.out.println("Assistant color? ");
        String colorString = this.scanner.nextLine();
        PoliticColor politicColor = new PoliticColor(colorString);
        //this.outputStream.writeObject(new ElectAsMainAction(region, politicColor));
    }

    private void acquire() throws IOException {
        this.output("In which region? ");
        String region = this.scanner.nextLine();
        LinkedList<PoliticColor> cardsColors = this.askForCards();
        this.output("Do you want the left(l) or the right(R) one? ");
        String response = this.scanner.nextLine();
        int position = 0;
        if ("l".equalsIgnoreCase(response) || "left".equalsIgnoreCase(response))
            position = 1;
        //this.outputStream.writeObject(new Acquire(region, cardsColors, position));
    }

    private void build() throws IOException {
        this.output("In which city? ");
        String city = this.scanner.nextLine();
        //this.outputStream.writeObject(new Build(city));
    }

    private void buildKing() throws IOException {
        this.output("In which city? ");
        String city = this.scanner.nextLine();
        LinkedList<PoliticColor> cards = this.askForCards();
        //this.outputStream.writeObject(new BuildKing(city, cards));
    }

    private void engageAssistant() throws IOException {
        //this.outputStream.writeObject(new EngageAssistant());
    }

    private void changeBPT() throws IOException {
        this.output("In which region? ");
        String region = this.scanner.nextLine();
        //this.outputStream.writeObject(new ChangeBPT(region));
    }

    private void electAsQuickAction() throws IOException {
        this.output("In which region? ");
        String region = this.scanner.nextLine();
        this.output("Assistant color? ");
        String colorString = this.scanner.nextLine();
        PoliticColor politicColor = new PoliticColor(colorString);
        //this.outputStream.writeObject(new ElectAsQuickAction(region, politicColor));
    }

    private void additionalMainAction() throws IOException {
        //this.outputStream.writeObject(new AdditionalMainAction());
    }

    private LinkedList<PoliticColor> askForCards() {
        LinkedList<PoliticColor> cardsColors = new LinkedList<>();
        for (int i = 1; i <= 4; i++) {
            if (i == 1)
                this.output(i + "° Card color? ");
            else
                this.output(i + "° Card color? (press RETURN to end)");
            String colorName = this.scanner.nextLine();
            if (i > 1 && colorName.isEmpty())
                break;
            cardsColors.add(new PoliticColor(colorName));
        }
        return cardsColors;
    }

}