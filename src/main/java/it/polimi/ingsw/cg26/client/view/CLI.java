package it.polimi.ingsw.cg26.client.view;

import it.polimi.ingsw.cg26.client.view.socket.ClientOutHandler;
import it.polimi.ingsw.cg26.common.commands.*;
import it.polimi.ingsw.cg26.common.dto.*;
import it.polimi.ingsw.cg26.common.observer.Observer;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;

/**
 *
 */
public class CLI implements Observer<GameBoardDTO>, Runnable {

    private ClientOutHandler outView;

    private Scanner scanner;

    private PrintWriter writer;

    private GameBoardDTO gameBoard;

    private boolean isMyTurn;

    private Consumer<BonusDTO> bonusPrinter = e -> writer.print(e.getMultiplicity() + " " + e.getKind());

    private Consumer<CouncillorDTO> councillorPrinter = e -> writer.print(e.getColor().toString());

    private Consumer<PoliticCardDTO> politicCardPrinter = e -> writer.print(e.getColor().toString());

    private Consumer<BusinessPermissionTileDTO> bptPrinter = e -> {
        e.getCities().forEach(c -> writer.print(c.toUpperCase().charAt(0) + "/"));
        writer.print("\t\t");
        e.getReward().getBonuses().forEach(b -> {
            bonusPrinter.accept(b);
            writer.print(", ");
        });
    }; // TODO fix

    private Consumer<PlayerDTO> playerPrinter = p -> {
        writer.print("\t" + p.getName() +
                "\n\t\tVictory Points number:         " + p.getVictoryPoints() +
                "\n\t\tCoins number:                  " + p.getCoins() +
                "\n\t\tPosition in Nobility Track:    " + p.getNobilityCell() +
                "\n\t\tAssistants number:             " + p.getAssistantsNumber() +
                "\n\t\tRemaining Main Actions:        " + p.getRemainingMainActions() +
                "\n\t\tRemaining Quick Actions:       " + p.getRemainingQuickActions());
        if (!p.getCards().isEmpty())
            writer.print("\n\t\tPolitic Cards:                 ");
        p.getCards().forEach(c -> {
            politicCardPrinter.accept(c);
            writer.print(" ");
        });
        if (!gameBoard.getLocalPlayer().getTiles().isEmpty())
            writer.print("\n\n\t\tBusiness Permit Tiles:         ");
        p.getTiles().forEach(bptPrinter::accept);
        writer.println("\n");
    };

    private Consumer<CityDTO> cityPrinter = c -> {
        writer.print(c.getName());
        if (gameBoard.getKing().getCurrentCity().equalsIgnoreCase(c.getName()))
            writer.print(" (KING) ");
        if (!c.getEmporiums().isEmpty())
            writer.print("[ ");
        c.getEmporiums().forEach(e -> writer.print(e.getPlayer() + " "));
        if (!c.getEmporiums().isEmpty())
            writer.print("]");
    };

    private Consumer<RegionDTO> regionPrinter = e -> writer.print(e.getName());

    private Consumer<RegionDTO> extendedRegionPrinter = r -> {
        writer.println("\t" + r.getName() + ": ");
        writer.print("\t\tThe balcony has: [ ");
        r.getBalcony().getCouncillors().forEach(c -> {
            councillorPrinter.accept(c);
            writer.print(" ");
        });
        writer.println("] <- councillors");
        writer.println("\n\t\tThe open Business Permit Tiles are: ");
        r.getDeck().getOpenCards().forEach(t ->  {
            writer.print("\t\t\t");
            bptPrinter.accept(t);
            writer.println();
        });
        writer.println();
        writer.println("\t\tCities: ");
        r.getCities().forEach(c -> {
            writer.print("\t\t\t");
            cityPrinter.accept(c);
            writer.println();
        });
        writer.println();

        writer.flush();
    };

    public CLI(Scanner scanner, PrintWriter writer, ClientOutHandler outView) {
        this.outView = outView;
        this.scanner = scanner;
        this.writer = writer;
    }

    @Override
    public void run() {
        askForCommand();
    }

    private void askForCommand() {
        if (isMyTurn) {
            writer.println("Is Your turn!" +
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
            writer.flush();

            String command = this.scanner.nextLine();
            switch (command) {
                case "Q":
                    quit();
                    break;
                case "p":
                    printFullState();
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
            writer.println("Is not Your turn." +
                    "\n\nCommands:" +
                    "\n(Q) STACCAH" +
                    "\n(p) Print state");
            writer.flush();

            String command = this.scanner.nextLine();
            switch (command) {
                case "Q":
                    quit();
                    break;
                case "p":
                    printFullState();
                    break;
                default:
                    break;
            }
        }
        askForCommand();
    }

    private void printFullState() {

        // print councillors pool
        writer.print("Councillors pool: [ ");
        gameBoard.getCouncillorsPool().forEach(c -> {
            councillorPrinter.accept(c);
            writer.print(" ");
        });
        writer.println("]");

        //print king an king's balcony
        writer.print("The King is in " + gameBoard.getKing().getCurrentCity() + " and his balcony has: [ ");
        gameBoard.getKingBalcony().getCouncillors().forEach(c -> {
            councillorPrinter.accept(c);
            writer.print(" ");
        });
        writer.println("]<- councillors\n");

        // print regions
        writer.println("The board has " + gameBoard.getRegions().size() + " regions:");
        gameBoard.getRegions().forEach(extendedRegionPrinter::accept);

        //print players
        writer.println("You:");
        playerPrinter.accept(gameBoard.getLocalPlayer());
        writer.println("Other players:");
        gameBoard.getPlayers().stream()
                .filter(playerDTO -> playerDTO.getName() != gameBoard.getLocalPlayer().getName())
                .forEach(playerPrinter::accept);

        writer.flush();
        askForCommand();
    }

    private void quit() {
        outView.writeObject(new Staccah());
    }

    private void electAsMainAction() {
        elect(true);
    }

    private void elect(boolean asMainAction) {
        RegionDTO region = askForElement(gameBoard.getRegions(), "In which region?", regionPrinter);
        CouncillorDTO councillor = askForElement(new LinkedList<>(gameBoard.getCouncillorsPool()), "Which Councillor?", councillorPrinter);
        if (asMainAction)
            outView.writeObject(new ElectAsMainActionCommand(region, councillor));
        else
            outView.writeObject(new ElectAsQuickActionCommand(region, councillor));
    }

    private void acquire() {
        RegionDTO region = askForElement(gameBoard.getRegions(), "In which region?", regionPrinter);
        List<PoliticCardDTO> cards = askForList(new LinkedList<>(gameBoard.getLocalPlayer().getCards()), 4, "Which card do you want to use?", politicCardPrinter);
        writer.println("Do you want the left(l) or the right(R) one? ");
        String response = this.scanner.nextLine();
        int position = 0;
        if ("l".equalsIgnoreCase(response) || "left".equalsIgnoreCase(response))
            position = 1;
        outView.writeObject(new AcquireCommand(region, cards, position));
    }

    private void build() {
        List<CityDTO> cities = new LinkedList<>();
        gameBoard.getRegions().forEach(r -> cities.addAll(r.getCities()));
        CityDTO city = askForElement(cities, "In which city?", cityPrinter);
        BusinessPermissionTileDTO tile = askForElement(new LinkedList<>(gameBoard.getLocalPlayer().getTiles()), "Which Permit Tile do you want to use?", bptPrinter);
        outView.writeObject(new BuildCommand(city, tile));
    }

    private void buildKing() {
        List<CityDTO> cities = new LinkedList<>();
        gameBoard.getRegions().forEach(r -> cities.addAll(r.getCities()));
        CityDTO city = askForElement(cities, "In which city?", cityPrinter);
        List<PoliticCardDTO> cards = askForList(new LinkedList<>(gameBoard.getLocalPlayer().getCards()), 4, "Which card do you want to use?", politicCardPrinter);
        outView.writeObject(new BuildKingCommand(city, cards));
    }

    private void engageAssistant() {
        outView.writeObject(new EngageAssistantCommand());
    }

    private void changeBPT() {
        RegionDTO region = askForElement(gameBoard.getRegions(), "In which region?", regionPrinter);
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

    private <T> T askForElement(List<T> list, String title, Consumer<T> printer) {
        writer.println(title);
        list.forEach(e -> {
            writer.print("(" + (list.indexOf(e) + 1) + ") ");
            printer.accept(e);
            writer.println();
        });
        writer.flush();
        return list.get(readInt() - 1);
    }

    private <T> List<T> askForList(List<T> list, int max, String title, Consumer<T> printer) {
        LinkedList<T> ret = new LinkedList<>();
        LinkedList<T> all = new LinkedList<>(list);
        int n = 0;
        while (true) {
            if (n > 0)
                title += " (press return to end)";
            T t = askForElement(all, title, printer);
            all.remove(t);
            ret.add(t);
            if (ret.size() == max)
                break;
            n++;
        }
        return ret;
    }

    private int readInt() {
        int ret;
        while (true) {
            try {
                ret = Integer.parseInt(this.scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                writer.println("The number is invalid, try again: ");
                writer.flush();
            }
        }
        return ret;
    }

    @Override
    public void update(GameBoardDTO o) {
        //writer.println(o);
        this.gameBoard = o;
        isMyTurn = gameBoard.getCurrentPlayer().getName().equals(gameBoard.getLocalPlayer().getName());
    }
}