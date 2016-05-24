package it.polimi.ingsw.cg26.server.model.bonus;

import it.polimi.ingsw.cg26.common.state.BonusState;
import it.polimi.ingsw.cg26.server.model.player.Player;

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

    @Override
    public BonusState getState() {
        return new BonusState("Coins", getMultiplicity());
    }

    @Override
    public String toString() {
        return "CoinBonus{" +
                "multiplicity=" + super.getMultiplicity() +
                "}";
    }

}