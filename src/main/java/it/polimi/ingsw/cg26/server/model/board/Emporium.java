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
    	this.player = player;
    }
    
    /**
     * Create a player's emporium
     * @param player is the owner of the emporium
     * @return a new player's emporium
     */
	public static Emporium createEmporium(Player player) {
        if (player == null)
            throw new NullPointerException();
		return new Emporium(player);
	}

	public static Emporium createPlaceholderEmporium() {
		return new Emporium(null);
	}

	/**
	 * Create an emporium DTO
	 * @return the DTO of the emporium
	 */
	public EmporiumDTO getState() {
		return (player != null ? EmporiumDTO.createEmporium(player.getName()) : EmporiumDTO.createPlaceholderEmporium());
	}

	/**
	 * Get the player of the emporium 
	 * @return the owner of the emporium
	 */
    public boolean belongsTo(Player p){
    	return player != null && player.equals(p);
    }

	@Override
	public String toString() {
		return "Emporium{" +
				"player=" + (player != null ? player.getName() : "placeholder") +
				'}';
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Emporium emporium = (Emporium) o;

        return player != null ? player.equals(emporium.player) : emporium.player == null;

    }

    @Override
    public int hashCode() {
        return player != null ? player.hashCode() : 0;
    }
}