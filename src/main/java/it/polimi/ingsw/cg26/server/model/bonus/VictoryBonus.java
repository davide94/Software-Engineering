package it.polimi.ingsw.cg26.server.model.bonus;

import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.VictoryBonusDTO;
import it.polimi.ingsw.cg26.server.exceptions.NoRemainingCardsException;
import it.polimi.ingsw.cg26.server.model.player.Player;

import java.util.List;

/**
 * 
 */
public class VictoryBonus extends BonusDecorator {

    /**
     * Create a VictoryBonus
     * @param decoratedBonus the bonus to be decorated
     * @param multilplicity of the bonus
     * @throws IllegalArgumentException if the multiplicity is less than 1
     */
    public VictoryBonus(Bonus decoratedBonus, int multilplicity) {
    	super(decoratedBonus, multilplicity);
    }

    /**
     * add to a player the number of victory points equal to the multiplicity
     * @param player the player to apply the bonus
     */
    @Override
    public void apply(Player player) throws NoRemainingCardsException {
    	super.apply(player);
        player.addVictoryPoints(getMultiplicity());
    }

    @Override
    public List<String> getBonusNames(){
    	List<String> bonuses = super.getBonusNames();
    	bonuses.add("Victory");
    	return bonuses;
    }

    @Override
    public BonusDTO getState() {
        return new VictoryBonusDTO(super.getState(), getMultiplicity());
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
        return super.toString() + "\nVictoryBonus{" +
                "multiplicity=" + super.getMultiplicity() +
                "}";
    }

}