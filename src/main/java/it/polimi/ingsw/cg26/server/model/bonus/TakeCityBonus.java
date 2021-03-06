package it.polimi.ingsw.cg26.server.model.bonus;

import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.TakeYourCityBonusDTO;
import it.polimi.ingsw.cg26.common.update.request.CityBonusRequest;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.util.List;

public class TakeCityBonus extends BonusDecorator {

	/**
     * Create a Bonus that allows the player to choose and take another bonus placed on a city in which he has an emporium 
     * @param decoratedBonus the bonus to be decorated
     * @param multiplicity of the bonus
     * @throws IllegalArgumentException if the multiplicity is less than 1
     * @throws NullPointerException if the bonus to decorate is null
     */
	public TakeCityBonus(Bonus decoratedBonus, int multiplicity) {
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
	
	@Override
	public BonusDTO getState() {
		return new TakeYourCityBonusDTO(super.getState(), this.getMultiplicity());
	}
}
