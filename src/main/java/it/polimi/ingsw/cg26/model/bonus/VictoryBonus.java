package it.polimi.ingsw.cg26.model.bonus;

import it.polimi.ingsw.cg26.model.player.Player;

/**
 * 
 */
public class VictoryBonus extends Bonus {

    /**
     * Default constructor
     */
    public VictoryBonus(int multilplicity) {
    	super(multilplicity);
    }

    /**
     * add to a player the number of victory points equal to the multiplicity
     * @param player the player to apply the bonus
     */
    public void apply(Player player) {
        player.addVictoryPoints(getMultiplicity());
    }

}