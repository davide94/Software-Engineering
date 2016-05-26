package it.polimi.ingsw.cg26.client.view;

import it.polimi.ingsw.cg26.common.commands.*;
import it.polimi.ingsw.cg26.common.dto.*;

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

    public CLI(ObjectOutputStream outputStream, GameBoardDTO model) {
        this.outputStream = outputStream;
        this.model = model;
        this.scanner = new Scanner(System.in);
        this.out = System.out;
    }

    @Override
    public void run() {
        try {
            waitInput();
        } catch (IOException e) {
            e.printStackTrace();
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
        // TODO Check if local player is not null
        out.println("You:");
        printPlayer(model.getlocalPlayer());
        out.print("Politic Cards:                 ");
        for (PoliticCardDTO card: model.getlocalPlayer().getCards())
            out.print(card.getColor().getColoredColor() + " ");
        out.println();
        out.print("Business Permit Tiles:         ");
        for (BusinessPermissionTileDTO tile: model.getlocalPlayer().getTiles())
            printBPT(tile);

        out.println();
        out.println("Other players:");
        for (PlayerDTO p: model.getPlayers())
            if (p.getName() != model.getlocalPlayer().getName())
                printPlayer(p);

        out.println();
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

        out.println("Victory Points number:         " + player.getVictoryPoints());
        out.println("Coins number:                  " + player.getCoins());
        out.println("Position in Nobility Track:    " + player.getNobilityCell());
        out.println("Assistants number:             " + player.getAssistantsNumber());

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
        out.println("Which card do you want to use?");
        LinkedList<PoliticCardDTO> allCards = new LinkedList<>(model.getlocalPlayer().getCards());
        LinkedList<PoliticCardDTO> cards = new LinkedList<>();
        while (true) {
            int i = 1;
            for (PoliticCardDTO card: allCards) {
                out.println("(" + i + ") " + card.getColor().getColoredColor());
                i++;
            }
            int cardNumber = this.scanner.nextInt();
            cards.add(allCards.get(cardNumber - 1));
            if (cards.size() == 4)
                break;
            out.println("more? (Y/n)");
            String response = this.scanner.nextLine();
            if (response.substring(0,1).equalsIgnoreCase("n"))
                break;
        }
        return cards;
    }

    private BusinessPermissionTileDTO askForBPT() {
        LinkedList<BusinessPermissionTileDTO> tiles = new LinkedList<>(model.getlocalPlayer().getTiles());
        out.println("Which Permit Tile do you want to use?");
        int i = 1;
        for (BusinessPermissionTileDTO tile: tiles) {
            out.print("(" + i + ") ");
            printBPT(tile);
            i++;
        }
        int tileNumber = this.scanner.nextInt();
        return tiles.get(tileNumber - 1);
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