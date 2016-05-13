package it.polimi.ingsw.cg26.model.board;

import it.polimi.ingsw.cg26.model.player.*;

/**
 * 
 */
public class Emporium {
	
	/**
	 * 
	 */
	private Player player;
	

    /**
     * Default constructor
     */
    public Emporium(Player player) {
    	this.player=player;
    }
    
    public Player getPlayer(){
    	return this.player;
    }

	@Override
	public String toString() {
		return "Emporium{" +
				"player=" + player.getName() +
				'}';
	}
}