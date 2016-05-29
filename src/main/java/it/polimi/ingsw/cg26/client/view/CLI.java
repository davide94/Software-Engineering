package it.polimi.ingsw.cg26.client.view;

import it.polimi.ingsw.cg26.client.view.socket.ClientOutHandler;
import it.polimi.ingsw.cg26.common.commands.*;
import it.polimi.ingsw.cg26.common.dto.*;
import it.polimi.ingsw.cg26.common.observer.Observer;

import java.io.*;
import java.util.*;

/**
 *
 */
public class CLI implements Observer<GameBoardDTO>, Runnable {

    private ClientOutHandler outView;

    private Scanner scanner;

    private PrintStream out;

    private GameBoardDTO gameBoard;

    private boolean isMyTurn;

    public CLI(ClientOutHandler outView) {
        this.outView = outView;
        this.scanner = new Scanner(System.in);
        this.out = System.out;
    }

    @Override
    public void run() {
        askForCommand();
    }

    private void askForCommand() {
        if (isMyTurn) {
            out.println("Is Your turn!" +
                    "\nYou have " + gameBoard.getCurrentPlayer().getRemainingMainActions() +
                    " Main and " + gameBoard.getCurrentPlayer().getRemainingQuickActions() + " Quick actions to perform." +
                    "\n\nCommands:" +
                    "\n(Q) STACCAH" +
                    "\n(p) Print state" +

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
                    "\n(9) Fold Quick Action" +
                    "\n\nWhat do you want to do? ");

            String command = this.scanner.nextLine();
            switch (command) {
                case "Q":
                    quit();
                    break;
                case "p":
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
                case "9":
                    foldQuickAction();
                    break;
                default:
                    break;
            }
        } else {
            out.println("Is not Your turn." +
                    "\n\nCommands:" +
                    "\n(Q) STACCAH" +
                    "\n(p) Print state");

            String command = this.scanner.nextLine();
            switch (command) {
                case "-1":
                    quit();
                    break;
                case "0":
                    print();
                    break;
                default:
                    break;
            }
        }
        askForCommand();
    }

    private void print() {
        // TODO Check if local player is not null

        //print players
        out.println();
        out.println("You:");
        out.println();
        printPlayer(gameBoard.getLocalPlayer(), true);
        out.println("Other players:");
        for (PlayerDTO p: gameBoard.getPlayers())
            if (p.getName() != gameBoard.getLocalPlayer().getName()) {
                out.println();
                printPlayer(p, false);
            }

        // print councillors pool
        out.print("Councillors pool: [");
        for (CouncillorDTO c: gameBoard.getCouncillorsPool())
            out.print("\t" + c.getColor().getColoredColor());
        out.println(" ]");
        out.println();

        //print king an king's balcony
        out.print("The King is in " + gameBoard.getKing().getCurrentCity() + " and his balcony has: [");
        for (CouncillorDTO c: gameBoard.getKingBalcony().getCouncillors())
            out.print("\t" + c.getColor().getColoredColor());
        out.println(" ]<- councillors");

        // print regions
        out.println("The board has " + gameBoard.getRegions().size() + " regions:");
        for (RegionDTO r: gameBoard.getRegions()) {
            out.println("\n" + r.getName() + ": ");
            out.print("The balcony has: [");
            for (CouncillorDTO c: r.getBalcony().getCouncillors())
                out.print(" " + c.getColor().getColoredColor());
            out.println(" ]<- councillors");
            out.println("the Business Permit Tiles open are: ");
            for (BusinessPermissionTileDTO b: r.getDeck().getOpenCards()) {
                printBPT(b);
                out.println();
            }
        }
        out.println("\n");
        askForCommand();
    }

    private void printPlayer(PlayerDTO player, boolean full) {
        out.println(player.getName());
        out.println("Victory Points number:         " + player.getVictoryPoints());
        out.println("Coins number:                  " + player.getCoins());
        out.println("Position in Nobility Track:    " + player.getNobilityCell());
        out.println("Assistants number:             " + player.getAssistantsNumber());
        out.println("Remaining Main Actions:        " + player.getRemainingMainActions());
        out.println("Remaining Quick Actions:       " + player.getRemainingQuickActions());
        if (full) {
            out.print("Politic Cards:                 ");
            for (PoliticCardDTO card: gameBoard.getLocalPlayer().getCards())
                out.print(card.getColor().getColoredColor() + " ");
            out.print("\nBusiness Permit Tiles:         ");
            for (BusinessPermissionTileDTO tile: gameBoard.getLocalPlayer().getTiles())
                printBPT(tile);
        }
        out.println("\n");
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
        for (BonusDTO b: bpt.getReward().getBonuses()) {
            if (i != 0)
                out.print(", ");
            out.print(b.getMultiplicity() + " " + b.getKind());
            i++;
        }
    }

    private void quit() {
        outView.writeObject(new Staccah());
    }

    private void electAsMainAction() {
        elect(true);
    }

    private void elect(boolean asMainAction) {
        RegionDTO region = askForRegion();
        CouncillorDTO councillor = askForCouncillor();
        if (asMainAction)
            outView.writeObject(new ElectAsMainActionCommand(region, councillor));
        else
            outView.writeObject(new ElectAsQuickActionCommand(region, councillor));

    }

    private void acquire() {
        RegionDTO region = askForRegion();
        List<PoliticCardDTO> cards = askForPoliticCards();
        out.println("Do you want the left(l) or the right(R) one? ");
        String response = this.scanner.nextLine();
        int position = 0;
        if ("l".equalsIgnoreCase(response) || "left".equalsIgnoreCase(response))
            position = 1;
        outView.writeObject(new AcquireCommand(region, cards, position));
    }

    private void build() {
        CityDTO city = askForCity();
        BusinessPermissionTileDTO tile = askForBPT();
        outView.writeObject(new BuildCommand(city, tile));
    }

    private void buildKing() {
        CityDTO city = askForCity();
        List<PoliticCardDTO> cards = askForPoliticCards();
        outView.writeObject(new BuildKingCommand(city, cards));
    }

    private void engageAssistant() {
        outView.writeObject(new EngageAssistantCommand());
    }

    private void changeBPT() {
        RegionDTO region = askForRegion();
        outView.writeObject(new ChangeBPTCommand(region));
    }

    private void electAsQuickAction() {
        elect(false);
    }

    private void additionalMainAction() {
        outView.writeObject(new AdditionalMainActionCommand());
    }

    private void foldQuickAction() {
        outView.writeObject(new FoldQuickActionCommand());
    }

    private RegionDTO askForRegion() {
        List<RegionDTO> regions = gameBoard.getRegions();
        out.println("In which region? ");
        int i = 1;
        for (RegionDTO r: regions) {
            out.print("(" + i + ") " + r.getName() + "\t\t[");
            for (CouncillorDTO c: r.getBalcony().getCouncillors())
                out.print(" " + c.getColor().getColoredColor());
            out.println(" ]");
            i++;
        }
        int regionNumber;
        while (true) {
            try {
                regionNumber = Integer.parseInt(this.scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                out.println("The number is invalid, try again: ");
            }

        }
        return regions.get(regionNumber - 1);
    }

    private CouncillorDTO askForCouncillor() {
        out.println("Which Councillor's color? ");
        Collection<CouncillorDTO> councillorsPool = gameBoard.getCouncillorsPool();
        List<PoliticColorDTO> colors = new LinkedList<>();
        int i = 1;
        for (CouncillorDTO c: councillorsPool) {
            if (!colors.contains(c.getColor())) {
                colors.add(c.getColor());
                out.println("(" + i + ") " + c.getColor().getColoredColor());
                i++;
            }
        }
        int colorNumber;
        while (true) {
            try {
                colorNumber = Integer.parseInt(this.scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                out.println("This is not a number, try again: ");
            }

        }
        for (CouncillorDTO c: councillorsPool)
            if (c.getColor().equals(colors.get(colorNumber - 1)))
                return c;
        return null;
    }

    private List<PoliticCardDTO> askForPoliticCards() {
        LinkedList<PoliticCardDTO> allCards = new LinkedList<>(gameBoard.getLocalPlayer().getCards());
        LinkedList<PoliticCardDTO> cards = new LinkedList<>();
        int n = 0;
        outerLoop:
        while (true) {
            out.print("Which card do you want to use?");
            if (n > 0)
                out.print(" (press return to end)");
            out.println();
            int i = 1;
            for (PoliticCardDTO card: allCards) {
                out.println("(" + i + ") " + card.getColor().getColoredColor());
                i++;
            }
            int cardNumber;
            while (true) {
                try {
                    String line = this.scanner.nextLine();
                    if (line.isEmpty())
                        break outerLoop;
                    cardNumber = Integer.parseInt(line);
                    break;
                } catch (NumberFormatException e) {
                    out.println("The number is invalid, try again: ");
                }

            }
            cards.add(allCards.remove(cardNumber - 1));
            if (cards.size() == 4)
                break;
            n++;
        }
        return cards;
    }

    private BusinessPermissionTileDTO askForBPT() {
        LinkedList<BusinessPermissionTileDTO> tiles = new LinkedList<>(gameBoard.getLocalPlayer().getTiles());
        out.println("Which Permit Tile do you want to use?");
        int i = 1;
        for (BusinessPermissionTileDTO tile: tiles) {
            out.print("(" + i + ") ");
            printBPT(tile);
            i++;
        }
        int tileNumber;
        while (true) {
            try {
                tileNumber = Integer.parseInt(this.scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                out.println("The number is invalid, try again: ");
            }

        }
        return tiles.get(tileNumber - 1);
    }

    private CityDTO askForCity() {
        out.println("In which city? ");
        List<CityDTO> cities = new LinkedList<>();
        for (RegionDTO r: gameBoard.getRegions())
            cities.addAll(r.getCities());
        int i = 1;
        for (CityDTO c: cities) {
            out.println("(" + i + ") " +c.getName());
            i++;
        }
        int cityNmber;
        while (true) {
            try {
                cityNmber = Integer.parseInt(this.scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                out.println("The number is invalid, try again: ");
            }

        }
        return cities.get(cityNmber - 1);
    }

    @Override
    public void update(GameBoardDTO o) {
        //System.out.println(o);
        this.gameBoard = o;
        isMyTurn = gameBoard.getCurrentPlayer().getName().equals(gameBoard.getLocalPlayer().getName());
    }
}