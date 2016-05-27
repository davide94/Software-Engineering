package it.polimi.ingsw.cg26.server.controller;

import it.polimi.ingsw.cg26.common.change.*;
import it.polimi.ingsw.cg26.common.dto.*;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.common.observer.Observer;

/**
 * 
 */
public class Controller implements Observer<Action>, Runnable {

    private final GameBoard gameBoard;

    public Controller(GameBoard gameBoard) {
        if (gameBoard == null)
            throw new NullPointerException();
        this.gameBoard = gameBoard;
    }

    @Override
    public synchronized void update(Action action) {
        try {
            if (gameBoard.getCurrentPlayer().getToken() == action.getToken()) {

                print(gameBoard.getState());

                action.apply(gameBoard);
                gameBoard.actionPerformed();

                System.out.println("---------- ACTION PERFORMED ----------");

                print(gameBoard.getState());

                gameBoard.notifyObservers(new FullStateChange(new BasicChange(), gameBoard.getState()));
                for (PlayerDTO player: gameBoard.getFullPlayers())
                    gameBoard.notifyObservers(new PrivateChange(new LocalPlayerChange(new BasicChange(), player), player.getToken()));

            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            // TODO notify the view that the action doesn't succeeded
        }
    }

    @Override
    public void run() {
        System.out.println("Partita cominciata");

        gameBoard.notifyObservers(new FullStateChange(new BasicChange(), gameBoard.getState()));
        for (PlayerDTO player: gameBoard.getFullPlayers())
            gameBoard.notifyObservers(new PrivateChange(new LocalPlayerChange(new BasicChange(), player), player.getToken()));

    }

    private void print(GameBoardDTO model) {
        // TODO Check if local player is not null

        //print players
        System.out.println();
        System.out.println("Other players:");
        for (PlayerDTO p: model.getPlayers()) {
                System.out.println();
                printPlayer(p, false);
        }

        // print councillors pool
        System.out.print("Councillors pool: [");
        for (CouncillorDTO c: model.getCouncillorsPool())
            System.out.print("\t" + c.getColor().getColoredColor());
        System.out.println(" ]");
        System.out.println();

        //print king an king's balcony
        System.out.print("The King is in " + model.getKing().getCurrentCity() + " and his balcony has: [");
        for (CouncillorDTO c: model.getKingBalcony().getCouncillors())
            System.out.print("\t" + c.getColor().getColoredColor());
        System.out.println(" ]<- councillors");

        // print regions
        System.out.println("The board has " + model.getRegions().size() + " regions:");
        for (RegionDTO r: model.getRegions()) {
            System.out.println("\n" + r.getName() + ": ");
            System.out.print("The balcony has: [");
            for (CouncillorDTO c: r.getBalcony().getCouncillors())
                System.out.print(" " + c.getColor().getColoredColor());
            System.out.println(" ]<- councillors");
            System.out.println("the Business Permit Tiles open are: ");
            for (BusinessPermissionTileDTO b: r.getDeck().getOpenCards()) {
                printBPT(b);
                System.out.println();
            }
        }
        System.out.println("\n");
    }

    private void printPlayer(PlayerDTO player, boolean full) {
        System.out.println(player.getName());
        System.out.println("Victory Points number:         " + player.getVictoryPoints());
        System.out.println("Coins number:                  " + player.getCoins());
        System.out.println("Position in Nobility Track:    " + player.getNobilityCell());
        System.out.println("Assistants number:             " + player.getAssistantsNumber());
        System.out.println("Remaining Main Actions:        " + player.getRemainingMainActions());
        System.out.println("Remaining Quick Actions:       " + player.getRemainingQuickActions());
        System.out.println("\n");
    }

    private void printBPT(BusinessPermissionTileDTO bpt) {
        int i = 0;
        for (String c: bpt.getCities()) {
            if (i != 0)
                System.out.print("/");
            System.out.print(c.toUpperCase().charAt(0));
            i++;
        }
        if (bpt.getCities().size() < 3)
            System.out.print("\t");
        System.out.print("\t");
        i = 0;
        for (BonusDTO b: bpt.getBonuses()) {
            if (i != 0)
                System.out.print(", ");
            System.out.print(b.getMultiplicity() + " " + b.getKind());
            i++;
        }
    }
}