package it.polimi.ingsw.cg26.server.model.board;

import it.polimi.ingsw.cg26.common.dto.EmporiumDTO;
import it.polimi.ingsw.cg26.server.model.player.Player;


public class Emporium {
	
	/**
	 * The owner of the emporium
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

    
    /**
     * Create a player's emporium
     * @param player is the owner of the emporium
     * @return a new player's emporium
     */
	public static Emporium createEmporium(Player player) {
		return new Emporium(player);
	}

	
	/**
	 * Create an emporium DTO
	 * @return the DTO of the emporium
	 */
	public EmporiumDTO getState() {
		return new EmporiumDTO(player.getName());
	}

	/**
	 * Get the player of the emporium 
	 * @return the owner of the emporium
	 */
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