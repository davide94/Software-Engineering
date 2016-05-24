package it.polimi.ingsw.cg26.server.model.cards;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polimi.ingsw.cg26.common.state.BonusState;
import it.polimi.ingsw.cg26.common.state.RewardTileState;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;

public class RewardTile {

	/**
	 * List of bonuses
	 */
	private List<Bonus> bonuses;

	/**
	 * Creates a Reward Tile with a collection of bonuses
	 * @param bonuses is the collection of bonuses that the tile will contain
     */
	public RewardTile(List<Bonus> bonuses) {
		this.bonuses = bonuses;
	}
	
	/**
	 * Generates the state of the tile
	 * @return the state of the tile
	 */
	public RewardTileState getState(){
		List<BonusState> bonusesState = new ArrayList<>();
		for(Bonus iterBonus : this.bonuses){
			bonusesState.add(iterBonus.getState());
		}
		return new RewardTileState(bonusesState);
	}
	
	/**
	 * Returns a list of bonuses contained by the tile
     * @return a list of bonuses contained by the tile
     */
    public List<Bonus> getBonuses() {
        List<Bonus> b =  new LinkedList<>();
        b.addAll(this.bonuses);
        return b;
    }
}
