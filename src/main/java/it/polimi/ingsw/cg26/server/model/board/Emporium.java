package it.polimi.ingsw.cg26.server.model.board;

import it.polimi.ingsw.cg26.server.model.player.Player;
import it.polimi.ingsw.cg26.state.EmporiumState;

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
    private Emporium(Player player) {
		if (player == null)
			throw new NullPointerException();
    	this.player=player;
    }

	public static Emporium createEmporium(Player player) {
		return new Emporium(player);
	}

	public EmporiumState getState() {
		return new EmporiumState(player.getName());
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