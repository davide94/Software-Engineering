package it.polimi.ingsw.cg26.server.model.bonus;

import it.polimi.ingsw.cg26.common.update.request.PlayerBPTRequest;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import it.polimi.ingsw.cg26.server.model.player.Player;

public class TakePlayerBPTBonus extends BonusDecorator {

	public TakePlayerBPTBonus(Bonus decoratedBonus, int multiplicity) {
		super(decoratedBonus, multiplicity);
	}
	
	@Override
	public void apply(Player player) throws NoRemainingCardsException{
		super.apply(player);
		player.addRemainingChooseAction(1);
		player.addPendingRequest(new PlayerBPTRequest(this.getMultiplicity()));
	}

}
