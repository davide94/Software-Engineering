package it.polimi.ingsw.cg26.client.view;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.common.change.Change;
import it.polimi.ingsw.cg26.common.commands.*;
import it.polimi.ingsw.cg26.common.dto.*;
import it.polimi.ingsw.cg26.common.observer.Observer;

import java.io.*;
import java.util.*;

/**
 *
 */
public class CLI implements Observer<Change>, Runnable {

    private ObjectOutputStream outputStream;

    private Scanner scanner;

    private PrintStream out;

    private Model model;

    public CLI(ObjectOutputStream outputStream, Model model) {
        this.outputStream = outputStream;
        this.model = model;
        this.scanner = new Scanner(System.in);
        this.out = System.out;
    }

    @Override
    public void run() {

    }

    private void waitInput() throws IOException {
        if (!model.isYourTurn()) {
            System.out.println("Wait your turn...");
            return;
        }
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
        printPlayer(model.getGameBoard().getLocalPlayer());
        out.print("Politic Cards:                 ");
        for (PoliticCardDTO card: model.getGameBoard().getLocalPlayer().getCards())
            out.print(card.getColor().getColoredColor() + " ");
        out.println();
        out.print("Business Permit Tiles:         ");
        for (BusinessPermissionTileDTO tile: model.getGameBoard().getLocalPlayer().getTiles())
            printBPT(tile);

        out.println();
        out.println("Other players:");
        for (PlayerDTO p: model.getGameBoard().getPlayers())
            if (p.getName() != model.getGameBoard().getLocalPlayer().getName())
                printPlayer(p);

        out.println();
        out.print("The King is in " + model.getGameBoard().getKing().getCurrentCity() + " and his balcony has");
        for (CouncillorDTO c: model.getGameBoard().getKingBalcony().getCouncillors())
            out.print(" " + c.getColor().getColoredColor());
        out.println(" councillors");

        out.println("The board has " + model.getGameBoard().getRegions().size() + " regions:");

        for (RegionDTO r: model.getGameBoard().getRegions()) {
            out.println("\n" + r.getName() + ": ");
            out.print("The balcony has");
            for (CouncillorDTO c: r.getBalcony().getCouncillors())
                out.print(" " + c.getColor().getColoredColor());
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
        out.println("Remaining Main Actions:        " + player.getRemainingMainActions());
        out.println("Remaining Quick Actions:       " + player.getRemainingQuickActions());

    }

    private void printBPT(BusinessPermissionTileDTO bpt) {
        int i = 0;
        for (String c: bpt.getCities()) {
            if (i != 0)
                out.print("/");
            out.print(c.toUpperCase().charAt(0));
            i++;
        }
        if (bpt.getCities().size() < 3)
            out.print("\t");
        out.print("\t");
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
        List<RegionDTO> regions = model.getGameBoard().getRegions();
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
        Collection<CouncillorDTO> councillorsPool = model.getGameBoard().getCouncillorsPool();
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
        LinkedList<PoliticCardDTO> allCards = new LinkedList<>(model.getGameBoard().getLocalPlayer().getCards());
        LinkedList<PoliticCardDTO> cards = new LinkedList<>();
        while (true) {
            int i = 1;
            for (PoliticCardDTO card: allCards) {
                out.println("(" + i + ") " + card.getColor().getColoredColor());
                i++;
            }
            int cardNumber = this.scanner.nextInt();
            this.scanner.nextLine();

            cards.add(allCards.remove(cardNumber - 1));
            if (cards.size() == 4)
                break;
            out.println("more? (Y/n)");
            String response = this.scanner.nextLine();
            if (!response.isEmpty() && response.substring(0,1).equalsIgnoreCase("n"))
                break;
        }
        return cards;
    }

    private BusinessPermissionTileDTO askForBPT() {
        LinkedList<BusinessPermissionTileDTO> tiles = new LinkedList<>(model.getGameBoard().getLocalPlayer().getTiles());
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
        for (RegionDTO r: model.getGameBoard().getRegions())
            cities.addAll(r.getCities());
        int i = 1;
        for (CityDTO c: cities) {
            out.println("(" + i + ") " +c.getName());
            i++;
        }
        int cityNmber = this.scanner.nextInt();
        return cities.get(cityNmber - 1);
    }

    @Override
    public void update(Change o) {
        //System.out.println(o);
        if (model.isYourTurn()) {
            System.out.println("Your turn begun...");
            try {
                waitInput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}