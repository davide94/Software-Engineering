package it.polimi.ingsw.cg26.server.model.bonus;

import it.polimi.ingsw.cg26.server.model.player.Assistant;
import it.polimi.ingsw.cg26.server.model.player.Player;

/**
 * 
 */
public class AssistantBonus extends Bonus {

    /**
     * Create an AssistantBonus
     * @param multilplicity of the bonus
     */
    public AssistantBonus(int multilplicity) {
    	super(multilplicity);
    }

    /**
     * add to a player the number of assistants equal to the multiplicity
     * @param player the player to apply the bonus
     */
    @Override
    public void apply(Player player) {
    	for(int i=0; i<this.getMultiplicity(); i++){
        	player.addAssistant(new Assistant());
    	}
    }

    @Override
    public String toString() {
        return "AssistantBonus{" +
                "multiplicity=" + super.getMultiplicity() +
                "}";
    }

}