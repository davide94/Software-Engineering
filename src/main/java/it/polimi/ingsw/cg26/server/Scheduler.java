package it.polimi.ingsw.cg26.server;

import it.polimi.ingsw.cg26.server.change.Change;
import it.polimi.ingsw.cg26.server.controller.Controller;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;
import it.polimi.ingsw.cg26.server.view.ServerSocketView;
import it.polimi.ingsw.cg26.server.view.View;

import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.LinkedList;

/**
 *
 */
public class Scheduler {
    private static final int INITIAL_CARDS_NUMBER = 6;

    private final LinkedList<Player> players;

    private Player currentPlayer;

    private final GameBoard gameBoard;

    private final Controller controller;

    public Scheduler(GameBoard gameBoard, Controller controller) {
        if (gameBoard == null || controller == null)
            throw new NullPointerException();
        this.players = new LinkedList<>();
        this.gameBoard = gameBoard;
        this.controller = controller;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void registerPlayer(Socket socket, String name) throws IOException {
        int playerNumber = this.players.size();
        Player player = newPlayer(playerNumber, name);
        this.players.add(player);
        View view = new ServerSocketView(socket);
        view.registerObserver(this.controller);
        Thread thread = new Thread(view, player.getName());
        thread.start();
        if (this.players.size() == 1)
            this.currentPlayer = this.players.poll();
    }

    private Player newPlayer(int playerNumber, String name) {
        if (name.equals(""))
            name = "Player_" + playerNumber;
        LinkedList<Assistant> assistants = new LinkedList<>();
        for (int i = 0; i <= playerNumber; i++)
            assistants.add(new Assistant());
        LinkedList<PoliticCard> cards = new LinkedList<>();
        for (int i = 0; i < INITIAL_CARDS_NUMBER; i++)
            cards.add(this.gameBoard.getPoliticDeck().draw());

        String token = "";
        boolean found = true;
        while (found) {
            found = false;
            token = new BigInteger(130, new SecureRandom()).toString(32);
            for (Player player: this.players)
                if (player.getToken().equals(token)) {
                    found = true;
                    break;
                }
        }
        return new Player(token, name, this.gameBoard.getNobilityTrack().getFirstCell(), playerNumber + 10, cards, assistants);
    }

    public void actionPerformed() {
        this.gameBoard.notifyObservers(new Change());
        // TODO controlli su currentPlayer
    }
}
