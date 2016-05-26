package it.polimi.ingsw.cg26.server.model.bonus;

import it.polimi.ingsw.cg26.common.dto.BonusDTO;
import it.polimi.ingsw.cg26.server.model.player.Player;

/**
 * 
 */
public class VictoryBonus extends Bonus {

    /**
     * Create a VictoryBonus
     * @param multilplicity of the bonus
     * @throws IllegalArgumentException if the multiplicity is less than 1
     */
    public VictoryBonus(int multilplicity) {
    	super(multilplicity);
    }

    /**
     * add to a player the number of victory points equal to the multiplicity
     * @param player the player to apply the bonus
     */
    @Override
    public void apply(Player player) {
        player.addVictoryPoints(getMultiplicity());
    }

    @Override
    public BonusDTO getState() {
        return new BonusDTO("Victory points", getMultiplicity());
    }

    @Override
    public String toString() {
        return "VictoryBonus{" +
                "multiplicity=" + super.getMultiplicity() +
                "}";
    }

}