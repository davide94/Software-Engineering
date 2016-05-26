package it.polimi.ingsw.cg26.client.view;

import it.polimi.ingsw.cg26.common.commands.*;
import it.polimi.ingsw.cg26.common.dto.*;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.io.*;
import java.util.*;

/**
 *
 */
public class CLI implements Runnable {

    private ObjectOutputStream outputStream;

    private Scanner scanner;

    private PrintStream out;

    private GameBoardDTO model;

    private PlayerDTO me;

    public CLI(ObjectOutputStream outputStream, GameBoardDTO model, PlayerDTO me) {
        this.outputStream = outputStream;
        this.model = model;
        this.scanner = new Scanner(System.in);
        this.out = System.out;
        this.me = me;
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
            this.output("\n(-1)STACCAH" +
                        "\n(0) Print state" +

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
                case "-1":
                    quit();
                    quit = true;
                    break;
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
        out.println(msg);
    }

    private void print() {

        out.println("There are " + model.getPlayers().size() + " players:");
        for (PlayerDTO p: model.getPlayers()) {
            printPlayer(p);
        }
        out.print("The King's balcony has");
        for (CouncillorDTO c: model.getKingBalcony().getCouncillors())
            out.print(" " + c.getColor());
        out.println(" councillors");

        out.println("The board has " + model.getRegions().size() + " regions:");

        for (RegionDTO r: model.getRegions()) {
            out.println("\n" + r.getName() + ": ");
            out.print("The balcony has");
            for (CouncillorDTO c: r.getBalcony().getCouncillors())
                out.print(" " + c.getColor());
            out.println(" councillors");
            out.println("the Business Permit Tiles open are: ");
            for (BusinessPermissionTileDTO b: r.getDeck().getOpenCards()) {
                printBPT(b);
                out.println("");
            }
        }
    }

    private void printPlayer(PlayerDTO player) {
        out.println("\n" + player.getName());

        out.println("victoryPoints: " + player.getVictoryPoints());
        out.println("coins: " + player.getCoins());
        out.println("nobilityCell: " + player.getNobilityCell());
        out.println("assistantsNumber: " + player.getAssistantsNumber());

        out.println();
    }

    private void printBPT(BusinessPermissionTileDTO bpt) {
        int i = 0;
        for (String c: bpt.getCities()) {
            if (i != 0)
                out.print("/");
            out.print(c.toUpperCase().charAt(0));
            i++;
        }
        out.print(" ");
        i = 0;
        for (BonusDTO b: bpt.getBonuses()) {
            if (i != 0)
                out.print(", ");
            out.print(b.getMultiplicity() + " " + b.getKind());
            i++;
        }
    }

    private void quit() throws IOException {
        this.outputStream.writeObject(new Staccah());
    }

    private void electAsMainAction() throws IOException {
        elect(true);
    }

    private void elect(boolean asMainAction) throws IOException {
        RegionDTO region = askForRegion();
        CouncillorDTO councillor = askForCouncillor();
        if (asMainAction)
            this.outputStream.writeObject(new ElectAsMainActionCommand(region, councillor));
        else
            this.outputStream.writeObject(new ElectAsQuickActionCommand(region, councillor));

    }

    private void acquire() throws IOException {
        RegionDTO region = askForRegion();
        List<PoliticCardDTO> cards = askForPoliticCards();
        this.output("Do you want the left(l) or the right(R) one? ");
        String response = this.scanner.nextLine();
        int position = 0;
        if ("l".equalsIgnoreCase(response) || "left".equalsIgnoreCase(response))
            position = 1;
        this.outputStream.writeObject(new AcquireCommand(region, cards, position));
    }

    private void build() throws IOException {
        CityDTO city = askForCity();
        BusinessPermissionTileDTO tile = askForBPT();
        this.outputStream.writeObject(new BuildCommand(city, tile));
    }

    private void buildKing() throws IOException {
        CityDTO city = askForCity();
        List<PoliticCardDTO> cards = askForPoliticCards();
        this.outputStream.writeObject(new BuildKingCommand(city, cards));
    }

    private void engageAssistant() throws IOException {
        this.outputStream.writeObject(new EngageAssistantCommand());
    }

    private void changeBPT() throws IOException {
        RegionDTO region = askForRegion();
        this.outputStream.writeObject(new ChangeBPTCommand(region));
    }

    private void electAsQuickAction() throws IOException {
        elect(false);
    }

    private void additionalMainAction() throws IOException {
        this.outputStream.writeObject(new AdditionalMainActionCommand());
    }

    private RegionDTO askForRegion() {
        List<RegionDTO> regions = model.getRegions();
        this.output("In which region? ");
        int i = 1;
        for (RegionDTO r: regions) {
            out.println("(" + i + ") " + r.getName());
            i++;
        }
        int regionNmber = this.scanner.nextInt();
        return regions.get(regionNmber - 1);
    }

    private CouncillorDTO askForCouncillor() {
        out.println("Which Councillor's color? ");
        Collection<CouncillorDTO> councillorsPool = model.getCouncillorsPool();
        List<PoliticColorDTO> colors = new LinkedList<>();
        int i = 1;
        for (CouncillorDTO c: councillorsPool) {
            if (!colors.contains(c.getColor())) {
                colors.add(c.getColor());
                out.println("(" + i + ") " + c.getColor().getColor());
                i++;
            }
        }
        int colorNumber = this.scanner.nextInt();
        for (CouncillorDTO c: councillorsPool)
            if (c.getColor().equals(colors.get(colorNumber - 1)))
                return c;
        return null;
    }

    private List<PoliticCardDTO> askForPoliticCards() {
        // TODO
        return null;
    }

    private BusinessPermissionTileDTO askForBPT() {
        // TODO
        return null;
    }

    private CityDTO askForCity() {
        this.output("In which city? ");
        List<CityDTO> cities = new LinkedList<>();
        for (RegionDTO r: model.getRegions())
            cities.addAll(r.getCities());
        int i = 1;
        for (CityDTO c: cities) {
            out.println("(" + i + ") " +c.getName());
            i++;
        }
        int cityNmber = this.scanner.nextInt();
        return cities.get(cityNmber - 1);
    }

}