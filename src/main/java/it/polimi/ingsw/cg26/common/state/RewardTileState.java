package it.polimi.ingsw.cg26.common.state;

import java.util.List;

public class RewardTileState {

	private final List<BonusState> bonuses;
	
	public RewardTileState(List<BonusState> bonuses) {
		this.bonuses = bonuses;
	}

	/**
	 * @return the bonuses
	 */
	public List<BonusState> getBonuses() {
		return bonuses;
	}
	
	
}
