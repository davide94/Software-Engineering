package it.polimi.ingsw.cg26.server.model.bonus;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.EmptyBonusDTO;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class EmptyBonus implements Bonus {

	@Override
	public void apply(Player player) {
		//nothing to do here, this is an empty bonus
	}
	
	@Override
	public List<String> getBonusNames(){
		return new ArrayList<>();
	}

	@Override
	public BonusDTO getState() {
		return new EmptyBonusDTO();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "";
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
		return true;
	}
}
