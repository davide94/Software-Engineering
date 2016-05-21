package it.polimi.ingsw.cg26.server.model.cards;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polimi.ingsw.cg26.common.state.BonusState;
import it.polimi.ingsw.cg26.common.state.RewardTileState;
import it.polimi.ingsw.cg26.server.model.bonus.Bonus;

public class RewardTile {
	
	private List<Bonus> bonuses;
	
	public RewardTile(List<Bonus> bonuses) {
		this.bonuses = bonuses;
	}
	
	/**
	 * 
	 * @return
	 */
	public RewardTileState getState(){
		List<BonusState> bonusesState = new ArrayList<>();
		for(Bonus iterBonus : this.bonuses){
			bonusesState.add(iterBonus.getState());
		}
		return new RewardTileState(bonusesState);
	}
	
	/**
     * @return
     */
    public List<Bonus> getBonuses() {
        List<Bonus> b =  new LinkedList<>();
        b.addAll(this.bonuses);
        return b;
    }
}
