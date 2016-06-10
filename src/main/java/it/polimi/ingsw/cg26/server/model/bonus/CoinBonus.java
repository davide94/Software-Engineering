package it.polimi.ingsw.cg26.server.model.bonus;

import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.CoinBonusDTO;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.util.List;

/**
 * 
 */
public class CoinBonus extends BonusDecorator {

    /**
     * Create a CoinBonus
     * @param multilplicity of the bonus
     * @throws IllegalArgumentException if the multiplicity is less than 1
     */
    public CoinBonus(Bonus decoratedBonus, int multilplicity) {
    	super(decoratedBonus, multilplicity);
    }

    /**
     * add to a player the number of coins equal to the multiplicity
     * @param player the player to apply the bonus
     */
    @Override
    public void apply(Player player) throws NoRemainingCardsException {
    	super.apply(player);
        player.addCoins(this.getMultiplicity());
    }

    @Override
    public List<String> getBonusNames(){
    	List<String> bonuses = super.getBonusNames();
    	bonuses.add("Coin");
    	return bonuses;
    }
    
    @Override
    public BonusDTO getState() {
        return new CoinBonusDTO(super.getState(), getMultiplicity());
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
        return super.toString()+"\nCoinBonus{" +
                "multiplicity=" + super.getMultiplicity() +
                "}";
    }

}