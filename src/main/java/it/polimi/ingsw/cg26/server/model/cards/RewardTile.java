package it.polimi.ingsw.cg26.server.model.cards;

import it.polimi.ingsw.cg26.common.dto.RewardTileDTO;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class RewardTile {

	/**
	 * List of bonuses
	 */
	private Bonus bonuses;

	/**
	 * Creates a Reward Tile with a collection of bonuses
	 * @param bonuses is the collection of bonuses that the tile will contain
	 * @throws NullPointerException if bonuses is null
     */
	public RewardTile(Bonus bonuses) {
		if (bonuses == null)
			throw new NullPointerException();
		this.bonuses = bonuses;
	}
	
	/**
	 * Generates the dto of the tile
	 * @return the dto of the tile
	 */
	public RewardTileDTO getState(){
		return new RewardTileDTO(bonuses.getState());
	}
	
	/**
	 * Returns a list of bonuses contained by the tile
     * @return a list of bonuses contained by the tile
     */
    public Bonus getBonuses() {
        return this.bonuses; //TODO prima ritornava una copia verificare
    }

    /**
	 * Apply the bonuses to a player
	 * @param player is the player who will earn the bonuses
     */
    public void apply(Player player) {
    	this.bonuses.apply(player);
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bonuses == null) ? 0 : bonuses.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RewardTile other = (RewardTile) obj;
		if (bonuses == null) {
			if (other.bonuses != null)
				return false;
		} else if (!bonuses.equals(other.bonuses))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RewardTile{" +
				"bonuses=" + bonuses +
				'}';
	}
}
