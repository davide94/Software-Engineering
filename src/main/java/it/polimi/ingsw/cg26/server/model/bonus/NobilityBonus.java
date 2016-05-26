package it.polimi.ingsw.cg26.server.model.bonus;

import it.polimi.ingsw.cg26.common.dto.BonusDTO;
import it.polimi.ingsw.cg26.server.model.player.Player;

/**
 * 
 */
public class NobilityBonus extends Bonus {

    /**
     * Create a NobilityBonus
     * @param multilplicity of the bonus
     */
    public NobilityBonus(int multilplicity) {
    	super(multilplicity);
    }

    /**
     * increment the nobility cell of the player equal to the multiplicity
     * @param player the player to apply the bonus
     */
    @Override
    public void apply(Player player) {
        for(int i=0; i<this.getMultiplicity(); i++){
        	player.incrementNobility();
        }
    }

    @Override
    public BonusDTO getState() {
        return new BonusDTO("Nobility points", getMultiplicity());
    }

    @Override
    public String toString() {
        return "NobilityBonus{" +
                "multiplicity=" + super.getMultiplicity() +
                "}";
    }

}