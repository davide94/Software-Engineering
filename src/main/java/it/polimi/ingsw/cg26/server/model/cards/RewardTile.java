package it.polimi.ingsw.cg26.server.model.cards;

import java.util.ArrayList;
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
	private List<Bonus> bonuses;

	/**
	 * Creates a Reward Tile with a collection of bonuses
	 * @param bonuses is the collection of bonuses that the tile will contain
	 * @throws NullPointerException if bonuses is null
     */
	public RewardTile(List<Bonus> bonuses) {
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
    public List<Bonus> getBonuses() {
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
}
