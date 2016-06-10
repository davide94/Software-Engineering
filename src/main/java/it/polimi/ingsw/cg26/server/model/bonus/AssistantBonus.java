package it.polimi.ingsw.cg26.server.model.bonus;

import it.polimi.ingsw.cg26.common.dto.bonusdto.AssistantBonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.util.List;

/**
 * 
 */
public class AssistantBonus extends BonusDecorator {

    /**
     * Create an AssistantBonus
     * @param multilplicity of the bonus
     * @throws IllegalArgumentException if the multiplicity is less than 1
     */
    public AssistantBonus(Bonus decoratedBonus, int multilplicity) {
    	super(decoratedBonus, multilplicity);
    }

    /**
     * add to a player the number of assistants equal to the multiplicity
     * @param player the player to apply the bonus
     */
    @Override
    public void apply(Player player) throws NoRemainingCardsException {
    	super.apply(player);
    	for(int i=0; i<this.getMultiplicity(); i++){
        	player.addAssistant(new Assistant());
    	}
    }
    
    @Override
    public List<String> getBonusNames(){
    	List<String> bonuses = super.getBonusNames();
    	bonuses.add("Assistant");
    	return bonuses;
    }

    @Override
    public BonusDTO getState() {
        return new AssistantBonusDTO(super.getState(), getMultiplicity());
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString()+"\nAssistantBonus{" +
                "multiplicity=" + super.getMultiplicity() +
                "}";
    }

}