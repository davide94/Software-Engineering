package it.polimi.ingsw.cg26.server.model.bonus;

import it.polimi.ingsw.cg26.common.update.request.CityBonusRequest;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.util.List;

public class TakeYourCityBonus extends BonusDecorator {

	/**
     * Create a TakeYourCityBonus
     * @param decoratedBonus the bonus to be decorated
     * @param multilplicity of the bonus
     * @throws IllegalArgumentException if the multiplicity is less than 1
     */
	public TakeYourCityBonus(Bonus decoratedBonus, int multiplicity) {
		super(decoratedBonus, multiplicity);
	}

	@Override
	public void apply(Player player) throws NoRemainingCardsException{
		super.apply(player);
		player.addRemainingChooseAction(1);
		player.addPendingRequest(new CityBonusRequest(this.getMultiplicity()));
	}
	
	@Override
    public List<String> getBonusNames(){
    	List<String> bonuses = super.getBonusNames();
    	bonuses.add("TakeYourCity");
    	return bonuses;
    }
	
}
