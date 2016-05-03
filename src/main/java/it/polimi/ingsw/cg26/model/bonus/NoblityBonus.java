package it.polimi.ingsw.cg26.model.bonus;

import it.polimi.ingsw.cg26.model.player.Player;

/**
 * 
 */
public class NoblityBonus extends Bonus {

    /**
     * Default constructor
     */
    public NoblityBonus(int multilplicity) {
    	super(multilplicity);
    }

    /**
     * increment the nobility cell of the player equal to the multiplicity
     * @param player the player to apply the bonus
     */
    public void apply(Player player) {
        for(int i=0; i<this.getMultiplicity(); i++){
        	player.incrementNobility();
        }
    }

}