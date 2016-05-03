package it.polimi.ingsw.cg26.model.bonus;

import it.polimi.ingsw.cg26.model.player.Assistant;
import it.polimi.ingsw.cg26.model.player.Player;

/**
 * 
 */
public class AssistantBonus extends Bonus {

    /**
     * Default constructor
     */
    public AssistantBonus(int multilplicity) {
    	super(multilplicity);
    }

    /**
     * add to a player the number of assistants equal to the multiplicity
     * @param player the player to apply the bonus
     */
    public void apply(Player player) {
    	for(int i=0; i<this.getMultiplicity(); i++){
        	player.addAssistant(new Assistant());
    	}
    }

}