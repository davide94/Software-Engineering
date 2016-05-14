package it.polimi.ingsw.cg26.controller;

import it.polimi.ingsw.cg26.Logger;
import it.polimi.ingsw.cg26.actions.Action;
import it.polimi.ingsw.cg26.model.board.GameBoard;
import it.polimi.ingsw.cg26.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.model.player.Assistant;
import it.polimi.ingsw.cg26.model.player.Player;
import it.polimi.ingsw.cg26.observer.Observer;
import it.polimi.ingsw.cg26.update.Update;
import it.polimi.ingsw.cg26.view.View;

import java.security.SecureRandom;
import java.util.LinkedList;
import java.math.BigInteger;

/**
 * 
 */
public class Controller implements Observer<Action> {

    private static final int INITIAL_CARDS_NUMBER = 6;

    private final LinkedList<Player> players;

    private Player currentPlayer;

    private final GameBoard gameBoard;

    public Controller(GameBoard gameBoard) {
        if (gameBoard == null)
            throw new NullPointerException();
        this.players = new LinkedList<>();
        this.gameBoard = gameBoard;
    }

    @Override
    public synchronized void update(Action action) {
        if (!action.getToken().equals(this.currentPlayer.getToken()))
            return;
        try {
            action.apply(gameBoard, currentPlayer);
            this.actionPerformed();
        } catch (RuntimeException e) {
            Logger.log(Logger.ERROR, e.getMessage());
        }
    }

    public void registerPlayer() {
        Player player = newPlayer();
        this.players.add(player);
        View view = new View(player.getToken(), player.getName());
        view.registerObserver(this);
        Thread thread = new Thread(view, player.getName());
        thread.start();
        if (this.players.size() == 1)
            this.currentPlayer = this.players.poll();
    }

    private Player newPlayer() {
        int playerNumber = this.players.size();
        String playerName = "Player " + playerNumber;
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
        return new Player(token, playerName, this.gameBoard.getNobilityTrack().getFirstCell(), playerNumber + 10, cards, assistants);
    }

    public void start() {
        this.currentPlayer = this.players.poll();
        this.players.add(this.currentPlayer);
    }

    public void actionPerformed() {
        this.gameBoard.notifyObservers(new Update(this.gameBoard));
        // TODO controlli su currentPlayer
    }
}