package it.polimi.ingsw.cg26.server.controller;

import it.polimi.ingsw.cg26.server.Scheduler;
import it.polimi.ingsw.cg26.server.actions.Action;
import it.polimi.ingsw.cg26.server.model.board.GameBoard;
import it.polimi.ingsw.cg26.server.observer.Observer;

import java.io.IOException;
import java.net.Socket;

/**
 * 
 */
public class Controller implements Observer<Action>, Runnable {

    private final GameBoard gameBoard;

    private final Scheduler scheduler;

    public Controller(GameBoard gameBoard) {
        if (gameBoard == null)
            throw new NullPointerException();
        this.gameBoard = gameBoard;
        System.out.println(gameBoard.getState());
        this.scheduler = new Scheduler(gameBoard, this);
    }

    @Override
    public synchronized void update(Action action) {
        try {
            action.apply(gameBoard, this.scheduler.getCurrentPlayer());
            this.scheduler.actionPerformed();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            // TODO notify the view that the action doesn't succeeded
        }
    }

    public void registerPlayer(Socket socket, String name) throws IOException {
        this.scheduler.registerPlayer(socket, name);
    }

    @Override
    public void run() {
        System.out.println("Partita cominciata");
        // TODO start the game
    }
}