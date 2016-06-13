package it.polimi.ingsw.cg26.client.view.ui;

import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.client.view.OutView;
import it.polimi.ingsw.cg26.common.commands.*;
import it.polimi.ingsw.cg26.common.commands.market.*;
import it.polimi.ingsw.cg26.common.dto.*;
import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import it.polimi.ingsw.cg26.common.observer.Observer;
import it.polimi.ingsw.cg26.common.update.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.util.*;
import java.util.function.Consumer;

/**
 *
 */
public class CLI implements Observer<Update>, Runnable {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final OutView outView;

    private final Scanner scanner;

    private PrintWriter writer;

    private Model model;

    private List<String> commands;

    private Consumer<BonusDTO> bonusPrinter = e -> writer.print(e.toString());

    private Consumer<CouncillorDTO> councillorPrinter = e -> writer.print(e.getColor().toString());

    private Consumer<PoliticCardDTO> politicCardPrinter = e -> writer.print(e.getColor().toString());

    private Consumer<BusinessPermissionTileDTO> bptPrinter = e -> {
        e.getCities().forEach(c -> writer.print(c.toUpperCase().charAt(0) + "/"));
        writer.print("\t\t");
        bonusPrinter.accept(e.getBonuses());
    }; // TODO: fix

    private Consumer<PlayerDTO> playerPrinter = p -> {
        writer.print("\t" + p.getName() +
                "\n\t\tState:                         " + (p.isOnline() ? "\u001B[32monline\u001B[0m" : "\u001B[31moffline\u001B[0m") +
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
        if (!model.getLocalPlayer().getTiles().isEmpty())
            writer.print("\n\n\t\tBusiness Permit Tiles:         ");
        p.getTiles().forEach(bptPrinter::accept);
        writer.println("\n");
    };

    private Consumer<CityDTO> cityPrinter = c -> {
        writer.print(c.getName() + " [" + c.getColor().getColor() + "] " + c.getBonuses().toString());
        if (model.getKing().getCurrentCity().equalsIgnoreCase(c.getName()))
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

    private Consumer<SellableDTO> sellablePrinter = s -> {
        writer.println(s);
        writer.flush();
    };

    private Consumer<MarketDTO> marketPrinter = m -> {
        m.getOnSale().forEach(s -> sellablePrinter.accept(s));
        writer.flush();
    };

    public CLI(Scanner scanner, PrintWriter writer, OutView outView, Model model) {
        this.outView = outView;
        this.scanner = scanner;
        this.writer = writer;
        this.model = model;
        this.commands = new ArrayList<>(0);
    }

    @Override
    public void run() {
        writer.println("Press RETURN to list all commands.");
        writer.flush();
        waitInput();
    }

    private void waitInput() {
        while (true) {
            int input = readInt();
            if (input == -1)
                printCommands();
            else
                handleInput(input - 1);
        }
    }

    private void printCommands() {
        Map<String, String> permittedCommands = model.getState().commands();
        commands = new ArrayList<>(permittedCommands.size());
        writer.println("--------------------");
        int i = 1;
        for(Map.Entry<String, String> entry : permittedCommands.entrySet()) {
            writer.println("(" + i + ") " + entry.getKey());
            commands.add(entry.getValue());
            i++;
        }
        writer.println("--------------------");
        writer.flush();
    }

    private void handleInput(int i) {
        try {
            CLI.class.getDeclaredMethod(commands.get(i)).invoke(this);
        } catch (Exception e) {
            log.error("Error calling method.", e);
        }
    }

    private void printFullState() {
        writer.println("--------------------");
        // print councillors pool
        writer.print("Councillors pool: [ ");
        model.getCouncillorsPool().forEach(c -> {
            councillorPrinter.accept(c);
            writer.print(" ");
        });
        writer.println("]");

        //print king an king's balcony
        writer.print("The King is in " + model.getKing().getCurrentCity() + " and his balcony has: [ ");
        model.getKingBalcony().getCouncillors().forEach(c -> {
            councillorPrinter.accept(c);
            writer.print(" ");
        });
        writer.println("]<- councillors\n");

        // print regions
        writer.println("The board has " + model.getRegions().size() + " regions:");
        model.getRegions().forEach(extendedRegionPrinter::accept);

        //print players
        writer.println("You:");
        playerPrinter.accept(model.getLocalPlayer());
        writer.println("Other players:");
        model.getPlayers().stream()
                .filter(playerDTO -> !playerDTO.getName().equals(model.getLocalPlayer().getName()))
                .forEach(playerPrinter::accept);
        writer.println("--------------------");
        writer.flush();
    }

    private void printMarket() {
        writer.println("--------------------");
        marketPrinter.accept(model.getMarket());
        writer.println("--------------------");
        writer.flush();
    }

    private void quit() {
        writer.println("Bye.");
        writer.flush();
        outView.writeObject(new StaccahCommand());
        // TODO: Staccah Staccah Staccah
    }

    private void electAsMainAction() {
        elect(true);
    }

    private void elect(boolean asMainAction) {
        List<RegionDTO> regions = model.getRegions();

        writer.println("Where?");
        writer.println("(1) King's balcony");
        for (int i = 0; i < regions.size(); i++) {
            writer.print("(" + (i + 2) + ") ");
            regionPrinter.accept(regions.get(i));
            writer.println();
        }
        writer.flush();
        int res = readInt(1, regions.size());

        CouncillorDTO councillor = askForElement(new LinkedList<>(model.getCouncillorsPool()), "Which Councillor?", councillorPrinter);
        if(res == 1) {
            if (asMainAction)
                outView.writeObject(new ElectKingAsMainActionCommand(councillor));
            else
                outView.writeObject(new ElectKingAsQuickActionCommand(councillor));
        } else {
            if (asMainAction)
                outView.writeObject(new ElectAsMainActionCommand(regions.get(res - 2), councillor));
            else
                outView.writeObject(new ElectAsQuickActionCommand(regions.get(res - 2), councillor));
        }
    }

    private void acquire() {
        RegionDTO region = askForElement(model.getRegions(), "In which region?", regionPrinter);
        List<PoliticCardDTO> cards = askForList(new LinkedList<>(model.getLocalPlayer().getCards()), 4, "Which card do you want to use?", politicCardPrinter);
        writer.println("Do you want the left(l) or the right(R) one? ");
        writer.flush();
        String response = this.scanner.nextLine();
        int position = 0;
        if ("l".equalsIgnoreCase(response) || "left".equalsIgnoreCase(response))
            position = 1;
        outView.writeObject(new AcquireCommand(region, cards, position));
    }

    private void build() {
        List<CityDTO> cities = new LinkedList<>();
        model.getRegions().forEach(r -> cities.addAll(r.getCities()));
        CityDTO city = askForElement(cities, "In which city?", cityPrinter);
        BusinessPermissionTileDTO tile = askForElement(new LinkedList<>(model.getLocalPlayer().getTiles()), "Which Permit Tile do you want to use?", bptPrinter);
        outView.writeObject(new BuildCommand(city, tile));
    }

    private void buildKing() {
        List<CityDTO> cities = new LinkedList<>();
        model.getRegions().forEach(r -> cities.addAll(r.getCities()));
        CityDTO city = askForElement(cities, "In which city?", cityPrinter);
        List<PoliticCardDTO> cards = askForList(new LinkedList<>(model.getLocalPlayer().getCards()), 4, "Which card do you want to use?", politicCardPrinter);
        outView.writeObject(new BuildKingCommand(city, cards));
    }

    private void engageAssistant() {
        outView.writeObject(new EngageAssistantCommand());
    }

    private void changeBPT() {
        RegionDTO region = askForElement(model.getRegions(), "In which region?", regionPrinter);
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

    private void sell() {
        writer.println("Which element?");
        PlayerDTO player = model.getLocalPlayer();
        int i = 1;
        if (player.getAssistantsNumber() > 0) {
            writer.println("(" + i + ") an assistant");
            i++;
        }
        int j = i;
        List<PoliticCardDTO> cards = new LinkedList<>(player.getCards());
        for (PoliticCardDTO c: cards) {
            writer.print("(" + j + ") ");
            politicCardPrinter.accept(c);
            writer.println(" card");
            j++;
        }
        int k = j;
        List<BusinessPermissionTileDTO> tiles = new LinkedList<>(player.getTiles());
        tiles.addAll(player.getDiscardedTiles());
        for (BusinessPermissionTileDTO t: tiles) {
            writer.print("(" + k + ") ");
            bptPrinter.accept(t);
            k++;
        }
        writer.flush();
        int res = readInt(1, k - 1);
        writer.println("Which price?");
        writer.flush();
        int price = readInt();
        while (price < 1) {
            writer.println("Price not permited, try again...");
            writer.flush();
            price = readInt();
        }
        if (res < i) {
            outView.writeObject(new SellAssistantCommand(price));
        } else if (res < j) {
            outView.writeObject(new SellPoliticCardCommand(price, cards.get(res - i)));
        } else {
            outView.writeObject(new SellBPTCommand(price, tiles.get(res - j)));
        }
    }

    private void foldSell() {
        outView.writeObject(new FoldSellCommand());
    }

    private void buy() {
        SellableDTO sellable = askForElement(model.getMarket().getOnSale(), "What do you want to buy?", sellablePrinter);
        outView.writeObject(new BuyCommand(sellable));
    }

    private void foldBuy() {
        outView.writeObject(new FoldBuyCommand());
    }

    private <T> T askForElement(List<T> list, String title, Consumer<T> printer) {
        writer.println(title);
        for (int i = 0; i < list.size(); i++) {
            writer.print("(" + (i + 1) + ") ");
            printer.accept(list.get(i));
            writer.println();
        }
        writer.flush();
        int res = readInt(1, list.size());
        if (res == 0)
            return null;
        return list.get(res - 1);
    }

    private <T> List<T> askForList(List<T> list, int max, String title, Consumer<T> printer) {
        LinkedList<T> ret = new LinkedList<>();
        LinkedList<T> all = new LinkedList<>(list);
        int n = 0;
        while (true) {
            if (n == 1)
                title += " (press return to end)";
            T t = askForElement(all, title, printer);
            if (t == null)
                break;
            all.remove(t);
            ret.add(t);
            if (ret.size() == max || all.isEmpty())
                break;
            n++;
        }
        return ret;
    }

    private int readInt() {
        return readInt(0, Integer.MAX_VALUE);
    }

    private int readInt(Integer min, Integer max) {
        int ret;
        while (true) {
            try {
                String str = scanner.nextLine();
                if (str.isEmpty())
                    return min - 1;
                ret = Integer.parseInt(str);
                if (ret > max || ret < min)
                    throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {
                writer.println("The number is invalid, try again: ");
                writer.flush();
            }
        }
        return ret;
    }

    @Override
    public void update(Update u) {
        //writer.println(u);
    }
}