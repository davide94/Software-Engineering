package it.polimi.ingsw.cg26.model;

import it.polimi.ingsw.cg26.exceptions.*;
import it.polimi.ingsw.cg26.model.board.GameBoard;
import it.polimi.ingsw.cg26.model.cards.BusinessPermissionTile;
import it.polimi.ingsw.cg26.model.cards.PoliticCard;
import it.polimi.ingsw.cg26.model.market.Sellable;
import it.polimi.ingsw.cg26.model.player.Assistant;
import it.polimi.ingsw.cg26.model.player.Player;
import it.polimi.ingsw.cg26.observer.Observable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 
 */
public class GameLogic extends Observable {
	
	private Player currentPlayer;
	
	private List<Player> players;
	
	//private GameBoard gameboard;
	
    /**
     * 
     */
    public GameLogic() {
    	players = new ArrayList<Player>();
        //this.gameboard=gameBoard;
    }

    /**
     * 
     */
    public void addPlayer(Player player) {
		if (this.players.isEmpty())
			this.currentPlayer = player;
        this.players.add(player);
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }
}