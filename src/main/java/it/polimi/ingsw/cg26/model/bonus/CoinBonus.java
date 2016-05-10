package it.polimi.ingsw.cg26.model.bonus;

import it.polimi.ingsw.cg26.model.player.Player;

/**
 * 
 */
public class CoinBonus extends Bonus {

    /**
     * Create a CoinBonus
     * @param multilplicity of the bonus
     */
    public CoinBonus(int multilplicity) {
    	super(multilplicity);
    }

    /**
     * add to a player the number of coins equal to the multiplicity
     * @param player the player to apply the bonus
     */
    @Override
    public void apply(Player player) {
        player.addCoins(this.getMultiplicity());
    }
}