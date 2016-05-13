package it.polimi.ingsw.cg26.model.bonus;

import it.polimi.ingsw.cg26.model.player.Player;

/**
 * 
 */
public class VictoryBonus extends Bonus {

    /**
     * Create a VictoryBonus
     * @param multilplicity of the bonus
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
    public String toString() {
        return "VictoryBonus{" +
                "multiplicity=" + super.getMultiplicity() +
                "}";
    }

}