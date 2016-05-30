package it.polimi.ingsw.cg26.server.model.cards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import it.polimi.ingsw.cg26.common.dto.BonusDTO;
import it.polimi.ingsw.cg26.common.dto.RewardTileDTO;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class RewardTile {

	/**
	 * List of bonuses
	 */
	private Collection<Bonus> bonuses;

	/**
	 * Creates a Reward Tile with a collection of bonuses
	 * @param bonuses is the collection of bonuses that the tile will contain
	 * @throws NullPointerException if bonuses is null
     */
	public RewardTile(Collection<Bonus> bonuses) {
		if (bonuses == null)
			throw new NullPointerException();
		this.bonuses = bonuses;
	}
	
	/**
	 * Generates the dto of the tile
	 * @return the dto of the tile
	 */
	public RewardTileDTO getState(){
		List<BonusDTO> bonusesState = new ArrayList<>();
		for(Bonus iterBonus : this.bonuses){
			bonusesState.add(iterBonus.getState());
		}
		return new RewardTileDTO(bonusesState);
	}
	
	/**
	 * Returns a list of bonuses contained by the tile
     * @return a list of bonuses contained by the tile
     */
    public Collection<Bonus> getBonuses() {
        return new LinkedList<>(this.bonuses);
    }

    /**
	 * Applay the bonuses to a player
	 * @param player is the player who whill earn the bonuses
     */
    public void apply(Player player) {
    	for (Bonus b: bonuses)
    		b.apply(player);
    }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		RewardTile that = (RewardTile) o;

		return bonuses != null ? bonuses.containsAll(that.bonuses) && that.bonuses.containsAll(bonuses) : that.bonuses == null;

	}

	@Override
	public int hashCode() {
		return bonuses != null ? bonuses.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "RewardTile{" +
				"bonuses=" + bonuses +
				'}';
	}
}
