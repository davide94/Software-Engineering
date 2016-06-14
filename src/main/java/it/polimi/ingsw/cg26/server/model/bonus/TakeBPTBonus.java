package it.polimi.ingsw.cg26.server.model.bonus;

import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.TakeBPTBonusDTO;
import it.polimi.ingsw.cg26.common.update.request.BPTRequest;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.util.List;

public class TakeBPTBonus extends BonusDecorator {

	/**
	 * Create a bonus that allows the player to take a BPT from the Board
	 * @param decoratedBonus the bonus to decorate
	 * @param multiplicity the multiplicity of the bonus
	 * @throws IllegalArgumentException if multiplicity is != 1
	 * @throws NullPointerException if the bonus to decorate is null
	 */
	public TakeBPTBonus(Bonus decoratedBonus, int multiplicity) {
		super(decoratedBonus, multiplicity);
		if(this.getMultiplicity()>1)
			throw new IllegalArgumentException();
	}
	
	@Override
	public void apply(Player player) throws NoRemainingCardsException{
		super.apply(player);
		player.addRemainingChooseAction(1);
		player.addPendingRequest(new BPTRequest());
	}
	
	@Override
    public List<String> getBonusNames(){
    	List<String> bonuses = super.getBonusNames();
    	bonuses.add("TakeBPT");
    	return bonuses;
    }
	
	@Override
	public BonusDTO getState() {
		return new TakeBPTBonusDTO(super.getState(), this.getMultiplicity());
	}
}
