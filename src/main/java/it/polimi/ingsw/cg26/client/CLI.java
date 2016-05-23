package it.polimi.ingsw.cg26.client;

import it.polimi.ingsw.cg26.client.socket.ClientSocket;
import it.polimi.ingsw.cg26.common.commands.*;
import it.polimi.ingsw.cg26.common.state.*;
import it.polimi.ingsw.cg26.server.model.board.Region;
import it.polimi.ingsw.cg26.server.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.server.model.cards.PoliticColor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

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
                    electAsMainAction();
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

    private void electAsMainAction() throws IOException {
        elect(true);
    }

    private void elect(boolean asMainAction) throws IOException {
        RegionState region = askForRegion();
        CouncillorState councillor = askForCouncillor();
        if (asMainAction)
            this.outputStream.writeObject(new ElectAsMainActionCommand(region, councillor));
        else
            this.outputStream.writeObject(new ElectAsQuickActionCommand(region, councillor));

    }

    private void acquire() throws IOException {
        RegionState region = askForRegion();
        List<PoliticCardState> cards = askForPoliticCards();
        this.output("Do you want the left(l) or the right(R) one? ");
        String response = this.scanner.nextLine();
        int position = 0;
        if ("l".equalsIgnoreCase(response) || "left".equalsIgnoreCase(response))
            position = 1;
        this.outputStream.writeObject(new AcquireCommand(region, cards, position));
    }

    private void build() throws IOException {
        CityState city = askForCity();
        this.outputStream.writeObject(new BuildCommand(city));
    }

    private void buildKing() throws IOException {
        CityState city = askForCity();
        List<PoliticCardState> cards = askForPoliticCards();
        this.outputStream.writeObject(new BuildKingCommand(city, cards));
    }

    private void engageAssistant() throws IOException {
        this.outputStream.writeObject(new EngageAssistantCommand());
    }

    private void changeBPT() throws IOException {
        RegionState region = askForRegion();
        this.outputStream.writeObject(new ChangeBPTCommand(region));
    }

    private void electAsQuickAction() throws IOException {
        elect(false);
    }

    private void additionalMainAction() throws IOException {
        this.outputStream.writeObject(new AdditionalMainActionCommand());
    }

    private RegionState askForRegion() {
        List<RegionState> regions = client.getState().getRegions();
        this.output("In which region? ");
        int i = 1;
        for (RegionState r: regions) {
            System.out.println("(" + i + ") " + r.getName());
            i++;
        }
        int regionNmber = this.scanner.nextInt();
        return regions.get(regionNmber - 1);
    }

    private CouncillorState askForCouncillor() {
        System.out.println("Which Councillor's color? ");
        List<CouncillorState> councillorsPool = client.getState().getCouncillorsPool();
        List<PoliticColorState> colors = new LinkedList<>();
        int i = 1;
        for (CouncillorState c: councillorsPool) {
            if (!colors.contains(c.getColor())) {
                colors.add(c.getColor());
                System.out.println("(" + i + ") " + c.getColor().getColor());
                i++;
            }
        }
        int colorNumber = this.scanner.nextInt();
        for (CouncillorState c: councillorsPool)
            if (c.getColor().equals(colors.get(colorNumber - 1)))
                return c;
        return null;
    }

    private List<PoliticCardState> askForPoliticCards() {
        // TODO
        return null;
    }

    private List<PoliticCardState> askForBPT() {
        // TODO
        return null;
    }

    private CityState askForCity() {
        this.output("In which city? ");
        List<CityState> cities = new LinkedList<>();
        for (RegionState r: client.getState().getRegions())
            cities.addAll(r.getCities());
        int i = 1;
        for (CityState c: cities) {
            System.out.println("(" + i + ") " +c.getName());
            i++;
        }
        int cityNmber = this.scanner.nextInt();
        return cities.get(cityNmber - 1);
    }

}