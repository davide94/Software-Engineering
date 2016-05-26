package it.polimi.ingsw.cg26.server.model.board;

import it.polimi.ingsw.cg26.server.model.player.Player;
import it.polimi.ingsw.cg26.common.dto.EmporiumDTO;

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

	public EmporiumDTO getState() {
		return new EmporiumDTO(player.getName());
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Emporium other = (Emporium) obj;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		return true;
	}
	
	
}