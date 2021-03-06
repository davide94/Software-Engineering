package it.polimi.ingsw.cg26.server.model.bonus;

import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.util.List;

public interface Bonus {

	public void apply(Player player) throws NoRemainingCardsException;
	
	public List<String> getBonusNames();
	
	public BonusDTO getState();
}
