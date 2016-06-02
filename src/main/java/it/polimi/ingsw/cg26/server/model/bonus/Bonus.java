package it.polimi.ingsw.cg26.server.model.bonus;

import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import it.polimi.ingsw.cg26.server.model.player.Player;

public interface Bonus {

	public void apply(Player player);
	
	public BonusDTO getState();
}
