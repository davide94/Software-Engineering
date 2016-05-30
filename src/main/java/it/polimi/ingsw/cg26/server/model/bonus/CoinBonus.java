package it.polimi.ingsw.cg26.server.model.bonus;

import it.polimi.ingsw.cg26.common.dto.BonusDTO;
import it.polimi.ingsw.cg26.server.model.player.Player;

/**
 * 
 */
public class CoinBonus extends Bonus {

    /**
     * Create a CoinBonus
     * @param multilplicity of the bonus
     * @throws IllegalArgumentException if the multiplicity is less than 1
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
    public BonusDTO getState() {
        return new BonusDTO("Coins", getMultiplicity());
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "CoinBonus{" +
                "multiplicity=" + super.getMultiplicity() +
                "}";
    }

}