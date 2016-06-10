package it.polimi.ingsw.cg26.server.model.bonus;

import java.util.List;

import it.polimi.ingsw.cg26.common.update.request.CityBonusRequest;
import it.polimi.ingsw.cg26.server.model.player.Player;

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
	public void apply(Player player){
		player.addRemainingChooseAction(1);
		player.setPendingRequest(new CityBonusRequest(this.getMultiplicity()));
	}
	
	@Override
    public List<String> getBonusNames(){
    	List<String> bonuses = super.getBonusNames();
    	bonuses.add("TakeYourCity");
    	return bonuses;
    }
	
}
