package it.polimi.ingsw.cg26.common.state;

import java.io.Serializable;
import java.util.List;

public class RewardTileState implements Serializable {

	private static final long serialVersionUID = -1029355922042286201L;

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
