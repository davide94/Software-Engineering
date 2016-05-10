package it.polimi.ingsw.cg26.model.bonus;

import it.polimi.ingsw.cg26.model.player.Player;

/**
 * 
 */
public class CardBonus extends Bonus {

    /**
     * Create a CardBonus
     * @param multilplicity of the bonus
     */
    public CardBonus(int multilplicity) {
    	super(multilplicity);
    }

    /**
     * the player draws a number of cards equal to the multiplicity
     * @param player the player to apply the bonus
     */
    @Override
    public void apply(Player player) {
        //TODO creare metodo per pescare
    }

}